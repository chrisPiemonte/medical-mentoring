package it.uniba.di.sss1415.medicalmentoring;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Chris on 20/07/2015.
 */
public class JSONManager {


    public static ArrayList<HashMap<String,String>> toListOfMap(String result, String nameArray){

        JSONArray jArray = null;
        ArrayList<HashMap<String,String>> listaApp = new ArrayList<HashMap<String,String>>();
        Log.i("JSONMAN_JSON RESULT = ", result);

        try {

            JSONObject oggettoJson = new JSONObject(result);
            jArray = oggettoJson.getJSONArray(nameArray);

        } catch(Exception e){
            e.printStackTrace();
            System.out.println("JSONMAN_UANNA'");
        }

        for (int i = 0; i < jArray.length(); i++){

            HashMap<String,String> map = new HashMap<String, String>();
            JSONObject JSONItem = null;

            try {
                JSONItem = jArray.getJSONObject(i);
                Iterator it = JSONItem.keys();

                while(it.hasNext()){
                    String current = (String) it.next();
                    map.put(current, JSONItem.getString(current));
                    Log.i("JSONMAN_CURRENT = ", current);
                }

                listaApp.add(map);

            }catch(JSONException e){
                e.printStackTrace();
            }

        }

        return listaApp;
    }

    public static List<String> toList(String data){

        ArrayList<String> list = new ArrayList<String>();
        //JSONArray jsonArray = (JSONArray)jsonObject;
        try {
            JSONArray jArray = new JSONArray(data);
            if (jArray.length() > 0) {
                for (int i = 0; i < jArray.length(); i++){
                    list.add(jArray.get(i).toString());
                }
            }
        } catch(Exception e){
            e.printStackTrace();
        }

        return list;
    }

}
