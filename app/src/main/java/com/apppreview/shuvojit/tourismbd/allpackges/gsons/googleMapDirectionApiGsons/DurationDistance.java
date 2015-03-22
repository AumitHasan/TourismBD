package com.apppreview.shuvojit.tourismbd.allpackges.gsons.googleMapDirectionApiGsons;

import com.google.gson.annotations.SerializedName;

/**
 * Created by shuvojit on 3/22/15.
 */
public class DurationDistance {

    @SerializedName("value")
    private int value;

    @SerializedName("text")
    private String text;

    public DurationDistance() {

    }

    public DurationDistance(int value, String text) {
        this.value = value;
        this.text = text;
    }

    public int getValue() {
        return value;
    }

    public String getText() {
        return text;
    }
}
