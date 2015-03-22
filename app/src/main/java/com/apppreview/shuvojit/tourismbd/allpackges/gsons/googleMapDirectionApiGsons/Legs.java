package com.apppreview.shuvojit.tourismbd.allpackges.gsons.googleMapDirectionApiGsons;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by shuvojit on 3/22/15.
 */
public class Legs {

    @SerializedName("steps")
    private ArrayList<Steps> stepsArrayList;

    @SerializedName("duration")
    private DurationDistance duration;

    @SerializedName("distance")
    private DurationDistance distance;

    @SerializedName("start_location")
    private Location startLocation;

    @SerializedName("end_location")
    private Location endLocation;

    @SerializedName("start_address")
    private String startAddress;

    @SerializedName("end_address")
    private String endAddress;

    public Legs() {
    }

    public Legs(ArrayList<Steps> stepsArrayList,
                DurationDistance durationDistance, DurationDistance distance,
                Location startLocation, Location endLocation,
                String startAddress, String endAddress) {
        this.stepsArrayList = stepsArrayList;
        this.duration = durationDistance;
        this.distance = distance;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.startAddress = startAddress;
        this.endAddress = endAddress;
    }

    public ArrayList<Steps> getStepsArrayList() {
        return stepsArrayList;
    }

    public DurationDistance getDuration() {
        return duration;
    }

    public DurationDistance getDistance() {
        return distance;
    }

    public Location getStartLocation() {
        return startLocation;
    }

    public Location getEndLocation() {
        return endLocation;
    }

    public String getStartAddress() {
        return startAddress;
    }

    public String getEndAddress() {
        return endAddress;
    }
}
