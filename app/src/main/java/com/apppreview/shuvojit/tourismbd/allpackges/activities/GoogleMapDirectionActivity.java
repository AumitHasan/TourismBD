package com.apppreview.shuvojit.tourismbd.allpackges.activities;


import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.apppreview.shuvojit.tourismbd.R;
import com.apppreview.shuvojit.tourismbd.allpackges.adapters.googleMapInfoWindowAdapters.InfoWindowAdapterForEachSpot;
import com.apppreview.shuvojit.tourismbd.allpackges.databaseTablesModel.LatLongInfoOfAllSpotsTable;
import com.apppreview.shuvojit.tourismbd.allpackges.interfaces.DirectionApiJsonClient;
import com.apppreview.shuvojit.tourismbd.allpackges.interfaces.GoogleMapClient;
import com.apppreview.shuvojit.tourismbd.allpackges.interfaces.InitializerClient;
import com.apppreview.shuvojit.tourismbd.allpackges.locationsServices.UserLocation;
import com.apppreview.shuvojit.tourismbd.allpackges.webServices.GoogleMapDirectionJsonAPIWebService;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class GoogleMapDirectionActivity extends ActionBarActivity implements
        GoogleMapClient, InitializerClient {

    private GoogleMap googleMap;
    private MapFragment mapFragment;
    private Intent intent;
    private LatLongInfoOfAllSpotsTable latlongInfo;
    private String durationText, distanceText, startAddress, endAddress,
            copyRight;
    private Timer timer;
    private Handler handler;
    private TimerTask timerTask;
    private ActionBar actionBar;
    private UserLocation userLocation;
    private Marker userMarker;
    private double userLatitudeVal;
    private double userLongitudeVal;
    private OnInfoWindowClickListener infoWindowClickListener = new OnInfoWindowClickListener() {

        @Override
        public void onInfoWindowClick(Marker marker) {
            if (marker.getTitle().equals(latlongInfo.getSpotName())) {
                Toast.makeText(getApplicationContext(),
                        marker.getTitle() + "\n" + marker.getSnippet(),
                        Toast.LENGTH_LONG).show();
            }
        }
    };

    /*private void getOverFlowMenu() {
        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class
                    .getDeclaredField("sHasPermanentMenuKey");
            if (menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.google_map_layout);
        initialize();
        //getOverFlowMenu();
        googleMap.setInfoWindowAdapter(new InfoWindowAdapterForEachSpot(this));
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        googleMap.setOnInfoWindowClickListener(infoWindowClickListener);
        //googleMap.setMapType(GoogleMap.MAP_TYPE_NONE);
    }

    @Override
    public void initialize() {
        actionBar = getSupportActionBar();
        mapFragment = (MapFragment) getFragmentManager().findFragmentById(
                R.id.googleMap);
        googleMap = this.mapFragment.getMap();
        intent = getIntent();
        latlongInfo = (LatLongInfoOfAllSpotsTable) this.intent
                .getSerializableExtra("SpotLatLongInfo");
        MarkerOptions spotMarkerOptions = new MarkerOptions()
                .position(new LatLng(latlongInfo.getSpotlatitudefield(),
                        latlongInfo.getSpotLongtitudeField()))
                .draggable(false)
                .title(latlongInfo.getSpotName())
                .snippet(latlongInfo.getSpotSnippet())
                .visible(true);
        Marker marker = googleMap.addMarker(spotMarkerOptions);
        setMarkerIcons(marker, latlongInfo.getSpotTypeField());
        handler = new Handler();
        timer = new Timer();
        setGoogleMapCameraMove();
        setTitle(latlongInfo.getSpotName());
        UiSettings uiSettings = googleMap.getUiSettings();
        uiSettings.setMapToolbarEnabled(false);

    }

    @Override
    protected void onResume() {
        super.onResume();
        userLocation = UserLocation.getUserLocation(GoogleMapDirectionActivity.this);
        userLocation.setUpUserLocation();
        userLatitudeVal = userLocation.getUserLatitude();
        userLongitudeVal = userLocation.getUserLongtitude();
        if (userMarker == null) {
            MarkerOptions markerOptions = new MarkerOptions()
                    .position(new LatLng(userLatitudeVal, userLongitudeVal))
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_user))
                    .title("You are here");
            userMarker = googleMap.addMarker(markerOptions);
        } else {
            changeUserPosition();
        }
        new GoogleMapDirectionApiAsyncTask().execute();
        setTimer();
    }

    private void changeUserPosition() {
        userLatitudeVal = userLocation.getUserLatitude();
        userLongitudeVal = userLocation.getUserLongtitude();
        LatLng markerLatLng = userMarker.getPosition();
        if (userLatitudeVal != markerLatLng.latitude ||
                userLongitudeVal != markerLatLng.longitude) {
            userMarker.setPosition(new LatLng(userLatitudeVal, userLongitudeVal));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.direction_for_each_spot, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                stopTimer();
                finish();
                break;
            case R.id.showDirectionInfo:
                showToastMessage();
                break;
            case R.id.reloadDirection:
                reloadDirection();
                break;
        }
        return true;
    }

    private void setGoogleMapCameraMove() {
        if (googleMap != null) {
            LatLng Bangladesh = new LatLng(23.7000, 90.3500);
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Bangladesh,
                    6.0f));
        }
    }

    private void setTimer() {
        if (timer != null && handler != null) {
            timerTask = new TimerTask() {

                @Override
                public void run() {
                    handler.post(new Runnable() {

                        @Override
                        public void run() {

                            reloadDirection();
                            Log.e(getClass().getName(), " timer task is running");
                        }
                    });
                }
            };
            timer.scheduleAtFixedRate(timerTask, 300000, 300000);

        }

    }

    private void reloadDirection() {
        changeUserPosition();
        new GoogleMapDirectionApiAsyncTask().execute();
    }

    private void stopTimer() {
        if (timer != null && handler != null && timerTask != null) {
            timerTask.cancel();
            timer.cancel();
            timer = null;
            timerTask = null;
            handler = null;
        }
    }

    private void showToastMessage() {
        if (durationText != null && distanceText != null && startAddress != null &&
                endAddress != null && copyRight != null) {
            String distanceIndicator = "Distance:";
            String durationIndicator = "Duration:";
            String startAddressIndicator = "Present Location:";
            String endAddressIndicator = "Destination Location:";
            String message = distanceIndicator + " " + distanceText + "\n" + durationIndicator +
                    " " + durationText + "\n" + startAddressIndicator + " " + startAddress + "\n"
                    + endAddressIndicator + " " + endAddress + "\n" + copyRight;
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();


        } else {
            Toast.makeText(getApplicationContext(), "Sorry we could not connect to web services" +
                            "right now. Please check your internet connection.",
                    Toast.LENGTH_LONG).show();

        }
        Log.e(getClass().getName(), "Message is showing");

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

    private class GoogleMapDirectionApiAsyncTask extends AsyncTask<Void, Void, Boolean>
            implements DirectionApiJsonClient {

        private SweetAlertDialog progressDialog;
        private String jsonData;
        private String userLatitude;
        private String userLongitude;
        private String touristSpotLatitude;
        private String touristSpotLongitude;
        private ArrayList<LatLng> directionPointslatLng;


        public GoogleMapDirectionApiAsyncTask() {

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            setProgressDialog();
        }

        private void setProgressDialog() {

            progressDialog = new SweetAlertDialog(GoogleMapDirectionActivity.this,
                    SweetAlertDialog.PROGRESS_TYPE);
            progressDialog.setTitleText("Loading...");
            progressDialog.setCancelable(true);
            progressDialog.show();
            Log.e(getClass().getName(), "Progress Dialog is Showing");

        }

        @Override
        protected Boolean doInBackground(Void... params) {
            if (latlongInfo != null && userLocation != null) {
                touristSpotLatitude = String.valueOf(latlongInfo.getSpotlatitudefield());
                touristSpotLongitude = String.valueOf(latlongInfo.getSpotLongtitudeField());
                userLatitude = String.valueOf(userLocation.getUserLatitude());
                userLongitude = String.valueOf(userLocation.getUserLongtitude());
                String googleMapDirectionWebUrl = DIRECTION_API_WEB_ADDRESS_PART_1 + userLatitude +
                        "," + userLongitude + DIRECTION_API_WEB_ADDRESS_PART_2 + touristSpotLatitude +
                        "," + touristSpotLongitude + DIRECTION_API_WEB_ADDRESS_PART_3;
                GoogleMapDirectionJsonAPIWebService googleMapDirectionJsonAPIWebService =
                        new GoogleMapDirectionJsonAPIWebService(googleMapDirectionWebUrl);
                Log.e(getClass().getName(), "Sending url to web client for getting json data");
                jsonData = googleMapDirectionJsonAPIWebService.getJsonData();
                if (jsonData != null) {
                    return true;
                }

            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            if (jsonData != null && result == true) {
                Log.e(getClass().getName(), "Data fetching Successfully");
                try {
                    JSONObject wholeJsonObject = new JSONObject(jsonData);
                    JSONArray wholeRoutesArray = wholeJsonObject.getJSONArray(ROUTES_FIELD);
                    JSONObject routesJsonObject = wholeRoutesArray.getJSONObject(0);
                    JSONArray wholeLegJsonArray = routesJsonObject.getJSONArray(LEGS_FIELD);
                    JSONObject legJsonObject = wholeLegJsonArray.getJSONObject(0);
                    copyRight = getCopyRight(routesJsonObject);
                    getAllDirectionRequiredVal(legJsonObject);


                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Log.e(getClass().getName(), "Data fetching failed");
            }
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
                showToastMessage();
            }

        }

        private void getAllDirectionRequiredVal(JSONObject legJsonObject) {
            if (legJsonObject != null) {
                Log.e(getClass().getName(), "Leg json object has been found");
                distanceText = getDistanceText(legJsonObject);
                durationText = getDurationText(legJsonObject);
                startAddress = getStartAddress(legJsonObject);
                endAddress = getEndAddress(legJsonObject);
                directionPointslatLng = getPolyLinePoints(legJsonObject);
                if (distanceText != null && durationText != null && startAddress != null &&
                        endAddress != null && directionPointslatLng != null &&
                        directionPointslatLng.size() > 0 && copyRight != null) {
                    Log.e(getClass().getName(), "All steps has been found.");
                    PolylineOptions polylineOptions = new PolylineOptions().width(5).visible(true)
                            .color(Color.MAGENTA);
                    for (int i = 0; i < directionPointslatLng.size(); i++) {
                        polylineOptions.add(directionPointslatLng.get(i));
                    }
                    googleMap.addPolyline(polylineOptions);
                }
            }
        }

        private String getCopyRight(JSONObject legJsonObject) {
            String copyRight = null;
            try {
                copyRight = legJsonObject.getString(COPYRIGHT_FIELD);
                Log.e(getClass().getName(), "CopyRight: " + copyRight);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return copyRight;
        }

        private String getEndAddress(JSONObject legJsonObject) {
            String endAddress = null;
            try {
                endAddress = legJsonObject.getString(END_ADDRESS_FIELD);
                Log.e(getClass().getName(), "End Address: " + endAddress);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return endAddress;
        }

        private String getStartAddress(JSONObject legJsonObject) {
            String startAddress = null;
            try {
                startAddress = legJsonObject.getString(START_ADDRESS_FIELD);
                Log.e(getClass().getName(), "Start Address: " + startAddress);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return startAddress;

        }


        private String getDurationText(JSONObject legJsonObject) {
            String durationText = null;
            try {
                JSONObject durationJsonObject = legJsonObject.getJSONObject(DURATION_FIELD);
                durationText = durationJsonObject.getString(TEXT_FIELD);
                Log.e(getClass().getName(), "Duration : " + durationText);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return durationText;
        }

        private String getDistanceText(JSONObject legJsonObject) {
            String distanceText = null;
            try {
                JSONObject distanceObject = legJsonObject.getJSONObject(DISTANCE_FIELD);
                distanceText = distanceObject.getString(TEXT_FIELD);
                Log.e(getClass().getName(), "Distance: " + distanceText);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return distanceText;
        }

        private ArrayList<LatLng> getPolyLinePoints(JSONObject legJsonObject) {
            ArrayList<LatLng> polyLines = null;
            JSONObject startLocation = null;
            JSONObject endLocation = null;
            JSONObject polyLine = null;
            JSONObject jsonObject = null;
            ArrayList<LatLng> decodePolyLineLatLngs;
            double lat = 0;
            double lng = 0;
            try {
                JSONArray stepsJsonArray = legJsonObject.getJSONArray(STEPS_FIELD);
                if (stepsJsonArray != null) {
                    Log.e(getClass().getName(), "All steps has been found.");
                    polyLines = new ArrayList<LatLng>();
                    for (int i = 0; i < stepsJsonArray.length(); i++) {
                        jsonObject = stepsJsonArray.getJSONObject(i);
                        startLocation = jsonObject.getJSONObject(START_LOCATION_FIELD);
                        lat = startLocation.getDouble(LATITUDE_FIELD);
                        lng = startLocation.getDouble(LONGTITUDE_FIELD);
                        polyLines.add(new LatLng(lat, lng));
                        polyLine = jsonObject.getJSONObject(POLYLINE_FIELD);
                        decodePolyLineLatLngs = decodePoly(polyLine.getString(POINTS_FIELD));
                        if (decodePolyLineLatLngs != null && decodePolyLineLatLngs.size() > 0) {
                            for (int j = 0; j < decodePolyLineLatLngs.size(); j++) {
                                LatLng latLng = decodePolyLineLatLngs.get(j);
                                polyLines.add(latLng);
                            }
                        }
                        endLocation = jsonObject.getJSONObject(END_LOCATION_FIELD);
                        lat = endLocation.getDouble(LATITUDE_FIELD);
                        lng = endLocation.getDouble(LONGTITUDE_FIELD);
                        polyLines.add(new LatLng(lat, lng));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return polyLines;
        }

        private ArrayList<LatLng> decodePoly(String encoded) {
            ArrayList<LatLng> poly = new ArrayList<LatLng>();
            int index = 0, len = encoded.length();
            int lat = 0, lng = 0;
            while (index < len) {
                int b, shift = 0, result = 0;
                do {
                    b = encoded.charAt(index++) - 63;
                    result |= (b & 0x1f) << shift;
                    shift += 5;
                } while (b >= 0x20);
                int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
                lat += dlat;
                shift = 0;
                result = 0;
                do {
                    b = encoded.charAt(index++) - 63;
                    result |= (b & 0x1f) << shift;
                    shift += 5;
                } while (b >= 0x20);
                int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
                lng += dlng;

                LatLng position = new LatLng((double) lat / 1E5,
                        (double) lng / 1E5);
                poly.add(position);
            }
            return poly;
        }
    }
}

