package it.uniba.di.sss1415.medicalmentoring;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;


public class Login extends ActionBarActivity {

    private static final String GET = "GET";
    private static final String POST = "POST";

    // per il login
    private EditText editUsername;        private EditText editPassword;

    //per la gestione della connessione per il login
    private static final String TIPO_ELEMENTO_LOGIN = "accediSistema";
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
        setContentView(R.layout.activity_login);

        //per il login
        editUsername = (EditText) findViewById(R.id.emailET);
        editPassword = (EditText) findViewById(R.id.passwordET);

    }


    // genera il Json e i parametri per il LOGIN
    private void prendiDatiLogin(){
        username = editUsername.getText().toString();
        password = editPassword.getText().toString();

        json = new JSONObject();
        try{
            json.put("email",username);
            json.put("password", password);

            //genero la stringa di parametri
            parametriServer = Parametri.generaParametri(TIPO_ELEMENTO_LOGIN, ACCESSO, json.toString());
            Log.i("PARAMETRI = ", parametriServer);
        }
        catch (JSONException e){
            e.printStackTrace();
        }
    }

    // accesso al sistema in seguito al LOGIN
    public void accessoSistema(View v){

        prendiDatiLogin();
        String serverAnswer = ServerManager.sendRequest(GET, parametriServer);

        if ( !(serverAnswer.equals("Utente non presente")) ){

            SharedStorageApp app = SharedStorageApp.getInstance();
            app.setDatiUtente(serverAnswer);

            try {

                creaMessaggio("Ciao " + app.getDatiUtente().getNome() + " !");
            }catch(Exception e){
                e.printStackTrace();
            }
            mostraHome();
        }
        else creaMessaggio(textErrorLogin);

    }

    //metodo che apre l'activity della home dopo la registrazione o l'accesso
    public  void mostraHome(){
        Intent apriHome = new Intent(Login.this, Home.class);
        startActivity(apriHome);
    }

    public  void mostraReg(View v){
        Intent apriReg = new Intent(Login.this, Registrati.class);
        startActivity(apriReg);
    }

    //metodo che mostra la pop up all'utente dicendogli di aver sbagliato ad inserire username e password
    public void creaMessaggio(CharSequence message){
        Context context = getApplicationContext();
        toastMessage = Toast.makeText(context, message, duration);
        toastMessage.show();
    }


}
