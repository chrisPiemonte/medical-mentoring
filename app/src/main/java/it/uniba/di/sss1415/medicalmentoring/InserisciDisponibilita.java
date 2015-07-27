package it.uniba.di.sss1415.medicalmentoring;

import android.app.Dialog;
import android.app.DialogFragment;
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
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
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


public class InserisciDisponibilita extends ActionBarActivity {


    String param ;
    final String TIPO_ELEMENTO = "dispon";
    final String TIPO_ELEMENTO_DD = "dateDisp";
    final String ACCESSO = "write";

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

    public String dataPicked;

    int fromHour = (Calendar.getInstance().get(Calendar.HOUR_OF_DAY) + 1) % 24;
    int fromMinute = 0;
    String timeFrom;

    int toHour = (fromHour + 2) % 24;
    int toMinute = 0;
    String timeTo;

    RadioButton una;
    RadioButton settimanale;
    RadioButton bisettimanale;
    RadioGroup checkRG;
    String ripetizione;

    int yearTo = yearFrom;
    int monthTo = (monthFrom + 1) % 12;
    int dayTo = dayFrom;
    String dataTo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inserisci_disponibilita);
        ripetizione = getResources().getString(R.string.una_volta).toString();

        fromDateBTN = (Button) findViewById(R.id.dateBTN);
        daBTN = (Button) findViewById(R.id.inizioBTN);
        aBTN = (Button) findViewById(R.id.fineBTN);
        toDateBTN = (Button) findViewById(R.id.finoADataBTN);
        checkRG = (RadioGroup) findViewById(R.id.checkRG);
        checkRG.check(R.id.one);

        una = (RadioButton) findViewById(R.id.one);
        settimanale = (RadioButton) findViewById(R.id.onceAWeek);
        bisettimanale = (RadioButton) findViewById(R.id.twiceAWeek);

        //  ------  Formatto la data, divisa da trattini ed aggiungendo gli zero per numeri
        //  ------  minori di dieci
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
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        //  ------ Salvo le istanze dei bottoni in caso l'activity venga posta in "Landscape"
        outState.putString("mydataTo", toDateBTN.getText().toString());
        outState.putString("mydataFrom", fromDateBTN.getText().toString());
        outState.putString("myTimeFrom", daBTN.getText().toString());
        outState.putString("myTimeTo", aBTN.getText().toString());


    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // ------ Assegno i valori alle varibili salvate in precedenza
        toDateBTN.setText(savedInstanceState.getString("mydataTo"));
        fromDateBTN.setText(savedInstanceState.getString("mydataFrom"));
        daBTN.setText(savedInstanceState.getString("myTimeFrom"));
        aBTN.setText(savedInstanceState.getString("myTimeTo"));


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_inserisci_disponibilita, menu);
        return true;
    }

    /*
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
    }*/

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {

            case R.id.one:
                if (checked) ripetizione = getBaseContext().getString(R.string.una_volta);
                break;

            case R.id.onceAWeek:
                if (checked) ripetizione = getBaseContext().getString(R.string.ogni_settimana);
                break;

            case R.id.twiceAWeek:
                if (checked) ripetizione = getBaseContext().getString(R.string.bisettimanale);
                break;
        }
    }

    public void showDatePicker(View v){

        DialogFragment picker = new DatePickerFragment((Button) v);
        picker.show(getFragmentManager(), "datePicker");

    }


    public void showTimePicker(View v){
        //  ------ Il metodo mostra il dialog per inserire l'orario
        DialogFragment newFragment = new TimePickerFragment((Button)v);
        newFragment.show(getFragmentManager(), "TimePicker");
    }

    public void inserisciClick(View v){

        //  ------ Il metodo mostra il dialog per la conferma
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_richiesta);
        dialog.setTitle(getBaseContext().getString(R.string.conferma));

        TextView message = (TextView) dialog.findViewById(R.id.richiestaTV);
        message.setText(getBaseContext().getString(R.string.sei_sicuro_inserire_disp));
        Button ok = (Button) dialog.findViewById(R.id.okBTN);
        Button annulla = (Button) dialog.findViewById(R.id.annullaBTN);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                disponibilitaInviata();
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

    public void disponibilitaInviata(){

        //  ------ Il metodo invia la disponibilita' appena inserita
        dataFrom = fromDateBTN.getText().toString();
        dataTo = toDateBTN.getText().toString();
        timeFrom = daBTN.getText().toString();
        timeTo = aBTN.getText().toString();
        try{
            DateFormat timeFormat = new SimpleDateFormat("hh:mm");
            Date checkFrom = timeFormat.parse(timeFrom);
            Date checkTo = timeFormat.parse(timeTo);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date checkDateFrom = dateFormat.parse(dataFrom);
            Date checkDateTo = dateFormat.parse(dataTo);
            if (checkDateTo.compareTo(checkDateFrom) > 0){
                if(checkTo.compareTo(checkFrom) > 0){
                    //  ------ Prendo i dati relativi all'utente
                    DatiUtente datiUser = SharedStorageApp.getInstance().getDatiUtente();

                    //  ------ Creo il dizionario con gli attributi "dispon"
                    Parametri diz = new Parametri("dispon");
                    //  ------ Popolo il dizionario creato
                    diz.value = new String[] {"", dataFrom, timeFrom, timeTo, datiUser.getPrimariaEx(), ripetizione, dataTo, datiUser.getEmail()};

                    //  ------ Genero i parametri relativi ai tipi d'accesso
                    param = Parametri.generaParametri(TIPO_ELEMENTO, ACCESSO, diz.toJsonObj().toString());

                    //  ------ Mando la richiesta al server con i parametri
                    String serverAnswer = ServerManager.sendRequest("POST", param);

                    //  ------ Mandiamo la richiesta anche nella sezione relativa alle date disponibili
                    //  ------ Con i caratteri formattati in base ad essa
                    Parametri dizDD = new Parametri("dateDisp");
                    dizDD.value = new String[] {dataFrom, timeFrom, timeTo, datiUser.getNome(), datiUser.getCognome(), datiUser.getScore()};
                    param = Parametri.generaParametri(TIPO_ELEMENTO_DD, ACCESSO, dizDD.toJsonObj().toString());
                    ServerManager.sendRequest("POST", param);



                    //  ------ Salviamo la disponibilita' in locale per visionarle nelle "Mie Disponibilità"
                    //  ------ L'operazione d'inserimento sul database non è disponibile,
                    //  ------ Quindi abbiamo utilizzato una simulazione
                    ArrayList<HashMap<String, String>> leMieDisp = SharedStorageApp.getInstance().getLeMieDisponibilita();
                    HashMap<String, String> map = new HashMap<String, String>();
                    for(int i = 0; i < diz.value.length; i++){
                        map.put(diz.keyOfMap[i], diz.value[i]);
                    }
                    leMieDisp.add(map);

                    Toast toastMessage = Toast.makeText(getApplicationContext(), R.string.disponibilita_inserita, Toast.LENGTH_LONG);
                    toastMessage.show();
                    finish();

                } else {
                    Toast.makeText(this, getBaseContext().getString(R.string.ops_orario), Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, getBaseContext().getString(R.string.ops_data), Toast.LENGTH_LONG).show();
            }
        }catch (ParseException e) {
            e.printStackTrace();
        }


    }

}
