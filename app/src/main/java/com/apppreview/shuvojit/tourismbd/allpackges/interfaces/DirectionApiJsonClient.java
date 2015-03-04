package com.apppreview.shuvojit.tourismbd.allpackges.interfaces;

/**
 * Created by Shuvojit Saha Shuvo on 1/30/2015.
 */
public interface DirectionApiJsonClient {
    public static final String DIRECTION_API_WEB_ADDRESS_PART_1 = "http://maps.googleapis.com/" +
            "maps/api/directions/json?origin=";
    public static final String DIRECTION_API_WEB_ADDRESS_PART_2 = "&destination=";
    public static final String DIRECTION_API_WEB_ADDRESS_PART_3 = "&language=en&sensor=false&" +
            "units=metric&mode=driving";
    public static final String ROUTES_FIELD = "routes";
    public static final String LEGS_FIELD = "legs";
    public static final String DURATION_FIELD = "duration";
    public static final String VALUE_FIELD = "value";
    public static final String TEXT_FIELD = "text";
    public static final String DISTANCE_FIELD = "distance";
    public static final String START_ADDRESS_FIELD = "start_address";
    public static final String END_ADDRESS_FIELD = "end_address";
    public static final String COPYRIGHT_FIELD = "copyrights";
    public static final String STEPS_FIELD = "steps";
    public static final String POLYLINE_FIELD = "polyline";
    public static final String LATITUDE_FIELD = "lat";
    public static final String LONGTITUDE_FIELD = "lng";
    public static final String START_LOCATION_FIELD = "start_location";
    public static final String END_LOCATION_FIELD = "end_location";
    public static final String POINTS_FIELD = "points";
}
