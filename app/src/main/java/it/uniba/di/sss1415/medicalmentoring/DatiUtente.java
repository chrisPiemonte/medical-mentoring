package it.uniba.di.sss1415.medicalmentoring;

import android.util.Log;

import java.util.List;

/**
 * Created by Chris on 20/07/2015.
 */
public class DatiUtente {
    //membri classe
    private String email;
    private String pass;

    private String nome;
    private String cognome;

    private String score;

    private String provinciaIscrizione;
    private String annoIscr;
    private String numeroIscr;

    private List<String> listaExpertise;
    private String primariaEx; //prima expertise
    private List<String> expertiseRicevute;


    DatiUtente(String dati) {


        String[] datiRicevuti = dati.toString().split(",", 10);

        email = datiRicevuti[0];
        pass = datiRicevuti[1];
        score = datiRicevuti[2];
        provinciaIscrizione = datiRicevuti[3];
        numeroIscr = datiRicevuti[4];
        annoIscr = datiRicevuti[5];
        nome = datiRicevuti[6];
        cognome = datiRicevuti[7];
        primariaEx = datiRicevuti[8];
        Log.i("PRIMARIA = ", primariaEx);
/*
        try{
            JSONObject j = new JSONObject();
            j.put("email", "gigi@afa.com");
            j.put("nome", "gigi");
            j.put("cognome", "pigi");
            j.put("provincia", "Foggia (FG)");
            j.put("anno", "2000");
            j.put("numero", "1000");
            j.put("primaEx", "Neonatologia");
            j.put("altreEx", "Medicina sportiva");

            String param = Parametri.generaParametri("modificaP", "change", j.toString());
            String serverAnswer = Server.sendRequest("POST", param);
            Log.i("DATI_UTENTE_INV = ", param);
            Log.i("DATI_UTENTE_RIC = ", serverAnswer + " -.-");
        } catch( Exception e){
            e.printStackTrace();
            Log.i("DATI_UTENTE_MALE = ", "MALE");
        }*/

        //listaExpertise = JSONManager.toList(datiRicevuti[9]);

    }



    //metodi di getter e setter
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getProvinciaIscrizione() {
        return provinciaIscrizione;
    }

    public void setProvinciaIscrizione(String provinciaIscrizione) {
        this.provinciaIscrizione = provinciaIscrizione;
    }

    public String getAnnoIscr() {
        return annoIscr;
    }

    public void setAnnoIscr(String annoIscr) {
        this.annoIscr = annoIscr;
    }

    public String getNumeroIscr() {
        return numeroIscr;
    }

    public void setNumeroIscr(String numeroIscr) {
        this.numeroIscr = numeroIscr;
    }

    public List<String> getListaExpertise() {
        return listaExpertise;
    }

    public void setListaExpertise(List<String> listaExpertise) {
        this.listaExpertise = listaExpertise;
    }

    public String getPrimariaEx() {
        return primariaEx;
    }

    public void setPrimariaEx(String primariaEx) {
        this.primariaEx = primariaEx;
    }

    public List<String> getExpertiseRicevute() {
        return expertiseRicevute;
    }

    public void setExpertiseRicevute(List<String> expertiseRicevute) {
        this.expertiseRicevute = expertiseRicevute;
    }
}
