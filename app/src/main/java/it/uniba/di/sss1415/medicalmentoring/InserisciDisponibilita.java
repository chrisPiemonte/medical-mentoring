package it.uniba.di.sss1415.medicalmentoring;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListAdapter;

import java.util.ArrayList;
import java.util.HashMap;


public class InserisciDisponibilita extends ActionBarActivity {


    String param ;
    final String TIPO_ELEMENTO = "dispon";
    final String ACCESSO = "read";
    ArrayList<HashMap<String,String>> listaApp = new ArrayList<HashMap<String,String>>();
    final String S1 = "Ogni due settimane (Il lunedi)";
    final String S2 = "Ogni due settimane";

    public ListAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inserisci_disponibilita);


        Parametri diz = new Parametri("dispon");
        diz.value = new String[] {"2","2012-21-03", "17:00:00", "19:00:00", "Urologia", S1, "", "chris.piemo@afa.com"};
        //diz.toJsonObj().toString()
        param = Parametri.generaParametri(TIPO_ELEMENTO, ACCESSO,"");

        String serverAnswer = ServerManager.sendRequest("POST",param);
        //listaApp = JSONManager.toListOfMap(serverAnswer, diz.chiaveAccesso);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_inserisci_disponibilita, menu);
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
}
