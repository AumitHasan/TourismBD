package com.apppreview.shuvojit.tourismbd.allpackges.fragments;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.apppreview.shuvojit.tourismbd.R;
import com.apppreview.shuvojit.tourismbd.allpackges.activities.TouristSpotInfoActivity;
import com.apppreview.shuvojit.tourismbd.allpackges.adapters.googleMapInfoWindowAdapters.InfoWindowAdapterForEachSpot;
import com.apppreview.shuvojit.tourismbd.allpackges.databaseTablesModel.LatLongInfoOfAllSpotsTable;
import com.apppreview.shuvojit.tourismbd.allpackges.databaseTablesModel.SpotInfoTable;
import com.apppreview.shuvojit.tourismbd.allpackges.interfaces.GoogleMapClient;
import com.apppreview.shuvojit.tourismbd.allpackges.interfaces.InitializerClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class GoogleMapForAllSpotsFragment extends Fragment implements
        GoogleMapClient, InitializerClient {


    private OnInfoWindowClickListener infoWindowClickListener = new OnInfoWindowClickListener() {

        @Override
        public void onInfoWindowClick(Marker marker) {

            if (getActivity().getClass().getSimpleName().equalsIgnoreCase("MainActivity")) {
                LatLongInfoOfAllSpotsTable latLongInfo = getLatLngInfo(marker);
                setSpotActivity(latLongInfo.getSpotName(), latLongInfo.getSpotTypeField());

            } else {
                Toast.makeText(context, marker.getSnippet(), Toast.LENGTH_LONG).show();
            }


        }
    };
    private Context context;
    private MapFragment mapFragment;
    private GoogleMap googleMap;
    private List<LatLongInfoOfAllSpotsTable> latLongInfoList;
    private ArrayList<Marker> allMarkers;
    private MarkerOptions markerOptions;
    private View fragmentView;
    private FragmentManager fragmentManager;
    private double cameraLatVal;
    private double cameraLngVal;
    private float zoomLevel;

    public GoogleMapForAllSpotsFragment() {

    }

    public static GoogleMapForAllSpotsFragment getNewInstance(
            ArrayList<LatLongInfoOfAllSpotsTable> latLongInfoList,
            double cameraMoveLatVal,
            double cameraMoveLngVal,
            float zoomLevel
    ) {
        GoogleMapForAllSpotsFragment googleMapForAllSpotsFragment = new
                GoogleMapForAllSpotsFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("Lat long info list", latLongInfoList);
        bundle.putDouble("Camera Move Lat Val", cameraMoveLatVal);
        bundle.putDouble("Camera Move Lng Val", cameraMoveLngVal);
        bundle.putFloat("Zoom Level", zoomLevel);
        googleMapForAllSpotsFragment.setArguments(bundle);
        return googleMapForAllSpotsFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            latLongInfoList = (List<LatLongInfoOfAllSpotsTable>) bundle.
                    getSerializable("Lat long info list");
            cameraLatVal = bundle.getDouble("Camera Move Lat Val");
            cameraLngVal = bundle.getDouble("Camera Move Lng Val");
            zoomLevel = bundle.getFloat("Zoom Level");

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentView = inflater.inflate(R.layout.google_map_layout, container,
                false);
        initialize();
        googleMap.setInfoWindowAdapter(new InfoWindowAdapterForEachSpot(
                context));
        googleMap.setOnInfoWindowClickListener(infoWindowClickListener);
        //googleMap.setMapType(GoogleMap.MAP_TYPE_NONE);
        return fragmentView;
    }

    @Override
    public void initialize() {
        if (fragmentView != null) {
            context = getActivity();
            fragmentManager = getActivity().getFragmentManager();
            mapFragment = (MapFragment) fragmentManager.findFragmentById(R.id.googleMap);
            googleMap = mapFragment.getMap();
            setGoogleMapCameraMove(cameraLatVal, cameraLngVal, zoomLevel);
            setAllMarkersOnMap();
            UiSettings googleMapUiSettings = googleMap.getUiSettings();
            googleMapUiSettings.setMapToolbarEnabled(false);
        }
    }

    private void setGoogleMapCameraMove(double cameraLatVal, double cameraLngVal,
                                        float zoomLevel) {
        if (googleMap != null) {
            LatLng spotLatLng = new LatLng(cameraLatVal, cameraLngVal);
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(spotLatLng,
                    zoomLevel));
        }
    }

    private void setAllMarkersOnMap() {
        LatLongInfoOfAllSpotsTable latLongInfo = null;
        Marker marker = null;
        allMarkers = new ArrayList<Marker>();
        if (latLongInfoList != null && latLongInfoList.size() > 0) {
            for (int i = 0; i < latLongInfoList.size(); i++) {
                latLongInfo = latLongInfoList.get(i);
                markerOptions = new MarkerOptions()
                        .position(
                                new LatLng(latLongInfo.getSpotlatitudefield(),
                                        latLongInfo.getSpotLongtitudeField()))
                        .title(latLongInfo.getSpotName())
                        .snippet(latLongInfo.getSpotSnippet());
                marker = googleMap.addMarker(markerOptions);
                setMarkerIcons(marker, latLongInfo.getSpotTypeField());
                allMarkers.add(marker);
            }
        }
    }

    @Override
    public void onDestroyView() {

        try {
            fragmentManager.beginTransaction().remove(mapFragment).commit();
            //remove(mapFragment).commit();
            googleMap = null;
            mapFragment = null;
            fragmentManager = null;
            fragmentView = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroyView();
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

    private LatLongInfoOfAllSpotsTable getLatLngInfo(Marker marker) {
        LatLongInfoOfAllSpotsTable latLongInfo = null;
        for (int i = 0; i <= allMarkers.size(); i++) {
            String spotName = allMarkers.get(i).getTitle();
            if (spotName.equalsIgnoreCase(marker.getTitle())) {
                latLongInfo = latLongInfoList.get(i);
                break;
            }
        }
        return latLongInfo;
    }

    private void setSpotActivity(String spotName, String spotType) {
        SpotInfoTable spotInfo = SpotInfoTable.getSpotInfoTableData(spotName, spotType);
        LatLongInfoOfAllSpotsTable latLongInfo = LatLongInfoOfAllSpotsTable.
                getLatLongInfoOfSpotTableData(spotName, spotType);
        if (spotInfo != null && latLongInfo != null) {
            Log.d(getClass().getName(), "found");
            Intent intent = new Intent(context, TouristSpotInfoActivity.class);
            intent.putExtra("SpotInfo", spotInfo);
            intent.putExtra("SpotLatLongInfo", latLongInfo);
            context.startActivity(intent);
        }

    }
}
