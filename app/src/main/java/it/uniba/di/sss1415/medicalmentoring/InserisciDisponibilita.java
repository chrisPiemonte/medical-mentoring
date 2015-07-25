package it.uniba.di.sss1415.medicalmentoring;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;


public class InserisciDisponibilita extends ActionBarActivity {


    String param ;
    final String TIPO_ELEMENTO = "dispon";
    final String ACCESSO = "read";
    ArrayList<HashMap<String,String>> listaApp = new ArrayList<HashMap<String,String>>();
    final String S1 = "Ogni due settimane (Il lunedi)";
    final String S2 = "Ogni due settimane";

    Button fromDateBTN;
    Button daBTN;
    Button aBTN;
    Button toDateBTN;

    int yearFrom = Calendar.getInstance().get(Calendar.YEAR);
    int monthFrom = Calendar.getInstance().get(Calendar.MONTH)+1;
    int dayFrom = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    String dataFrom;

    int fromHour = (Calendar.getInstance().get(Calendar.HOUR_OF_DAY) + 1) % 24;
    int fromMinute = 0;
    String timeFrom;

    int toHour = (fromHour + 2) % 24;
    int toMinute = 0;
    String timeTo;

    RadioButton una;
    RadioButton settimanale;
    RadioButton bisettimanale;
    String ripetizione = "";

    int yearTo = yearFrom;
    int monthTo = (monthFrom + 1) % 12;
    int dayTo = dayFrom;
    String dataTo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inserisci_disponibilita);

        fromDateBTN = (Button) findViewById(R.id.dateBTN);
        daBTN = (Button) findViewById(R.id.inizioBTN);
        aBTN = (Button) findViewById(R.id.fineBTN);
        toDateBTN = (Button) findViewById(R.id.finoADataBTN);

        una = (RadioButton) findViewById(R.id.one);
        settimanale = (RadioButton) findViewById(R.id.onceAWeek);
        bisettimanale = (RadioButton) findViewById(R.id.twiceAWeek);

        dataFrom = yearFrom + "-" + String.format("%02d", monthFrom) + "-" + String.format("%02d", dayFrom);
        fromDateBTN.setText(dataFrom);

        timeFrom = String.format("%02d", fromHour) + ":" + "00";
        daBTN.setText(timeFrom);

        timeTo = String.format("%02d", toHour) + ":" + "00";
        aBTN.setText(timeTo);

        dataTo = yearTo + "-" + String.format("%02d", monthTo) + "-" + String.format("%02d", dayTo);
        toDateBTN.setText(dataTo);

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

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {

            case R.id.one:
                if (checked) ripetizione = "una volta";
                break;

            case R.id.onceAWeek:
                if (checked) ripetizione = "Ogni settimana";
                break;

            case R.id.twiceAWeek:
                if (checked) ripetizione = "Ogni due settimane";
                break;
        }
    }

    public void showDatePicker(View v){

        final View callerView = v;
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_date_picker);

        final DatePicker datePick = (DatePicker) dialog.findViewById(R.id.datePicker);

        Button ok = (Button) dialog.findViewById(R.id.okBTN);
        Button annulla = (Button) dialog.findViewById(R.id.annullaBTN);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(callerView.getId() == fromDateBTN.getId()) {
                    dayFrom = datePick.getDayOfMonth();
                    monthFrom = datePick.getMonth() + 1;
                    yearFrom = datePick.getYear();

                    dataFrom = yearFrom + "-" + String.format("%02d", monthFrom) + "-" + String.format("%02d", dayFrom);

                    fromDateBTN.setText(dataFrom);
                }else{
                    dayTo = datePick.getDayOfMonth();
                    monthTo = datePick.getMonth() + 1;
                    yearTo = datePick.getYear();

                    dataTo = yearTo + "-" + String.format("%02d", monthTo) + "-" + String.format("%02d", dayTo);

                    toDateBTN.setText(dataTo);
                }

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


    public void showTimePicker(View v){
        final View callerView = v;

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_time_picker);

        final TimePicker timePick = (TimePicker) dialog.findViewById(R.id.timePicker);
        timePick.setIs24HourView(true);

        Button ok = (Button) dialog.findViewById(R.id.okBTN);
        Button annulla = (Button) dialog.findViewById(R.id.annullaBTN);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(callerView.getId() == daBTN.getId()) {
                    fromHour = timePick.getCurrentHour();
                    fromMinute = timePick.getCurrentMinute();
                    timeFrom = String.format("%02d", fromHour) + ":" + String.format("%02d", fromMinute);
                    daBTN.setText(timeFrom);
                }else{
                    toHour = timePick.getCurrentHour();
                    toMinute = timePick.getCurrentMinute();
                    timeTo = String.format("%02d", toHour) + ":" + String.format("%02d", toMinute);
                    aBTN.setText(timeTo);
                }
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

    public void inserisciClick(View v){

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_richiesta);
        dialog.setTitle("Conferma");

        TextView message = (TextView) dialog.findViewById(R.id.richiestaTV);
        message.setText("Sei sicuro di voler inserire la disponibilità ?");
        Button ok = (Button) dialog.findViewById(R.id.okBTN);
        Button annulla = (Button) dialog.findViewById(R.id.annullaBTN);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                disponibilitaInviata();
                dialog.dismiss();
                finish();
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

    public void disponibilitaInviata(){

        DatiUtente datiUser = SharedStorageApp.getInstance().getDatiUtente();
        Parametri diz = new Parametri("dispon");

        diz.value = new String[] {"", dataFrom, timeFrom, timeTo, datiUser.getPrimariaEx(), ripetizione, dataTo, datiUser.getEmail()};
        param = Parametri.generaParametri(TIPO_ELEMENTO, ACCESSO, diz.toJsonObj().toString());

        String serverAnswer = ServerManager.sendRequest("POST", param);

        Toast toastMessage = Toast.makeText(getApplicationContext(), R.string.disponibilita_inserita, Toast.LENGTH_LONG);
        toastMessage.show();
    }

}
