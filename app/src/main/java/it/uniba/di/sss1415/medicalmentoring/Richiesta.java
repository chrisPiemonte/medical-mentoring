package it.uniba.di.sss1415.medicalmentoring;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Richiesta extends AppCompatActivity {

    String from,to,data;
    ListView list;

    String param ;
    final String TIPO_ELEMENTO = "dateDisp";
    final String ACCESSO = "read";
    ArrayList<HashMap<String,String>> listaApp = new ArrayList<HashMap<String,String>>();

    public ListAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_richiesta);

        Intent ricevuto = getIntent();
        from = ricevuto.getStringExtra("oraInizio");
        to = ricevuto.getStringExtra("oraFine");
        data = ricevuto.getStringExtra("data");
        list = (ListView) findViewById(R.id.list);

        Parametri diz = new Parametri("dateDisp");
        diz.value = new String[]{data,from,to,"","",""};
        param = Parametri.generaParametri(TIPO_ELEMENTO, ACCESSO, diz.toJsonObj().toString());
        String serverAnswer = ServerManager.sendRequest("POST",param);

        listaApp = JSONManager.toListOfMap(serverAnswer, diz.chiaveAccesso);

        adapter = new SimpleAdapter(Richiesta.this,
                listaApp,
                R.layout.item_richiesta,
                new String[] {"data","oraInizio","oraFine","nomeT","cognomeT","scoreT"},
                new int[]{R.id.dataTV, R.id.oraInizioTV, R.id.oraFineTV,
                        R.id.nomeTTV, R.id.cognomeTTV, R.id.scoreTTV});
        list.setAdapter(adapter);




    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_richiesta, menu);
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
