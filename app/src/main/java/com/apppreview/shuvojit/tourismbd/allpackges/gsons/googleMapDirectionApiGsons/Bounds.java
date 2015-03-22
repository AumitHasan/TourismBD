package com.apppreview.shuvojit.tourismbd.allpackges.gsons.googleMapDirectionApiGsons;

import com.google.gson.annotations.SerializedName;

/**
 * Created by shuvojit on 3/22/15.
 */
public class Bounds {

    @SerializedName("southwest")
    private Location southwest;

    @SerializedName("northeast")
    private Location northEast;

    public Bounds() {
    }

    public Bounds(Location southwest, Location northEast) {
        this.southwest = southwest;
        this.northEast = northEast;
    }

    public Location getSouthwest() {
        return southwest;
    }

    public Location getNorthEast() {
        return northEast;
    }
}
