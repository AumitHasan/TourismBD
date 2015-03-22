package com.apppreview.shuvojit.tourismbd.allpackges.gsons.googleMapDirectionApiGsons;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by shuvojit on 3/22/15.
 */
public class Routes {

    @SerializedName("summary")
    private String summary;

    @SerializedName("legs")
    private ArrayList<Legs> legsArrayList;

    @SerializedName("copyrights")
    private String copyrights;

    @SerializedName("overview_polyline")
    private Polyline polyline;

    @SerializedName("warnings")
    private String[] warnings;

    @SerializedName("waypoint_order")
    private int[] wayPointOrder;

    @SerializedName("bounds")
    private Bounds bounds;

    public Routes() {
    }

    public Routes(String summary, ArrayList<Legs> legsArrayList,
                  String copyrights, Polyline polyline,
                  String[] warnings, int[] wayPointOrder, Bounds bounds) {
        this.summary = summary;
        this.legsArrayList = legsArrayList;
        this.copyrights = copyrights;
        this.polyline = polyline;
        this.warnings = warnings;
        this.wayPointOrder = wayPointOrder;
        this.bounds = bounds;
    }

    public String getSummary() {
        return summary;
    }

    public ArrayList<Legs> getLegsArrayList() {
        return legsArrayList;
    }

    public String getCopyrights() {
        return copyrights;
    }

    public Polyline getPolyline() {
        return polyline;
    }

    public String[] getWarnings() {
        return warnings;
    }

    public int[] getWayPointOrder() {
        return wayPointOrder;
    }

    public Bounds getBounds() {
        return bounds;
    }


}
