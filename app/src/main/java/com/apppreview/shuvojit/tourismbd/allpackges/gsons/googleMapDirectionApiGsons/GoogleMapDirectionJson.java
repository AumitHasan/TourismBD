package com.apppreview.shuvojit.tourismbd.allpackges.gsons.googleMapDirectionApiGsons;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by shuvojit on 3/22/15.
 */
public class GoogleMapDirectionJson {

    @SerializedName("status")
    private String status;

    @SerializedName("routes")
    private ArrayList<Routes> routesArrayList;

    public GoogleMapDirectionJson() {
    }

    public GoogleMapDirectionJson(String status, ArrayList<Routes> routesArrayList) {
        this.status = status;
        this.routesArrayList = routesArrayList;
    }

    public String getStatus() {
        return status;
    }

    public ArrayList<Routes> getRoutesArrayList() {
        return routesArrayList;
    }
}
