package it.uniba.di.sss1415.medicalmentoring;

import android.util.Log;

import org.json.JSONObject;

/**
 * Created by Chris on 20/07/2015.
 */
public class Parametri {

    String[] keyOfMap;
    String[] value;
    String chiaveAccesso;



    public static String generaParametri(String tipoElemento, String accesso, String jsonDaInviare){

        // parametri = 'accesso:' + accesso + ', elemento:' + this.tipoElemento + ', jsonDaScrivere:' + jsonDaInviare;
        String stringaP;
        stringaP =  "accesso:" + accesso +
                ", elemento:" + tipoElemento +
                ", jsonDaScrivere:" + jsonDaInviare;

        return stringaP;
    }



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



}