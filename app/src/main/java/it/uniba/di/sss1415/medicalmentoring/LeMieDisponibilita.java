package it.uniba.di.sss1415.medicalmentoring;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

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
}
