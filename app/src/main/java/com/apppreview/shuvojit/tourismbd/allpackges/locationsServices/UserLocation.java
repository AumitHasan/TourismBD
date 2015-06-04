package com.apppreview.shuvojit.tourismbd.allpackges.locationsServices;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import com.apppreview.shuvojit.tourismbd.allpackges.interfaces.InitializerClient;

/**
 * Created by shuvojit on 3/3/15.
 */
public class UserLocation implements InitializerClient {

    private Context context;
    private double userLatitude;
    private double userLongtitude;
    private LocationManager locationManager;
    private static UserLocation userLocation;


    private UserLocation(Context context) {
        this.context = context;
        initialize();
    }

    @Override
    public void initialize() {
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    private boolean isServiceEnabled(String locationProvider) {
        if (locationManager.isProviderEnabled(locationProvider)) {
            return true;
        }
        return false;
    }

    private Location getRequestUpdateFromLocationProvider(String locationProvider) {
        Location location = null;
        if (isServiceEnabled(locationProvider)) {
            locationManager.requestLocationUpdates(locationProvider, 1000, 10, locationListener);
            location = locationManager.getLastKnownLocation(locationProvider);
        }
        return location;
    }

    public void setUpUserLocation() {
        Location gpsProviderLocation = null;
        Location networkProviderLocation = null;
        if (locationManager != null) {
            gpsProviderLocation = getRequestUpdateFromLocationProvider
                    (LocationManager.GPS_PROVIDER);
            networkProviderLocation = getRequestUpdateFromLocationProvider
                    (LocationManager.NETWORK_PROVIDER);
            if (gpsProviderLocation != null && networkProviderLocation != null) {
                Location bestLocationProvider = getBestProviderLocation(gpsProviderLocation,
                        networkProviderLocation);
            } else if (gpsProviderLocation != null) {
                setUserLatitude(gpsProviderLocation);
                setUserLongitude(gpsProviderLocation);
            } else if (networkProviderLocation != null) {
                setUserLatitude(networkProviderLocation);
                setUserLongitude(networkProviderLocation);
            } else {
                Log.e(getClass().getName(), "no location provider has been found");
            }

        }
    }

    private Location getBestProviderLocation(Location newLocation, Location oldLocation) {
        if (oldLocation == null) {
            return newLocation;
        }
        long newTime = newLocation.getTime();
        long oldTime = oldLocation.getTime();
        long timeDelta = newTime - oldTime;
        boolean isSignificantlyNewer = timeDelta > 60000;
        boolean isSignificantlyOlder = timeDelta < -60000;
        boolean isNewer = timeDelta > 0;
        if (isSignificantlyNewer) {
            return newLocation;

        } else if (isSignificantlyOlder) {
            return oldLocation;
        }

        float newAccuracy = newLocation.getAccuracy();
        float oldAccuracy = oldLocation.getAccuracy();
        int accuracyDelta = (int) (newAccuracy - oldAccuracy);
        boolean isMoreAccurate = accuracyDelta < 0;
        boolean isLessAccurate = accuracyDelta > 0;
        boolean isSignificantlyLessAccurate = accuracyDelta > 200;

        if (isMoreAccurate) {
            return newLocation;
        } else if (isNewer && !isLessAccurate) {
            return newLocation;
        } else if (isNewer && !isSignificantlyLessAccurate) {
            return newLocation;
        }

        return oldLocation;
    }

    public boolean isLocationServiceIsOn() {
        boolean res = false;
        if (isServiceEnabled(LocationManager.GPS_PROVIDER)) {
            Log.e(getClass().getName(), "GPS is on");
            res = true;
        } else if (isServiceEnabled(LocationManager.NETWORK_PROVIDER)) {
            Log.e(getClass().getName(), "Network is on");
            res = true;
        }
        return res;
    }

    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            setUserLatitude(location);
            setUserLongitude(location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

            Log.e(getClass().getName(), "Location Provider: " + provider +
                    "has been status changed");

        }

        @Override
        public void onProviderEnabled(String provider) {
            Log.e(getClass().getName(), "Location Provider: " + provider + " is enabled");
        }

        @Override
        public void onProviderDisabled(String provider) {
            Log.e(getClass().getName(), "Location Provider: " + provider + " is disabled");
        }
    };

    public void setUserLatitude(Location location) {
        userLatitude = location.getLatitude();
    }

    public void setUserLongitude(Location location) {
        userLongtitude = location.getLongitude();
    }

    public double getUserLatitude() {
        return userLatitude;
    }

    public double getUserLongtitude() {
        return userLongtitude;
    }

    public static UserLocation getUserLocation(Context context) {
        if (userLocation == null) {
            userLocation = new UserLocation(context);
        }
        return userLocation;
    }
}

