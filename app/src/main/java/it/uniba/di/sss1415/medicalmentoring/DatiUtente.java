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

    // classe per salvare in locale i dati del profilo
    // Divide la string ricevuti dal server al momento del login e del modifica profilo
    DatiUtente(String dati) {


        String[] datiRicevuti = dati.toString().split(",", 13);

        email = datiRicevuti[0];
        pass = datiRicevuti[1];
        score = datiRicevuti[2];
        provinciaIscrizione = datiRicevuti[3];
        numeroIscr = datiRicevuti[4];
        annoIscr = datiRicevuti[5];
        nome = datiRicevuti[6];
        cognome = datiRicevuti[7];

        try {
            primariaEx = datiRicevuti[11];
        } catch (Exception e){
            e.printStackTrace();
        }
        if (primariaEx == null || primariaEx.equals(""))
            primariaEx = datiRicevuti[8];

        Log.i("PRIMARIA = ", primariaEx);
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
