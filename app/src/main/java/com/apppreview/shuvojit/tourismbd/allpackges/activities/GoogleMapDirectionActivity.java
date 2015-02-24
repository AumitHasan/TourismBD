package com.apppreview.shuvojit.tourismbd.allpackges.activities;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewConfiguration;
import android.widget.Toast;

import com.apppreview.shuvojit.tourismbd.R;
import com.apppreview.shuvojit.tourismbd.allpackges.adapters.googleMapInfoWindowAdapters.InfoWindowAdapterForEachSpot;
import com.apppreview.shuvojit.tourismbd.allpackges.infos.LatLongInfo;
import com.apppreview.shuvojit.tourismbd.allpackges.interfaces.GoogleMapInterface;
import com.apppreview.shuvojit.tourismbd.allpackges.interfaces.Intializer;
import com.apppreview.shuvojit.tourismbd.allpackges.webServices.GoogleMapDirectionJsonParser;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class GoogleMapDirectionActivity extends ActionBarActivity implements
        LocationListener, GoogleMapInterface, Intializer {

    private GoogleMap googleMap;
    private MapFragment mapFragment;
    private LocationManager locationManager;
    private Location userLocation;
    private double userLatitudeVal, userLongitudeVal;
    private Intent intent;
    private LatLongInfo latlongInfo;
    private LatLng fromLatLong, toLatLong;
    private MarkerOptions userMarkerOptions;
    private MarkerOptions spotMarkeroptions;
    private String durationText, distanceText, startAddress, endAddress,
            copyRight, durationValue, distanceValue;
    private ArrayList<LatLng> latLongList;
    private ProgressDialog progressDialog;
    private String jsonData;
    private Timer timer;
    private Handler handler;
    private TimerTask timerTask;
    private Marker userMarker;
    private ActionBar actionBar;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.google_map_layout);
        intialize();
        getOverFlowMenu();
        googleMap.setInfoWindowAdapter(new InfoWindowAdapterForEachSpot(this));
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources()
                .getColor(R.color.darkred)));
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void intialize() {
        actionBar = getSupportActionBar();
        mapFragment = (MapFragment) getFragmentManager().findFragmentById(
                R.id.googleMap);
        googleMap = this.mapFragment.getMap();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        intent = getIntent();
        latlongInfo = (LatLongInfo) this.intent
                .getSerializableExtra("SpotLatLongInfo");
        progressDialog = new ProgressDialog(this);
        jsonData = null;
        handler = new Handler();
        timer = new Timer();
        userMarker = null;
        setGoogleMapCameraMove();
        setUpSpotLocation();
        setTitle(latlongInfo.getSpotName());

    }

    private void getOverFlowMenu() {
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

    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpUserLocationProvider();
        setUpUserLocation();
        setUpDirection();
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
                showToastMessageOfDirectionInfo();
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

    private void setUpDirection() {
        new DirectionAsyncTask().execute();
        userMarkerOptions = new MarkerOptions()
                .position(fromLatLong)
                .icon(BitmapDescriptorFactory
                        .fromResource(R.drawable.marker_user))
                .title("You are here");
        userMarker = googleMap.addMarker(userMarkerOptions);
        spotMarkeroptions = new MarkerOptions().position(toLatLong)
                .title(latlongInfo.getSpotName())
                .snippet(latlongInfo.getSpotSnippet());
        Marker spotmarker = googleMap.addMarker(spotMarkeroptions);
        setMarkerIcons(spotmarker, latlongInfo.getSpotType());
        setTimer();
        googleMap.setOnInfoWindowClickListener(infoWindowClickListener);

    }

    private void setTimer() {
        if (timer != null && handler != null) {
            timerTask = new TimerTask() {

                @Override
                public void run() {
                    handler.post(new Runnable() {

                        @Override
                        public void run() {

                            showToastMessageOfDirectionInfo();
                        }
                    });
                }
            };
            timer.scheduleAtFixedRate(timerTask, 60000, 60000);

        }

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

    private void showToastMessageOfDirectionInfo() {
        if (durationText != null && distanceText != null
                && startAddress != null && endAddress != null
                && copyRight != null) {
            Toast.makeText(
                    getApplicationContext(),
                    "Duration: " + durationText + "\n" + "Distance: "
                            + distanceText + "\n" + "Present Address: "
                            + startAddress + "\n" + "Destination Address: "
                            + endAddress + "\n" + copyRight, Toast.LENGTH_LONG)
                    .show();
        } else {
            Toast.makeText(getApplicationContext(), "Sry no direction found",
                    Toast.LENGTH_LONG).show();
        }

    }

    private void setUpSpotLocation() {
        toLatLong = new LatLng(latlongInfo.getLatitudeVal(),
                latlongInfo.getLongtitudeVal());
    }

    private void setUpUserLocation() {
        fromLatLong = new LatLng(getUserLatitudeVal(), getUserLongitudeVal());
    }

    private boolean isServiceEnabled(String provider) {
        if (locationManager.isProviderEnabled(provider)) {
            return true;
        }
        return false;
    }

    private void setUpUserLocationProvider() {
        Location gpsLocation = null;
        Location networkLocation = null;
        gpsLocation = requestUpdateFromprovider(LocationManager.GPS_PROVIDER);
        networkLocation = requestUpdateFromprovider(LocationManager.NETWORK_PROVIDER);
        if (gpsLocation != null && networkLocation != null) {
            userLocation = bestLocationProvider(gpsLocation, networkLocation);
            setUserLatitudeVal(userLocation.getLatitude());
            setUserLongitudeVal(userLocation.getLongitude());

        } else if (gpsLocation != null) {
            userLocation = gpsLocation;
            setUserLatitudeVal(userLocation.getLatitude());
            setUserLongitudeVal(userLocation.getLongitude());
        } else if (networkLocation != null) {
            userLocation = networkLocation;
            setUserLatitudeVal(userLocation.getLatitude());
            setUserLongitudeVal(userLocation.getLongitude());
        } else {
            Toast.makeText(getApplicationContext(),
                    "No Location provider is enabled", Toast.LENGTH_LONG)
                    .show();
        }

    }

    private Location requestUpdateFromprovider(String provider) {
        Location location = null;
        if (isServiceEnabled(provider)) {
            locationManager.requestLocationUpdates(provider, 1000, 10, this);
            location = locationManager.getLastKnownLocation(provider);
        }
        return location;
    }

    private Location bestLocationProvider(Location newLocation,
                                          Location oldLocation) {
        if (oldLocation == null) {
            return newLocation;
        }

        long timeDelta = newLocation.getTime() - oldLocation.getTime();
        boolean isSignificantlyNewer = timeDelta > 60000;
        boolean isSignificantlyOlder = timeDelta < -60000;
        boolean isNewer = timeDelta > 0;
        if (isSignificantlyNewer) {
            return newLocation;
        } else if (isSignificantlyOlder) {
            return oldLocation;
        }

        int deltaAccuracy = (int) (newLocation.getAccuracy() - oldLocation
                .getAccuracy());
        boolean isMoreAccurate = deltaAccuracy < 0;
        boolean islessAccurate = deltaAccuracy > 0;
        boolean isSignificantlyLessAccurate = deltaAccuracy > 200;

        if (isMoreAccurate) {
            return newLocation;
        } else if (isNewer && !islessAccurate) {
            return newLocation;
        } else if (isNewer && !isSignificantlyLessAccurate) {
            return newLocation;
        }
        return oldLocation;

    }

    private double getUserLatitudeVal() {
        return userLatitudeVal;
    }

    private void setUserLatitudeVal(double userLatitudeVal) {
        this.userLatitudeVal = userLatitudeVal;
    }

    private double getUserLongitudeVal() {
        return userLongitudeVal;
    }

    private void setUserLongitudeVal(double userLongitudeVal) {
        this.userLongitudeVal = userLongitudeVal;
    }

    @Override
    public void onLocationChanged(Location location) {
        userLocation = location;
        setUserLatitudeVal(userLocation.getLatitude());
        setUserLongitudeVal(userLocation.getLongitude());

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(getApplicationContext(), provider + " is enabled",
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(getApplicationContext(), provider + " is not enabled",
                Toast.LENGTH_LONG).show();
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

    private class DirectionAsyncTask extends AsyncTask<Void, Void, Boolean> {

        public DirectionAsyncTask() {

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            setProgressDialog();
        }

        private void setProgressDialog() {
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
            progressDialog.show();

        }

        @Override
        protected Boolean doInBackground(Void... params) {
            GoogleMapDirectionJsonParser googleMapDirectionJsonParser = new GoogleMapDirectionJsonParser(
                    userLatitudeVal, userLongitudeVal,
                    latlongInfo.getLatitudeVal(),
                    latlongInfo.getLongtitudeVal());
            jsonData = googleMapDirectionJsonParser.getJsonData();
            if (jsonData != null) {
                Log.e(getClass().getName(), "Found 1");
                try {
                    JSONObject allJsonObject = new JSONObject(jsonData);
                    JSONArray routesJsonArray = allJsonObject
                            .getJSONArray("routes");
                    JSONObject routesJObject = routesJsonArray.getJSONObject(0);
                    JSONArray allLegJsonArray = routesJObject
                            .getJSONArray("legs");
                    JSONObject legJsonObject = allLegJsonArray.getJSONObject(0);
                    durationText = getDurationText(legJsonObject);
                    durationValue = getDurationVal(legJsonObject);
                    distanceText = getDistanceText(legJsonObject);
                    distanceValue = getDistanceVal(legJsonObject);
                    startAddress = getStartAddress(legJsonObject);
                    endAddress = getEndAddress(legJsonObject);
                    copyRight = getCopyright(routesJObject);
                    latLongList = getDirection(legJsonObject);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;

            } else {
                Log.e(getClass().getName(), "No Found data");
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
                if (result == true) {
                    Log.d(getClass().getName(), "Parsing is successful");
                    if (durationText != null && distanceText != null
                            && startAddress != null && endAddress != null
                            && copyRight != null && latLongList != null) {
                        Toast.makeText(
                                getApplicationContext(),
                                "Duration: " + durationText + "\n"
                                        + "Distance: " + distanceText + "\n"
                                        + "Present Address: " + startAddress
                                        + "\n" + "Destination Address: "
                                        + endAddress + "\n" + copyRight,
                                Toast.LENGTH_LONG).show();
                        PolylineOptions polylineOptions = new PolylineOptions()
                                .width(3).color(Color.MAGENTA);
                        for (int i = 0; i < latLongList.size(); i++) {
                            LatLng latLng = latLongList.get(i);
                            polylineOptions.add(latLng);
                        }
                        googleMap.addPolyline(polylineOptions);

                    } else {
                        Toast.makeText(getApplicationContext(),
                                "sry no direction found", Toast.LENGTH_LONG)
                                .show();
                    }

                } else {
                    Log.d(getClass().getName(), "parsing has been failed");

                }
            }
        }

        private String getDurationText(JSONObject jsonObject) {
            String duration = null;
            try {
                JSONObject durationObject = jsonObject
                        .getJSONObject("duration");
                duration = durationObject.getString("text");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return duration;

        }

        private String getDurationVal(JSONObject jsonObject) {
            String duration = null;
            try {
                JSONObject durationObject = jsonObject
                        .getJSONObject("duration");
                duration = String.valueOf(durationObject.getInt("value"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return duration;

        }

        private String getDistanceText(JSONObject jsonObject) {
            String distance = null;
            try {
                JSONObject distanceObject = jsonObject
                        .getJSONObject("distance");
                distance = distanceObject.getString("text");
            } catch (Exception e) {
                e.printStackTrace();
            }

            return distance;
        }

        private String getDistanceVal(JSONObject jsonObject) {
            String distance = null;
            try {
                JSONObject distanceObject = jsonObject
                        .getJSONObject("distance");
                distance = String.valueOf(distanceObject.getString("value"));
            } catch (Exception e) {
                e.printStackTrace();
            }

            return distance;
        }

        private String getStartAddress(JSONObject jsonObject) {
            String address = null;
            try {
                address = jsonObject.getString("start_address");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return address;
        }

        private String getEndAddress(JSONObject jsonObject) {
            String address = null;
            try {
                address = jsonObject.getString("end_address");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return address;
        }

        private String getCopyright(JSONObject jsonObject) {
            String copyRight = null;
            try {
                copyRight = jsonObject.getString("copyrights");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return copyRight;
        }

        private ArrayList<LatLng> getDirection(JSONObject jsonObject) {
            ArrayList<LatLng> listGeoPoints = new ArrayList<LatLng>();
            try {
                JSONArray allstepsArray = jsonObject.getJSONArray("steps");
                for (int i = 0; i < allstepsArray.length(); i++) {
                    JSONObject step = allstepsArray.getJSONObject(i);
                    JSONObject start = step.getJSONObject("start_location");
                    double startLat = start.getDouble("lat");
                    double startLng = start.getDouble("lng");
                    listGeoPoints.add(new LatLng(startLat, startLng));
                    JSONObject polyLine = step.getJSONObject("polyline");
                    ArrayList<LatLng> arr = decodePoly(polyLine
                            .getString("points"));
                    for (int j = 0; j < arr.size(); j++) {
                        listGeoPoints.add(arr.get(j));
                    }
                    JSONObject end = step.getJSONObject("end_location");
                    double endLat = end.getDouble("lat");
                    double endLng = end.getDouble("lng");
                    listGeoPoints.add(new LatLng(endLat, endLng));

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return listGeoPoints;
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

