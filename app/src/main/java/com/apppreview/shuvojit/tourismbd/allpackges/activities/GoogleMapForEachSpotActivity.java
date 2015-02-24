package com.apppreview.shuvojit.tourismbd.allpackges.activities;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.apppreview.shuvojit.tourismbd.R;
import com.apppreview.shuvojit.tourismbd.allpackges.adapters.googleMapInfoWindowAdapters.InfoWindowAdapterForEachSpot;
import com.apppreview.shuvojit.tourismbd.allpackges.infos.LatLongInfo;
import com.apppreview.shuvojit.tourismbd.allpackges.interfaces.GoogleMapInterface;
import com.apppreview.shuvojit.tourismbd.allpackges.interfaces.Intializer;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;



public class GoogleMapForEachSpotActivity extends ActionBarActivity implements
        GoogleMapInterface, Intializer {

    private MapFragment mapFragement;
    private GoogleMap googleMap;
    private Intent intent;
    private LatLongInfo latLongInfo;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.google_map_layout);
        intialize();
        googleMap.setInfoWindowAdapter(new InfoWindowAdapterForEachSpot(this));
        googleMap.setOnInfoWindowClickListener(listener);
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources()
                .getColor(R.color.darkred)));
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public void intialize() {
        actionBar = getSupportActionBar();
        mapFragement = (MapFragment) getFragmentManager().findFragmentById(
                R.id.googleMap);
        googleMap = mapFragement.getMap();
        intent = getIntent();
        latLongInfo = (LatLongInfo) intent
                .getSerializableExtra("SpotLatLongInfo");
        setTitle(latLongInfo.getSpotName());
        setPostionOnGoogleMap(latLongInfo.getSpotName(),
                latLongInfo.getSpotSnippet(), latLongInfo.getSpotType(),
                latLongInfo.getLatitudeVal(), latLongInfo.getLongtitudeVal());

    }

    OnInfoWindowClickListener listener = new OnInfoWindowClickListener() {

        @Override
        public void onInfoWindowClick(Marker marker) {
            Toast.makeText(getApplicationContext(),
                    marker.getTitle() + "\n" + marker.getSnippet(),
                    Toast.LENGTH_LONG).show();

        }
    };

    private void setPostionOnGoogleMap(String spotTitle, String spotSnippet,
                                       String spotType, double latitude, double longitude) {

        LatLng spotLatLng = new LatLng(latitude, longitude);
        googleMap.moveCamera(CameraUpdateFactory
                .newLatLngZoom(spotLatLng, 7.0f));
        setMarkerOnThePosition(spotLatLng, spotTitle, spotSnippet, spotType);

    }

    private void setMarkerOnThePosition(LatLng spotLatLng, String spotTitle,
                                        String spotSnippet, String spotType) {
        MarkerOptions mapMarkerOptions = new MarkerOptions()
                .position(spotLatLng).title(spotTitle).snippet(spotSnippet);
        Marker spotMarker = this.googleMap.addMarker(mapMarkerOptions);
        setMarkerIcons(spotMarker, spotType);

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
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setMarkerIcons(Marker marker, String spotType) {
        switch (spotType) {
            case "Heritage Site":
                marker.setIcon(BitmapDescriptorFactory
                        .fromResource(R.drawable.marker_heritage_site));

                break;
            case "Beaches":
                marker.setIcon(BitmapDescriptorFactory
                        .fromResource(R.drawable.marker_beach));

                break;
            case "Hill stations":
                marker.setIcon(BitmapDescriptorFactory
                        .fromResource(R.drawable.marker_hill_stations));

                break;
            case "Island":
                marker.setIcon(BitmapDescriptorFactory
                        .fromResource(R.drawable.marker_island));

                break;
            case "Wildlife":
                marker.setIcon(BitmapDescriptorFactory
                        .fromResource(R.drawable.marker_wildlife));

                break;
            case "Waterfall":
                marker.setIcon(BitmapDescriptorFactory
                        .fromResource(R.drawable.marker_waterfall));

                break;
            case "Archaeological Sites":
                marker.setIcon(BitmapDescriptorFactory
                        .fromResource(R.drawable.marker_archeological_sites));

                break;
            case "Architecture":
                marker.setIcon(BitmapDescriptorFactory
                        .fromResource(R.drawable.marker_architecture));

                break;
            case "Religious":
                switch (marker.getTitle()) {
                    case "Kantajew Temple":
                        marker.setIcon(BitmapDescriptorFactory
                                .fromResource(R.drawable.marker_temple));
                        break;
                    case "Armenian Church":
                        marker.setIcon(BitmapDescriptorFactory
                                .fromResource(R.drawable.marker_chruch));
                        break;
                    case "Buddha Dhatu Jadi":
                        marker.setIcon(BitmapDescriptorFactory
                                .fromResource(R.drawable.marker_pagoda));
                        break;

                }
                break;
            case "National Monuments":
                marker.setIcon(BitmapDescriptorFactory
                        .fromResource(R.drawable.marker_national_monument));

                break;

        }

    }

}

