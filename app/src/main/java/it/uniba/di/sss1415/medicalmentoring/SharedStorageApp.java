package it.uniba.di.sss1415.medicalmentoring;

import android.app.Application;

/**
 * Created by Chris on 20/07/2015.
 */
public class SharedStorageApp extends Application {

    private DatiUtente userData;
    private static SharedStorageApp appStorage;

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

}