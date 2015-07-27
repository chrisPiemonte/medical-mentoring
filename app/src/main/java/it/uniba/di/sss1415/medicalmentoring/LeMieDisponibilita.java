package it.uniba.di.sss1415.medicalmentoring;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class LeMieDisponibilita extends AppCompatActivity {

    public ListAdapter adapter;
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_le_mie_disponibilita);

        //  ------  mostra le dixponibilità inserite e salvate in locale
        list = (ListView) findViewById(R.id.mieDispLV);
        ArrayList<HashMap<String, String>> leMieDisp = SharedStorageApp.getInstance().getLeMieDisponibilita();
        Parametri.sort(leMieDisp);

        adapter = new SimpleAdapter(this,
                leMieDisp,
                R.layout.item_le_mie_disponibilita,
                new String[] {"data", "fineRipetizione", "oraInizio", "oraFine", "ripetizione"},
                new int[]{R.id.textViewdataFrom, R.id.textViewDataTo, R.id.textViewOraInizio,
                        R.id.textViewOraFine, R.id.textViewRipetizione});
        list.setAdapter(adapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_le_mie_disponibilita, menu);
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

    public void rimuovi(View v) {
        ViewGroup item = (ViewGroup) v.getParent().getParent();
        //R.id.textViewdataFrom, R.id.textViewDataTo, R.id.textViewOraInizio,
        //R.id.textViewOraFine, R.id.textViewRipetizione
        if(v.getId() == R.id.inviaRichiestaBTN) {
            HashMap<String, String> newAppu = new HashMap<String, String>();
            newAppu.put("data", ((TextView) item.findViewById(R.id.textViewdataFrom)).getText().toString());
            newAppu.put("oraInizio", ((TextView) item.findViewById(R.id.textViewOraInizio)).getText().toString());
            newAppu.put("oraFine", ((TextView) item.findViewById(R.id.textViewOraFine)).getText().toString());
            newAppu.put("ripetizione", ((TextView) item.findViewById(R.id.textViewRipetizione)).getText().toString());
            newAppu.put("fineRipetizione", ((TextView) item.findViewById(R.id.textViewDataTo)).getText().toString());


            SharedStorageApp.getInstance().removeDisponibilita(newAppu);
        }else{
            HashMap<String, String> newAppu = new HashMap<String, String>();
            newAppu.put("data", ((TextView) item.findViewById(R.id.textViewdataFrom)).getText().toString());
            newAppu.put("oraInizio", ((TextView) item.findViewById(R.id.textViewOraInizio)).getText().toString());
            newAppu.put("oraFine", ((TextView) item.findViewById(R.id.textViewOraFine)).getText().toString());
            newAppu.put("ripetizione", ((TextView) item.findViewById(R.id.textViewRipetizione)).getText().toString());
            newAppu.put("fineRipetizione", ((TextView) item.findViewById(R.id.textViewDataTo)).getText().toString());


            SharedStorageApp.getInstance().removeDisponibilita(newAppu);
            Intent i = new Intent(LeMieDisponibilita.this, InserisciDisponibilita.class);
            startActivity(i);
            finish();
        }

        item.setVisibility(View.GONE);

    }
}
