package it.uniba.di.sss1415.medicalmentoring;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Chris on 20/07/2015.
 */
public class ServerManager {

    static String method = "GET";
    private static String response;

    public static String sendRequest(String methodd, final String parametriServer){

        method = methodd;

        class AppuntamentiAsyncTask extends AsyncTask<String,Void,String> {

            @Override
            protected String doInBackground(String...params) {

                String parametri = params[0];  Log.i("PARAMETRI INVIATI = ", parametri);
                HttpURLConnection conn = null;

                //  ------  stabilisce la connessione, manda e riceve i dati
                try {
                    URL url = new URL("http://www.di.uniba.it/~buono/SSS/casidistudio/consulenze/script_php/accessoFile.php");
                    System.out.println(url.toString());
                    conn = (HttpURLConnection) url.openConnection();

                    conn.setRequestMethod(method);
                    conn.setRequestProperty("Content-Type", "application/json");
                    conn.setRequestProperty("Accept", "application/json");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);

                    DataOutputStream body = new DataOutputStream(conn.getOutputStream());
                    body.writeBytes(parametri);
                    body.flush();
                    body.close();

                    int responseCode = conn.getResponseCode();
                    Log.i("SERVER_CODICE RISPI = ", Integer.toString(responseCode));

                    BufferedReader in =  new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String inputLine = "";

                    while( (inputLine = in.readLine()) != null){
                        response.append(inputLine);
                        //System.out.println(inputLine + "----------------------------");
                    }
                    in.close();
                    conn.disconnect();

                    Log.i("SERVER RICEVUTAH = ", "'" + response.toString() + "'");
                    return response.toString();

                } catch (IOException e) {
                    e.printStackTrace();
                    Log.i("SERVER_ERRORACCIO = ", e.toString());
                } catch(Exception e){
                    Log.i("SERVER_ERRORACCIO2 = ", e.toString());
                }

                return "failed";
            }

            @Override
            protected void onPostExecute(String result){
                super.onPostExecute(result);

                response = result;
            }
        }
        try {

            AppuntamentiAsyncTask inviaRichiestaAppuntamenti = new AppuntamentiAsyncTask();
            response = inviaRichiestaAppuntamenti.execute(parametriServer).get();
        }catch(Exception e){
            e.printStackTrace();
            response = "failed";
        }
        return response;
    }




}