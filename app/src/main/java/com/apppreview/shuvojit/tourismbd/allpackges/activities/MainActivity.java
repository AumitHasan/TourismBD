package com.apppreview.shuvojit.tourismbd.allpackges.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.apppreview.shuvojit.tourismbd.R;
import com.apppreview.shuvojit.tourismbd.allpackges.adapters.listViewAdapters.NavDrawerListViewAdapter;
import com.apppreview.shuvojit.tourismbd.allpackges.fragments.AboutOurAppFragment;
import com.apppreview.shuvojit.tourismbd.allpackges.fragments.AllSiteTypeListFragment;
import com.apppreview.shuvojit.tourismbd.allpackges.fragments.DocumentryVideoListFragment;
import com.apppreview.shuvojit.tourismbd.allpackges.fragments.FavourtiesFragment;
import com.apppreview.shuvojit.tourismbd.allpackges.fragments.GoogleMapForAllSpotsFragment;
import com.apppreview.shuvojit.tourismbd.allpackges.fragments.HomeFragment;
import com.apppreview.shuvojit.tourismbd.allpackges.interfaces.Intializer;


public class MainActivity extends ActionBarActivity implements Intializer {

    private DrawerLayout drawerLayout;
    private ListView navDrawerListView;
    private ActionBarDrawerToggle navDrawerListener;
    private NavDrawerListViewAdapter navDrawerListViewAdapter;
    private String[] navDrawerlistItem;

    OnItemClickListener navDrawerItemListener = new OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {

            selectPostion(position);
        }

        private void selectPostion(int position) {

            navDrawerListView.setItemChecked(position, true);
            setTitle(navDrawerlistItem[position]);
            Log.e(getClass().getName(), navDrawerlistItem[position]
                    + " is selected");
            setFragment(position);

        }
    };
    private FragmentManager fragmentManager;
    private AllSiteTypeListFragment allSiteTypeListFragment;
    private HomeFragment homeFragment;
    private GoogleMapForAllSpotsFragment googleMapForAllSpotsFragment;
    private AboutOurAppFragment aboutOurAppFragment;
    private DocumentryVideoListFragment documentryVideoListFragment;
    private FavourtiesFragment favourtiesFragment;
    private ActionBar actionBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_layout);
        intialize();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources()
                .getColor(R.color.darkred)));
        homeFragment = new HomeFragment();
        fragmentManager.beginTransaction()
                .replace(R.id.content_fragment, homeFragment).commit();
    }

    @Override
    public void intialize() {
        fragmentManager = getFragmentManager();
        actionBar = getSupportActionBar();
        navDrawerlistItem = getResources().getStringArray(
                R.array.nav_drawer_categories);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navDrawerListView = (ListView) findViewById(R.id.left_nav_drawer_list_view);
        navDrawerListViewAdapter = new NavDrawerListViewAdapter(
                MainActivity.this);
        navDrawerListView.setAdapter(navDrawerListViewAdapter);
        navDrawerListView.setOnItemClickListener(navDrawerItemListener);
        navDrawerListener = new ActionBarDrawerToggle(this, drawerLayout,
                R.string.drawer_open, R.string.drawer_close) {

            @Override
            public void onDrawerOpened(View drawerView) {

                super.onDrawerOpened(drawerView);

            }

            @Override
            public void onDrawerClosed(View drawerView) {

                super.onDrawerClosed(drawerView);

            }

        };
        navDrawerListener.setHomeAsUpIndicator(R.drawable.ic_drawer);
        drawerLayout.setDrawerListener(navDrawerListener);

    }

    private void setFragment(int position) {
        String navDrawerCategory = navDrawerlistItem[position];
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = null;
        switch (navDrawerCategory) {
            case "Maps":
                fragment = new GoogleMapForAllSpotsFragment();
                break;
            case "Home":
                fragment = new HomeFragment();
                break;
            case "Documentry Videos":
                fragment = new DocumentryVideoListFragment();
                break;
            case "Favourites":
                fragment = new FavourtiesFragment();
                break;
            case "About":
                fragment = new AboutOurAppFragment();
                break;
            default:
                fragment = AllSiteTypeListFragment.newInstance(navDrawerCategory);
                break;
        }
        if (fragment != null && fragmentTransaction != null) {
            fragmentTransaction.replace(R.id.content_fragment, fragment).commit();
        }

        drawerLayout.closeDrawers();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (navDrawerListener.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {

        super.onPostCreate(savedInstanceState);
        navDrawerListener.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {

        super.onConfigurationChanged(newConfig);
        navDrawerListener.onConfigurationChanged(newConfig);
    }

}


