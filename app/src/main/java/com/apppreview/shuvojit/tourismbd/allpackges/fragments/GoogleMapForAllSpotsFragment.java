package com.apppreview.shuvojit.tourismbd.allpackges.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.apppreview.shuvojit.tourismbd.R;
import com.apppreview.shuvojit.tourismbd.allpackges.adapters.googleMapInfoWindowAdapters.InfoWindowAdapterForEachSpot;
import com.apppreview.shuvojit.tourismbd.allpackges.databases.TourismGuiderDatabase;
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

import java.util.ArrayList;

public class GoogleMapForAllSpotsFragment extends Fragment implements
        GoogleMapInterface, Intializer {

    OnInfoWindowClickListener infoWindowClickListener = new OnInfoWindowClickListener() {

        @Override
        public void onInfoWindowClick(Marker marker) {
            Toast.makeText(context,
                    marker.getTitle() + "\n" + marker.getSnippet(),
                    Toast.LENGTH_LONG).show();

        }
    };
    private Context context;
    private MapFragment mapFragment;
    private GoogleMap googleMap;
    private TourismGuiderDatabase tourismGuiderDatabase;
    private ArrayList<LatLongInfo> latLongInfoList;
    private ArrayList<Marker> allMarkers;
    private MarkerOptions markerOptions;
    private View fragmentView;
    private FragmentManager fragmentManager;

    public GoogleMapForAllSpotsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (fragmentView == null && mapFragment == null && googleMap == null) {
            fragmentView = inflater.inflate(R.layout.google_map_layout, container,
                    false);
            intialize();
            setGoogleMapCameraMove();
            setAllMarkersOnMap();
            googleMap.setInfoWindowAdapter(new InfoWindowAdapterForEachSpot(
                    context));
            googleMap.setOnInfoWindowClickListener(infoWindowClickListener);
        }
        return fragmentView;

    }

    @Override
    public void intialize() {
        context = getActivity();
        fragmentManager = getActivity().getFragmentManager();
        mapFragment = (MapFragment) fragmentManager
                .findFragmentById(R.id.googleMap);
        googleMap = mapFragment.getMap();
        tourismGuiderDatabase = TourismGuiderDatabase.getTourismGuiderDatabase(context);

    }

    private void setGoogleMapCameraMove() {
        if (googleMap != null) {
            LatLng Bangladesh = new LatLng(23.7000, 90.3500);
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Bangladesh,
                    6.0f));
        }
    }

    private void getAllLatlongInfoFromDB() {
        latLongInfoList = tourismGuiderDatabase.getLatLongInfo();
        allMarkers = new ArrayList<Marker>();
    }

    private void setAllMarkersOnMap() {
        getAllLatlongInfoFromDB();
        LatLongInfo latLongInfo = null;
        Marker marker = null;
        if (latLongInfoList != null && latLongInfoList.size() > 0) {
            for (int i = 0; i < latLongInfoList.size(); i++) {
                latLongInfo = latLongInfoList.get(i);
                markerOptions = new MarkerOptions()
                        .position(
                                new LatLng(latLongInfo.getLatitudeVal(),
                                        latLongInfo.getLongtitudeVal()))
                        .title(latLongInfo.getSpotName())
                        .snippet(latLongInfo.getSpotSnippet());
                marker = googleMap.addMarker(markerOptions);
                setMarkerIcons(marker, latLongInfo.getSpotType());
                allMarkers.add(marker);

            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.remove(mapFragment);
        ft.commit();
        googleMap = null;
        mapFragment = null;
        fragmentView = null;
        Log.e(getClass().getName(), "map removed");

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        tourismGuiderDatabase.close();
        Log.e(getClass().getName(), "Database close");
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
