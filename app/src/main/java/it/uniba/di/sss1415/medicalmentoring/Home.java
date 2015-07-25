package it.uniba.di.sss1415.medicalmentoring;

//import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.app.ActionBar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;


public class Home extends ActionBarActivity {

    CustomPagerAdapter mCustomPagerAdapter;
    ViewPager mViewPager;

    // Elementi per il Drawer
    private String[] menuItems;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    // Titoli nell' ActionBar quando il drawer menu è aperto/chiuso
    private CharSequence mDrawerTitle = "Menu";
    private CharSequence mTitle = "Home";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // ------ Per il menu attivabile dall' "Hamburger"
        menuItems = getResources().getStringArray(R.array.arrayDrawerMenu);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // ------ Per settare il bottone nell' ActionBar che fa aprire il menu laterale(Drawer)
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mTitle);
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle(mDrawerTitle);
            }
        };


        mDrawerLayout.setDrawerListener(mDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        // ------ genero le azioni da far intraprendere ad i vari bottoni del menu laterale
        mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, menuItems));
        mDrawerList.setOnItemClickListener(new ListView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                selectItem(position);
            }
        });


        // Per le tab a scorrimento
        mCustomPagerAdapter = new CustomPagerAdapter(getSupportFragmentManager(), this);

        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mCustomPagerAdapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event

        // ------ Suddivido le azioni dei tre bottoni presenti nell'action bar
        switch (item.getItemId()) {
            case R.id.action_disponibilita:
                nuovaDisp();
                return true;
            case R.id.action_search:
                mostraSearch();
                return true;
            case R.id.logout:
                SharedStorageApp.getInstance().cleanLeMieDisponibilita();
                SharedStorageApp.getInstance().cleanLeMieRichieste();
                finish();
                return true;
        }

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }


    // ------ da qui per le modifiche per i bottoni sulla barra
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);

        return super.onCreateOptionsMenu(menu);
    }

    //  ------ Metodi relativi ad i bottoni della action bar

    // porta a Inserisci richiesta
    public  void mostraSearch(){
        Intent apri = new Intent(Home.this, InserisciRichiesta.class);
        startActivity(apri);
    }

    public void nuovaDisp() {
        Intent apriDisp = new Intent(Home.this, InserisciDisponibilita.class);
        startActivity(apriDisp);
    }


    // CustomAdapter per le tab a scorrimento della ActionBar
    class CustomPagerAdapter extends FragmentPagerAdapter {

        Context mContext;

        public CustomPagerAdapter(FragmentManager fm, Context context) {
            super(fm);
            mContext = context;
        }

        @Override
        // ------ Imposto i fragment da aprire nell'activity
        public Fragment getItem(int pos) {

            if(pos == 0){
                return new Appuntamenti();
            }else{
                return new Notifiche();
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int pos) {
            if(pos == 0){
                return "APPUNTAMENTI";
            }else{
                return "NOTIFICHE";
            }
        }

    }

    // Metodo che apre una Activity dopo il click nel Drawer
    private void selectItem(int pos){

        switch(pos){
            case 0:
                Intent i = new Intent(Home.this, Profilo.class);
                startActivity(i);
                break;
            case 1:
                Intent in = new Intent(Home.this, LeMieRichieste.class);
                startActivity(in);
                break;
            case 2:
                Intent intent = new Intent(Home.this, LeMieDisponibilita.class);
                startActivity(intent);
                break;

        }
        if(pos == 0){
            Intent i = new Intent(Home.this, Login.class);
        }
        mDrawerList.setItemChecked(pos, true);
        mDrawerLayout.closeDrawer(mDrawerList);
    }



}

