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


    public ArrayList<HashMap<String, String>> getLeMieDisponibilita(){
        return LeMieDisponibilita;
    }


    public ArrayList<HashMap<String, String>> getLeMieRichieste(){
        return LeMieRichieste;
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



    public void cleanLeMieDisponibilita(){
        LeMieDisponibilita = new ArrayList<HashMap<String, String>>();
    }





    public void cleanLeMieRichieste() {
        LeMieRichieste = new ArrayList<HashMap<String, String>>();
    }

}