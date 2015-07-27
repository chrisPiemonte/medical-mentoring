package it.uniba.di.sss1415.medicalmentoring;

import android.util.Log;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Chris on 20/07/2015.
 */
public class Parametri {

    String[] keyOfMap;
    String[] value;
    String chiaveAccesso;


    //  ------  genera la stringa da mandare al server
    public static String generaParametri(String tipoElemento, String accesso, String jsonDaInviare){

        // parametri = 'accesso:' + accesso + ', elemento:' + this.tipoElemento + ', jsonDaScrivere:' + jsonDaInviare;
        String stringaP;
        stringaP =  "accesso:" + accesso +
                ", elemento:" + tipoElemento +
                ", jsonDaScrivere:" + jsonDaInviare;

        return stringaP;
    }


    //  ------  creo dei "dizionari" in modo da facilitare la comunicazione con il server
    public Parametri(String tipo){

        switch (tipo){
            case "appuntamenti":
                this.keyOfMap = new String[] {"data", "oraInizio", "oraFine", "tipoAppuntamento", "intervento", "dottore"};
                this.value = new String[6];
                this.chiaveAccesso = "appuntamenti";
                break;

            case "richiesteValutare":
                this.keyOfMap = new String[] {"data", "oraInizio", "oraFine", "intervento", "dottore"};
                this.value = new String[5];
                this.chiaveAccesso = "richieste";
                break;

            case "mieRichiesteInserite":
                this.keyOfMap = new String[] {"id", "data", "oraInizio", "oraFine", "intervento", "nomeTutor", "cognomeTutor", "percorso", "utente"};
                this.value = new String[9];
                this.chiaveAccesso = "richiesteUtente";
                break;

            case "dispon":
                this.keyOfMap = new String[] {"id", "data", "oraInizio", "oraFine", "intervento", "ripetizione", "fineRipetizione", "utente"};
                this.value = new String[8];
                this.chiaveAccesso = "disponibilita";
                break;

            case "tutor":
                this.keyOfMap = new String[] {"nomeT", "cognomeT", "scoreT"};
                this.value = new String[3];
                this.chiaveAccesso = "tutor";
                break;

            case "dateDisp":
                this.keyOfMap = new String[] {"data", "oraInizio", "oraFine", "nomeT", "cognomeT", "scoreT"};
                this.value = new String[6];
                this.chiaveAccesso = "dateDisponibili";
                break;

            case "registrazione":
                this.keyOfMap = new String[] {"email", "numeroIscrizione", "annoIscrizione", "provincia", "password"};
                this.value = new String[5];
                break;

            case "accediSistema":
                this.keyOfMap = new String[] {"email", "password"};
                this.value = new String[2];
                break;

            case "expertise":
                this.keyOfMap = new String[] {"nomeEx"};
                this.value = new String[1];
                break;

            case "cambioPsw":
                this.keyOfMap = new String[] {"email", "nuovaP"};
                this.value = new String[2];
                break;

            case "modificaP":
                this.keyOfMap = new String[] {"email", "nome", "cognome", "provincia", "anno", "numero", "primaEx", "altreEx"};
                this.value = new String[8];
                break;

            case "interventiMedici":
                this.keyOfMap = new String[] {"campoMedico"};
                this.value = new String[1];
                break;
        }

    }


    //  ------  converte a json i dizionari
    public JSONObject toJsonObj(){

        JSONObject j = new JSONObject();
        for(int i = 0; i < this.value.length; i++){

            try{
                j.put(this.keyOfMap[i], this.value[i]);
            } catch(Exception e){
                e.printStackTrace();
                Log.i("FAIL_TO_CONVERT = ", this.keyOfMap[i] + this.value[i]);
            }
        }
        return  j;
    }

    public static void sort(ArrayList<HashMap<String,String>> s){
        SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date dataJson;
        int min = 0 ;

            try {

                for ( int i = 0 ; i < s.size(); i++) {
                    min = i;
                    Date minData = timeFormat.parse(s.get(min).get("data"));

                    for(int j = i+1; j < s.size(); j++){
                        dataJson = timeFormat.parse(s.get(j).get("data"));
                        if (dataJson.compareTo(minData) < 1) {
                            minData = dataJson;
                            min = j;
                        }
                    }
                    Collections.swap(s, i, min);
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }

    }


}