package it.uniba.di.sss1415.medicalmentoring;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;


public class Appuntamenti extends ListFragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private static final String TIPO_ELEMENTO = "appuntamenti";
    private static final String ACCESSO = "read";

    // lista dei parametri da inviare al server
    private String param;

    ArrayList<HashMap<String,String>> listaApp = new ArrayList<HashMap<String,String>>();
    public ListAdapter adapter;


     public static Appuntamenti newInstance(String param1, String param2) {
        Appuntamenti fragment = new Appuntamenti();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public Appuntamenti() {
    }


    @Override
    public void onResume() {
        super.onResume();

        param = Parametri.generaParametri(TIPO_ELEMENTO, ACCESSO, "");  Log.i("JSON IN RISP = ", "");
        String serverAnswer = ServerManager.sendRequest("POST", param);

        listaApp = JSONManager.toListOfMap(serverAnswer,"appuntamenti");

        adapter = new SimpleAdapter(getActivity(),
                listaApp,
                R.layout.item_appuntamento,
                new String[] {"data","oraInizio","oraFine","tipoAppuntamento","intervento","dottore"},
                new int[]{R.id.textViewData, R.id.textViewOraInizio, R.id.textViewOraFine,
                        R.id.textViewTipoAppuntamento, R.id.textViewIntervento, R.id.textViewDottore});
        setListAdapter(adapter);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_appuntamenti, container, false);
    }




}
