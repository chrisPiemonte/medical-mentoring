package it.uniba.di.sss1415.medicalmentoring;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONObject;

public class Profilo extends AppCompatActivity {

    private EditText nome;
    private EditText cognome;

    private Spinner dropdownProvince;
    private EditText anno;
    private EditText numero;

    private Spinner dropdownSpec;

    DatiUtente datiUser;

    private CharSequence textErrorOldP = "Attenzione, vecchia password sbagliata";
    private CharSequence textErrorNuovaP = "O nuova password vuota";
    int duration = Toast.LENGTH_LONG;
    private Toast toastMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profilo);


        //prendo i dati da modificare
        nome = (EditText) findViewById(R.id.nomeET);
        cognome = (EditText) findViewById(R.id.cognomeET);
        anno = (EditText) findViewById(R.id.annoET);
        numero = (EditText) findViewById(R.id.numeroET);

        //per gli spinner

        dropdownProvince = (Spinner) findViewById(R.id.provSP);
        ArrayAdapter<CharSequence> adapterProv = ArrayAdapter.createFromResource(this, R.array.arrayProvince,
                android.R.layout.simple_spinner_item);
        adapterProv.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdownProvince.setAdapter(adapterProv);

        dropdownSpec = (Spinner) findViewById(R.id.specSP);
        ArrayAdapter<CharSequence> adapterSpec = ArrayAdapter.createFromResource(this, R.array.specializzazioni,
                android.R.layout.simple_spinner_item);
        adapterSpec.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdownSpec.setAdapter(adapterSpec);



    }


    @Override
    protected void onResume() {
        super.onResume();

        SharedStorageApp app = (SharedStorageApp) getApplicationContext();
        datiUser = app.getDatiUtente();

        int i = getIndex(dropdownProvince, datiUser.getProvinciaIscrizione());
        dropdownProvince.setSelection(i);
        i = getIndex(dropdownSpec, datiUser.getPrimariaEx());
        dropdownSpec.setSelection(i);


        nome.setText(datiUser.getNome());
        cognome.setText(datiUser.getCognome());
        anno.setText(datiUser.getAnnoIscr());
        numero.setText(datiUser.getNumeroIscr());

    }

        @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profilo, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        
        return super.onOptionsItemSelected(item);
    }


    private int getIndex(Spinner spinner, String myString){
        int index = 0;

        for (int i = 0; i < spinner.getCount(); i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                index = i;
                break;
            }
        }
        return index;
    }

    // aggiungere pop-up di conferma e aggiornare i dati condivisi
    public void modificaAccount(View v){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.sei_sicuro);
        builder.setCancelable(true);
        builder.setPositiveButton(R.string.si, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                try {
                    Parametri diz = new Parametri("modificaP");
                    diz.value[0] = SharedStorageApp.getInstance().getDatiUtente().getEmail();
                    diz.value[1] = nome.getText().toString();
                    diz.value[2] = cognome.getText().toString();
                    diz.value[3] = dropdownProvince.getSelectedItem().toString();
                    diz.value[4] = anno.getText().toString();
                    diz.value[5] = numero.getText().toString();
                    diz.value[6] = dropdownSpec.getSelectedItem().toString();
                    diz.value[7] = dropdownSpec.getSelectedItem().toString();


                    String param = Parametri.generaParametri("modificaP", "write", diz.toJsonObj().toString());
                    String serverAnswer = ServerManager.sendRequest("POST", param);

                    SharedStorageApp app = SharedStorageApp.getInstance();
                    app.setDatiUtente(serverAnswer);
                    app.getDatiUtente().setPrimariaEx(diz.value[7]);

                    Log.i("GEST_ACC_INV = ", param);
                    Log.i("GEST_ACC_RIC = ", serverAnswer + " -.-");
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.i("GEST_ACC_MALE = ", "MALE");
                }
                finish();

            }
        });

        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }


    public void cambiaPassword(View v){

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_password);
        dialog.setTitle("Cambia Password");

        final EditText oldPass = (EditText) dialog.findViewById(R.id.oldPassET);
        final EditText newPass = (EditText) dialog.findViewById(R.id.newPassET);
        Button ok = (Button) dialog.findViewById(R.id.okBTN);
        Button annulla = (Button) dialog.findViewById(R.id.annullaBTN);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String oldP = oldPass.getText().toString();
                String newP = newPass.getText().toString();

                DatiUtente datiUser = SharedStorageApp.getInstance().getDatiUtente();


                if (oldP.equals(datiUser.getPass()) && !(newP.equals("")) ) {
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("email", datiUser.getEmail());
                        jsonObject.put("nuovaP", newP);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    String param = Parametri.generaParametri("cambioPsw", "change", jsonObject.toString());
                    String serverAnswer = ServerManager.sendRequest("POST", param);
                    creaMessaggio("Inserimento avvenuto con successo!");
                    datiUser.setPass(newP);
                    dialog.dismiss();
                } else {
                    creaMessaggio(textErrorOldP+" "+textErrorNuovaP);
                }

            }
        });
        annulla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

            }
        });
        dialog.show();
    }

    public void creaMessaggio(CharSequence message){
        Context context = getApplicationContext();
        toastMessage = Toast.makeText(context, message, duration);
        toastMessage.show();
    }


}
