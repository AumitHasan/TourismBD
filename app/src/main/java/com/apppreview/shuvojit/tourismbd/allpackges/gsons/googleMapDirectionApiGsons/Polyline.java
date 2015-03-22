package com.apppreview.shuvojit.tourismbd.allpackges.gsons.googleMapDirectionApiGsons;

import com.google.gson.annotations.SerializedName;

/**
 * Created by shuvojit on 3/22/15.
 */
public class Polyline {

    @SerializedName("points")
    private String points;

    public Polyline() {

    }

    public Polyline(String points) {
        this.points = points;
    }

    public String getPoints() {
        return points;
    }
}
