package it.uniba.di.sss1415.medicalmentoring;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;


import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class InserisciRichiesta extends AppCompatActivity {

    Button dateBTN;
    Button daBTN;
    Button aBTN;
    Spinner specSP;

    int year = Calendar.getInstance().get(Calendar.YEAR);
    int month = Calendar.getInstance().get(Calendar.MONTH)+1;
    int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    String data;

    int fromHour = (Calendar.getInstance().get(Calendar.HOUR_OF_DAY) + 1) % 24;
    int fromMinute = 0;
    String from;

    int toHour = (fromHour + 2) % 24;
    int toMinute = 0;
    String to;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inserisci_richiesta);

        dateBTN = (Button) findViewById(R.id.dateBTN);
        daBTN = (Button) findViewById(R.id.inizioBTN);
        aBTN = (Button) findViewById(R.id.fineBTN);
        specSP = (Spinner) findViewById(R.id.specSP);

        //  ------ Imposto gli array per gli spinner
        specSP = (Spinner) findViewById(R.id.specSP);
        ArrayAdapter<CharSequence> adapterSpec = ArrayAdapter.createFromResource(this, R.array.specializzazioni,
                android.R.layout.simple_spinner_item);
        adapterSpec.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        specSP.setAdapter(adapterSpec);

        //  ------  Formatto la data, divisa da trattini ed aggiungendo gli zero per numeri
        //  ------  minori di dieci
        data = String.format("%02d", year) + "-" + String.format("%02d", month) + "-" + String.format("%02d", day);
        dateBTN.setText(data);

        from = String.format("%02d", fromHour) + ":" + "00";
        daBTN.setText(from);

        to = String.format("%02d", toHour) + ":" + "00";
        aBTN.setText(to);
    }

    //  ------  Salvo i dati inseriti quando il telefono cambia orientazione
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("mydata", dateBTN.getText().toString());
        outState.putString("myfrom", daBTN.getText().toString());
        outState.putString("myto", aBTN.getText().toString());


    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        dateBTN.setText(savedInstanceState.getString("mydata"));
        daBTN.setText(savedInstanceState.getString("myfrom"));
        aBTN.setText(savedInstanceState.getString("myto"));


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_inserisci_richiesta, menu);
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

    //  ------  mostro il dialog con il dataPicker per selezionare una data e salvarla
    public void showDatePicker(View v){

        android.app.DialogFragment picker = new DatePickerFragment((Button) v);
        picker.show(getFragmentManager(), "datePicker");

    }

    //  ------  mostro il dialog con il dataPicker per selezionare una data e salvarla
    public void showTimePicker(View v){

        android.app.DialogFragment newFragment = new TimePickerFragment((Button)v);
        newFragment.show(getFragmentManager(), "TimePicker");
    }



    public  void mostraCerca(View v){
        data = dateBTN.getText().toString();
        from =  daBTN.getText().toString();
        to = aBTN.getText().toString();
        try{
            DateFormat sdf = new SimpleDateFormat("hh:mm");
            Date checkFrom = sdf.parse(from);
            DateFormat sdf2 = new SimpleDateFormat("hh:mm");
            Date checkTo = sdf.parse(to);
            if (checkTo.compareTo(checkFrom) > 0){
                Intent apriRic = new Intent(InserisciRichiesta.this, Richiesta.class);
                apriRic.putExtra("data",data);
                apriRic.putExtra("oraInizio",from);
                apriRic.putExtra("oraFine",to);
                startActivity(apriRic);

                Parametri diz = new Parametri("mieRichiesteInserite");
                diz.value = new String[] { "", data, from, to, specSP.getSelectedItem().toString(), "", "", "", ""};

                ArrayList<HashMap<String, String>> leMieRic = SharedStorageApp.getInstance().getLeMieRichieste();
                HashMap<String, String> map = new HashMap<String, String>();
                for(int i = 0; i < diz.value.length; i++){
                    map.put(diz.keyOfMap[i], diz.value[i]);
                }
                leMieRic.add(map);
            }
            else{
                Toast.makeText(this,getResources().getString(R.string.ops_orario),Toast.LENGTH_LONG).show();
            }

        }catch(ParseException e) {
            e.printStackTrace();
        }


    }


}
