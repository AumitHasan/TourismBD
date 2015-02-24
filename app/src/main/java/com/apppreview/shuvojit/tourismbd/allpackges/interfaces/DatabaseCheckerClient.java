package com.apppreview.shuvojit.tourismbd.allpackges.interfaces;

import android.database.sqlite.SQLiteDatabase;

public interface DatabaseCheckerClient {

    public static final String LAT_LONG_INFO_OF_ALL_SPOTS_TABLE = "latlonginfoofallspots";
    public static final String SPOT_INFO_TABLE = "spotinfo";
    public static final String DISCRIPTION_FIELD = "discription";
    public static final String FAMOUS_SPOTS_FIELD = "famous_spots";
    public static final String HOTELS_INFO = "hotels_info";
    public static final String LOCATION_FIELD = "location";
    public static final String URL_LINK_INFO = "url_link";
    public static final String LATITUDE_FIELD = "spotlatitudefield";
    public static final String LONGTITUDE_FIELD = "spotlongtitudefield";
    public static final String SNIPPET_FIELD = "spotsnippet";
    public static final String DB_TABLE_NAME_3 = "favourite_lists";
    public static final String DATABASE_NAME = "touristguiderDB";
    public static final int VERSION = 1;
    public static final SQLiteDatabase.CursorFactory CURSOR_FACTORY = null;
    public static final String ID_FIELD = "_id";
    public static final String SPOT_NAME_FIELD = "spotname";
    public static final String SPOT_SNIPPET_FIELD = "spotsnippet";
    public static final String SPOT_TYPE_FIELD = "spottypefield";
    public static final String SPOT_LATITUDE_FIELD = "spotlatitudefield";
    public static final String SPOT_LONGTITUDE_FIELD = "spotlongtitudefield";
    public static final String FAVOURITES_LIST_TABLE = "favourite_lists";
    public static final String DOC_VIDEO_TABLE = "doc_video";
    public static final String DOC_NAME_FIELD = "doc_name";
    public static final String KEY_FIELD = "key";

    public SQLiteDatabase openDB();

    public void uploadDB();

    public boolean dbExist();

    public void copyDB();

}
