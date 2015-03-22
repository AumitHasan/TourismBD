package com.apppreview.shuvojit.tourismbd.allpackges.gsons.googleMapDirectionApiGsons;

import com.google.gson.annotations.SerializedName;

/**
 * Created by shuvojit on 3/22/15.
 */
public class Steps {

    @SerializedName("travel_mode")
    private String travelMode;

    @SerializedName("start_location")
    private Location startLocation;

    @SerializedName("end_location")
    private Location endLocation;

    @SerializedName("polyline")
    private Polyline polyline;

    @SerializedName("duration")
    private DurationDistance durationDistance;

    @SerializedName("html_instructions")
    private String htmlInstructions;

    @SerializedName("distance")
    private DurationDistance distance;

    public Steps() {
    }

    public Steps(String travelMode, Location startLocation,
                 Location endLocation, Polyline polyline,
                 DurationDistance durationDistance, String htmlInstructions,
                 DurationDistance distance) {
        this.travelMode = travelMode;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.polyline = polyline;
        this.durationDistance = durationDistance;
        this.htmlInstructions = htmlInstructions;
        this.distance = distance;
    }

    public String getTravelMode() {
        return travelMode;
    }

    public Location getStartLocation() {
        return startLocation;
    }

    public Location getEndLocation() {
        return endLocation;
    }

    public Polyline getPolyline() {
        return polyline;
    }

    public DurationDistance getDurationDistance() {
        return durationDistance;
    }

    public String getHtmlInstructions() {
        return htmlInstructions;
    }

    public DurationDistance getDistance() {
        return distance;
    }
}
