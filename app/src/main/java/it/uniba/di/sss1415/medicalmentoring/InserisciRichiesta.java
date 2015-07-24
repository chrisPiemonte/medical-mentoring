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


import org.json.JSONObject;

import java.util.Calendar;

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

        specSP = (Spinner) findViewById(R.id.specSP);
        ArrayAdapter<CharSequence> adapterSpec = ArrayAdapter.createFromResource(this, R.array.specializzazioni,
                android.R.layout.simple_spinner_item);
        adapterSpec.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        specSP.setAdapter(adapterSpec);

        data = String.format("%02d", year) + "-" + String.format("%02d", month) + "-" + String.format("%02d", day);
        dateBTN.setText(data);

        from = String.format("%02d", fromHour) + ":" + "00";
        daBTN.setText(from);

        to = String.format("%02d", toHour) + ":" + "00";
        aBTN.setText(to);
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


    public void showDatePicker(View v){

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_date_picker);

        final DatePicker datePick = (DatePicker) dialog.findViewById(R.id.datePicker);

        Button ok = (Button) dialog.findViewById(R.id.okBTN);
        Button annulla = (Button) dialog.findViewById(R.id.annullaBTN);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                day = datePick.getDayOfMonth();
                month = datePick.getMonth()+1;
                year = datePick.getYear();
                data = String.format("%02d", year) + "-" + String.format("%02d", month) + "-" + String.format("%02d", day);

                dateBTN.setText(data);

                dialog.dismiss();
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

    public void showDaTimePicker(View v){

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_time_picker);

        final TimePicker timePick = (TimePicker) dialog.findViewById(R.id.timePicker);
        timePick.setIs24HourView(true);

        Button ok = (Button) dialog.findViewById(R.id.okBTN);
        Button annulla = (Button) dialog.findViewById(R.id.annullaBTN);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fromHour = timePick.getCurrentHour();
                fromMinute = timePick.getCurrentMinute();
                from = String.format("%02d", fromHour) + ":" + String.format("%02d", fromMinute);
                daBTN.setText(from);
                dialog.dismiss();
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

    public void showATimePicker(View v){

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_time_picker);

        final TimePicker timePick = (TimePicker) dialog.findViewById(R.id.timePicker);
        timePick.setIs24HourView(true);

        Button ok = (Button) dialog.findViewById(R.id.okBTN);
        Button annulla = (Button) dialog.findViewById(R.id.annullaBTN);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toHour = timePick.getCurrentHour();
                toMinute = timePick.getCurrentMinute();
                to = String.format("%02d", toHour) + ":" + String.format("%02d", toMinute);
                aBTN.setText(to);
                dialog.dismiss();
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

    public  void mostraCerca(View v){
        Intent apriRic = new Intent(InserisciRichiesta.this, Richiesta.class);
        apriRic.putExtra("data",data);
        apriRic.putExtra("oraInizio",from);
        apriRic.putExtra("oraFine",to);
        startActivity(apriRic);
    }


}
