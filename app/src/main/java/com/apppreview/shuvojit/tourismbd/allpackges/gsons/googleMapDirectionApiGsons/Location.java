package com.apppreview.shuvojit.tourismbd.allpackges.gsons.googleMapDirectionApiGsons;

import com.google.gson.annotations.SerializedName;

/**
 * Created by shuvojit on 3/22/15.
 */
public class Location {

    @SerializedName("lat")
    private double lat;

    @SerializedName("lng")
    private double lng;

    public Location() {

    }

    public Location(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }
}
