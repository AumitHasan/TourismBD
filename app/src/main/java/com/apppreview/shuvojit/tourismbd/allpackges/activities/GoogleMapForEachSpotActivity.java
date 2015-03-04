package com.apppreview.shuvojit.tourismbd.allpackges.activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.apppreview.shuvojit.tourismbd.R;
import com.apppreview.shuvojit.tourismbd.allpackges.fragments.GoogleMapForAllSpotsFragment;
import com.apppreview.shuvojit.tourismbd.allpackges.infos.LatLongInfo;
import com.apppreview.shuvojit.tourismbd.allpackges.interfaces.InitializerClient;

import java.util.ArrayList;


public class GoogleMapForEachSpotActivity extends ActionBarActivity implements
        InitializerClient {
    private ActionBar actionBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.google_map_for_each_for_activity);
        initialize();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public void initialize() {
        actionBar = getSupportActionBar();
        Intent intent = getIntent();
        LatLongInfo latLongInfo = (LatLongInfo) intent
                .getSerializableExtra("SpotLatLongInfo");
        actionBar.setTitle(latLongInfo.getSpotName());
        ArrayList<LatLongInfo> latLongInfoArrayList = new ArrayList<LatLongInfo>();
        latLongInfoArrayList.add(latLongInfo);
        Fragment fragment = GoogleMapForAllSpotsFragment.getNewInstance(latLongInfoArrayList,
                latLongInfo.getLatitudeVal(), latLongInfo.getLongtitudeVal());
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().
                beginTransaction();
        fragmentTransaction.replace(R.id.content_fragment, fragment).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.google_map_for_each_spot, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();

        }
        return true;
    }

}

