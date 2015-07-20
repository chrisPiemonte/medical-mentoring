package it.uniba.di.sss1415.medicalmentoring;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
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

import org.json.JSONException;
import org.json.JSONObject;


public class Registrati extends ActionBarActivity {

    private static final String GET = "GET";
    private static final String POST = "POST";

    // per la registrazione dati utente
    public EditText editEmail;            private EditText editNewPassword;
    private EditText editAnnoIscrizione;  private EditText editNumero;
    private Spinner dropdownProvince;


    //bottone registrati
    private Button btnAccedi;

    //per la gestione della connessione per la registrazione
    private static final String TIPO_ELEMENTO = "registrazione";
    private static final String ACCESSO = "write";

    // membri di dati
    private String email;      private String anno;     private String numero;
    private String provincia;  private String password;

    //per il login
    private String username;
    private JSONObject json; //JSON creato dai dati
    private String parametriServer; //lista dei parametri da inviare al server

    //dati dell'utente
    public DatiUtente datiUser;

    //pop up
    private CharSequence textErrorLogin = "Username o password errati!";
    private CharSequence textOkRegistration = "Registrazione Completata!";
    private CharSequence textErrorSignInNotComplete = "Riempire tutti i campi";
    int duration = Toast.LENGTH_LONG;
    private Toast toastMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrati);

        // prendo i dati
        editEmail = (EditText) findViewById(R.id.emailR_TV);
        editAnnoIscrizione = (EditText) findViewById(R.id.annoR_TV);
        editNumero = (EditText) findViewById(R.id.numeroR_TV);
        editNewPassword = (EditText) findViewById(R.id.passwordR_TV);

        //per lo spinner
        dropdownProvince = (Spinner) findViewById(R.id.provinceR_SP);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.arrayProvince,
                                             android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdownProvince.setAdapter(adapter);

    }
    
    public void prendiDatiSignIn(){

        //recuperiamo i dati inseriti dall'utente
        email = editEmail.getText().toString();
        anno = editAnnoIscrizione.getText().toString();
        numero = editNumero.getText().toString();
        provincia = dropdownProvince.getSelectedItem().toString();
        password = editNewPassword.getText().toString();

        //creo un nuovo oggetto JSON
        json = new JSONObject();
        try{
            json.put("email",email);
            json.put("numeroIscrizione",numero);
            json.put("annoIscrizione",anno);
            json.put("provincia",provincia);
            json.put("password", password);

            //genero la stringa di parametri
            parametriServer = Parametri.generaParametri(TIPO_ELEMENTO, ACCESSO, json.toString());
            Log.i("REG_PARAMETRI = ", parametriServer);
        }
        catch (JSONException e){
            e.printStackTrace();
        }
    }


    // evento per registrare l'utente
    public void registrazioneUtente(View v){

        // genera il json e i parametri
        prendiDatiSignIn();

        if ( (email.equals("")) || (anno.equals("")) || (numero.equals("")) || (provincia.equals("")) ) {
            creaMessaggio(textErrorSignInNotComplete);
        }else{
            String serverAnswer = ServerManager.sendRequest(POST, parametriServer);

            SharedStorageApp app = (SharedStorageApp) getApplicationContext();
            app.setDatiUtente(serverAnswer);       // datiUser = new DatiUtente(serverAnswer);

            creaMessaggio("Benvenuto " + "!");
            mostraHome();
        }

    }

    //metodo che apre l'activity della home dopo la registrazione o l'accesso
    public  void mostraHome(){
        Intent apriProfilo = new Intent(Registrati.this, Home.class);
        startActivity(apriProfilo);
    }


    //metodo che mostra la pop up all'utente dicendogli di aver sbagliato ad inserire username e password
    public void creaMessaggio(CharSequence message){
        Context context = getApplicationContext();
        toastMessage = Toast.makeText(context, message, duration);
        toastMessage.show();
    }

}
