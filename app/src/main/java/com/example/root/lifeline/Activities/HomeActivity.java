package com.example.root.lifeline.Activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.root.lifeline.Profiles.DocProfile;
import com.example.root.lifeline.R;
import com.example.root.lifeline.adapters.FirstAidRecyclerView;
import com.example.root.lifeline.adapters.GridItemDecorator;
import com.example.root.lifeline.models.FirstAid;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, SearchView.OnQueryTextListener {

    ArrayList<FirstAid> firstAidArrayList;
    FirstAidRecyclerView adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initView();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    private void initView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_firstAid);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),2);
        recyclerView.setLayoutManager(layoutManager);
        GridItemDecorator gridItemDecorator = new GridItemDecorator(getApplicationContext(),R.dimen.offset_dimen);
        recyclerView.addItemDecoration(gridItemDecorator);
        ArrayList<FirstAid> nearYouItemsArrayList = showItems();
        adapter = new FirstAidRecyclerView(showItems(),this);
        recyclerView.setAdapter(adapter);

    }

    private ArrayList<FirstAid> showItems() {

       firstAidArrayList = new ArrayList<>();
        FirstAid firstAid = new FirstAid("Accident",R.drawable.acccidentp);
        firstAidArrayList.add(firstAid);
        firstAid = new FirstAid("Fracture",R.drawable.fracture);
        firstAidArrayList.add(firstAid);
        firstAid = new FirstAid("Broken Toe",R.drawable.brokentoe);
        firstAidArrayList.add(firstAid);
        firstAid = new FirstAid("Shock",R.drawable.electric);
        firstAidArrayList.add(firstAid);
        firstAid = new FirstAid("Fire Burn",R.drawable.fireburn);
        firstAidArrayList.add(firstAid);
        firstAid = new FirstAid("Electrical Burn",R.drawable.electricburn);
        firstAidArrayList.add(firstAid);
        firstAid = new FirstAid("ToothAche",R.drawable.toothach);
        firstAidArrayList.add(firstAid);
        firstAid = new FirstAid("Breathing Problems",R.drawable.breathingproblems);
        firstAidArrayList.add(firstAid);
        firstAid = new FirstAid("Dog Bite",R.drawable.animalbite);
        firstAidArrayList.add(firstAid);
        firstAid = new FirstAid("Snake Bite",R.drawable.snakebite);
        firstAidArrayList.add(firstAid);
        firstAid = new FirstAid("Insect Bite",R.drawable.insectbite);
        firstAidArrayList.add(firstAid);
        firstAid = new FirstAid("Testicular Pain",R.drawable.testicularpain);
        firstAidArrayList.add(firstAid);
        firstAid = new FirstAid("Abdominal Pain",R.drawable.abdomenal);
        firstAidArrayList.add(firstAid);
        firstAid = new FirstAid("Chest Pain",R.drawable.cardiac);
        firstAidArrayList.add(firstAid);
        firstAid = new FirstAid("Fever",R.drawable.feverinchildren);
        firstAidArrayList.add(firstAid);
        firstAid = new FirstAid("Heat Exhaustion",R.drawable.heatexhuation);
        firstAidArrayList.add(firstAid);
        firstAid = new FirstAid("Heat Stroke",R.drawable.heatstroke);
        firstAidArrayList.add(firstAid);
        firstAid = new FirstAid("Hypothermia",R.drawable.hypothermia);
        firstAidArrayList.add(firstAid);
        firstAid = new FirstAid("Flu",R.drawable.feverinchildren);
        firstAidArrayList.add(firstAid);
        firstAid = new FirstAid("Heart Problems",R.drawable.cardiac);
        firstAidArrayList.add(firstAid);
        firstAid = new FirstAid("Motion Sickness",R.drawable.motion);
        firstAidArrayList.add(firstAid);
        firstAid = new FirstAid("Drowning",R.drawable.drowning);
        firstAidArrayList.add(firstAid);
        firstAid = new FirstAid("Dizziness",R.drawable.dizziness);
        firstAidArrayList.add(firstAid);
        firstAid = new FirstAid("Diarrhea",R.drawable.diarrahea);
        firstAidArrayList.add(firstAid);
        firstAid = new FirstAid("Urine Bleeding",R.drawable.nosebleeding);
        firstAidArrayList.add(firstAid);
        firstAid = new FirstAid("Nose Bleeding",R.drawable.nosebleeding);
        firstAidArrayList.add(firstAid);
        firstAid = new FirstAid("Anal Bleeding",R.drawable.nosebleeding);
        firstAidArrayList.add(firstAid);
        firstAid = new FirstAid("Sprain/Strain",R.drawable.sprain);
        firstAidArrayList.add(firstAid);
        firstAid = new FirstAid("Bruises",R.drawable.bruise);
        firstAidArrayList.add(firstAid);
        firstAid = new FirstAid("Choking",R.drawable.choke);
        firstAidArrayList.add(firstAid);
        firstAid = new FirstAid("Food Poison",R.drawable.foodpoison);
        firstAidArrayList.add(firstAid);


        return firstAidArrayList;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home, menu);
        SearchManager manager =(SearchManager) getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchViewItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchViewItem);
        searchView.setSearchableInfo(manager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(this);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.list) {
            startActivity(new Intent(this,CustomListActivity.class));
            // Handle the camera action
        } else if (id == R.id.homed) {

        } else if (id == R.id.chat) {
            startActivity(new Intent(this,DocProfile.class));

        }else if (id==R.id.prrofile){

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }
    private ArrayList<FirstAid> filter(ArrayList<FirstAid> fir, String query) {
        query = query.toLowerCase();
        final ArrayList<FirstAid>filteredList = new ArrayList<>();
        for (FirstAid customer1 : fir){
            final String text = customer1.getFirstAidName().toLowerCase();
            if (text.contains(query)) {
                filteredList.add(customer1);
            }
        }
        return filteredList;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        final ArrayList<FirstAid> customersList = filter(firstAidArrayList,newText);
        adapter.setFilter(customersList);
        return true;
    }
}
