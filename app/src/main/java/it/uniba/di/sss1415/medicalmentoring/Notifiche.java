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
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Notifiche.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Notifiche#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Notifiche extends ListFragment {

    ArrayList<HashMap<String,String>> listaApp = new ArrayList<HashMap<String,String>>();
    public ListAdapter adapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Notifiche.
     */
    // TODO: Rename and change types and number of parameters
    public static Notifiche newInstance(String param1, String param2) {
        Notifiche fragment = new Notifiche();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    
    public Notifiche() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String s = "{\"richieste\":[{\"data\":\"2015-02-28\",\"oraInizio\":\"15:00\",\"oraFine\":\"19:00\",\"intervento\":\"Colicisti\",\"dottore\":\"Pinco Panco\"},{\"data\":\"2015-09-10\",\"oraInizio\":\"09:00\",\"oraFine\":\"11:00\",\"intervento\":\"Cardiologia\",\"dottore\":\"Panco Pinco\"}]}";

        Parametri dizUno = new Parametri("richiesteValutare");
        dizUno.value = new String[]{"2015-02-28", "15:00", "19:00", "Colicisti", "Pinco Panco"};

        Parametri dizDue = new Parametri("richiesteValutare");
        dizDue.value = new String[]{"2015-09-10", "09:00", "11:00", "Cardiologia", "Panco Pinco"};
        JSONArray jArray = new JSONArray();
        jArray.put(dizUno.toJsonObj().toString());
        jArray.put(dizDue.toJsonObj().toString());

        JSONObject jObj = new JSONObject();
        try{
            jObj.put("richieste", jArray);
        } catch(Exception e){
            e.printStackTrace();
        }

        Log.i("PROVA", jObj.toString().replaceAll("\\\\", "") );
        listaApp = JSONManager.toListOfMap( s, "richieste" );

        adapter = new SimpleAdapter(getActivity(),
                listaApp,
                R.layout.item_notifica,
                new String[] {"data","oraInizio","oraFine","intervento","dottore"},
                new int[]{R.id.textViewData, R.id.textViewOraInizio, R.id.textViewOraFine,
                          R.id.textViewIntervento, R.id.textViewDottore});
        setListAdapter(adapter);


    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notifiche, container, false);
    }

    
}
