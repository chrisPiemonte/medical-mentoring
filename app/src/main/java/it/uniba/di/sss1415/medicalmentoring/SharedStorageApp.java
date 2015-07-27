package it.uniba.di.sss1415.medicalmentoring;

import android.app.Application;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Chris on 20/07/2015.
 */

//  ------  classe per salvare alcuni dati in locale e raggiungibile da tutte le activity
public class SharedStorageApp extends Application {

    private DatiUtente userData;
    private static SharedStorageApp appStorage;
    private ArrayList<HashMap<String, String>> LeMieDisponibilita = new ArrayList<HashMap<String, String>>();
    private ArrayList<HashMap<String, String>> LeMieRichieste = new ArrayList<HashMap<String, String>>();
    private ArrayList<HashMap<String, String>> MieiAppuntamenti = new ArrayList<HashMap<String, String>>();



    // Returns the application instance
    public static SharedStorageApp getInstance() {
        return appStorage;
    }

    public final void onCreate() {
        super.onCreate();
        appStorage = this;
    }


    public void setDatiUtente(String data){
        userData = new DatiUtente(data);
    }

    public DatiUtente getDatiUtente(){
        return userData;
    }










    public ArrayList<HashMap<String, String>> getMieiAppuntamenti(){
        return MieiAppuntamenti;
    }

    public void addMieiAppuntamenti(HashMap<String, String> map){
        MieiAppuntamenti.add(map);
        Parametri.sort(MieiAppuntamenti);
    }

    public void setMieiAppuntamenti(ArrayList<HashMap<String, String>> map){
        MieiAppuntamenti = map;
    }

    public void cleanMieiAppuntamenti(){
        MieiAppuntamenti = new ArrayList<HashMap<String, String>>();
    }






    // RICHIESTE

    public ArrayList<HashMap<String, String>> getLeMieRichieste(){
        return LeMieRichieste;
    }

    public void cleanLeMieRichieste() {
        LeMieRichieste = new ArrayList<HashMap<String, String>>();
    }

    public void removeRichiesta(HashMap<String,String> map) {
        HashMap<String, String> check = new HashMap<String, String>();
        for (int i = 0; i < LeMieRichieste.size(); i++) {

            check = LeMieRichieste.get(i);
            if (map.get("intervento").equals(check.get("intervento"))) {
                if (map.get("oraInizio").equals(check.get("oraInizio"))) {
                    if (map.get("oraFine").equals(check.get("oraFine"))) {
                        if (map.get("data").equals(check.get("data"))) {
                            LeMieRichieste.remove(i);
                            break;
                        }
                    }
                }
            }
        }
    }



    // DISPONIBILITà
    public ArrayList<HashMap<String, String>> getLeMieDisponibilita(){
        return LeMieDisponibilita;
    }

    public void cleanLeMieDisponibilita(){
        LeMieDisponibilita = new ArrayList<HashMap<String, String>>();
    }

    public void removeDisponibilita(HashMap<String,String> map){
        HashMap<String,String> check = new HashMap<String,String>();
        for( int i = 0; i < LeMieDisponibilita.size(); i++){

            check = LeMieDisponibilita.get(i);
            if(map.get("data").equals(check.get("data"))){
                if(map.get("oraInizio").equals(check.get("oraInizio"))){
                    if(map.get("oraFine").equals(check.get("oraFine"))) {
                        if (map.get("ripetizione").equals(check.get("ripetizione"))) {
                            if (map.get("fineRipetizione").equals(check.get("fineRipetizione"))) {
                                LeMieDisponibilita.remove(i);
                                break;
                            }


                        }
                    }

                }
            }

        }





    }



}