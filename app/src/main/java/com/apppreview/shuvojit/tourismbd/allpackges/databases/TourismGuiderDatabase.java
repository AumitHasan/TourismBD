package com.apppreview.shuvojit.tourismbd.allpackges.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.apppreview.shuvojit.tourismbd.allpackges.infos.DocumentaryVideoInfo;
import com.apppreview.shuvojit.tourismbd.allpackges.infos.FavouritesSpotInfo;
import com.apppreview.shuvojit.tourismbd.allpackges.infos.LatLongInfo;
import com.apppreview.shuvojit.tourismbd.allpackges.infos.SpotInfo;
import com.apppreview.shuvojit.tourismbd.allpackges.interfaces.DatabaseClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;


public class TourismGuiderDatabase extends SQLiteOpenHelper implements
        DatabaseClient {

    private static TourismGuiderDatabase spotInfoDB;
    private SQLiteDatabase DB;
    private Context context;
    private String databasePath = "data/data/";

    private TourismGuiderDatabase(Context context) {
        super(context, DATABASE_NAME, CURSOR_FACTORY, VERSION);
        this.context = context;
        databasePath += this.context.getPackageName() + "/databases/"
                + DATABASE_NAME;
        DB = openDB();

    }

    public static TourismGuiderDatabase getTourismGuiderDatabase(Context context) {
        if (spotInfoDB == null) {
            spotInfoDB = new TourismGuiderDatabase(context);
        }

        return spotInfoDB;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public SQLiteDatabase openDB() {
        if (DB == null) {
            uploadDB();
            DB = SQLiteDatabase.openDatabase(databasePath, CURSOR_FACTORY,
                    SQLiteDatabase.OPEN_READWRITE);
        }
        return DB;
    }

    @Override
    public void uploadDB() {
        boolean isExist = dbExist();
        if (!isExist) {
            this.getReadableDatabase();
            Log.e(getClass().getName(), "Database is not exist");
            copyDB();
        } else {
            Log.e(getClass().getName(), "Database already exist");
        }

    }

    @Override
    public boolean dbExist() {
        File file = new File(databasePath);
        if (file.exists()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void copyDB() {
        try {
            InputStream inputStream = context.getAssets().open(DATABASE_NAME);
            OutputStream outputStream = new FileOutputStream(databasePath);
            int readCount = 0;
            byte[] buffer = new byte[4096];
            readCount = inputStream.read(buffer);
            while (readCount > 0) {
                outputStream.write(buffer, 0, readCount);
                readCount = inputStream.read(buffer);
            }
            inputStream.close();
            outputStream.close();
            Log.e(getClass().getName(), "Database has been copied");

        } catch (IOException e) {

            e.printStackTrace();
        }

    }

    private synchronized void closeDB() {
        if (DB != null) {
            DB.close();
        }
    }

    public SpotInfo getSpotInfo(String spotName, String spotType) {

        SpotInfo spotInfo = null;
        this.DB = getReadableDatabase();
        String selection = SPOT_NAME_FIELD + " = ? and " + SPOT_TYPE_FIELD
                + " = ?";
        String selectionArgs[] = new String[]{spotName, spotType};
        Cursor cursor = DB.query(SPOT_INFO_TABLE, null, selection,
                selectionArgs, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            Log.e(getClass().getName(), String.valueOf(cursor.getCount()));
            cursor.moveToFirst();

            String spot_name = cursor.getString(cursor
                    .getColumnIndex(SPOT_NAME_FIELD));
            String spot_discription = cursor.getString(cursor
                    .getColumnIndex(DISCRIPTION_FIELD));
            String spot_famous = cursor.getString(cursor
                    .getColumnIndex(FAMOUS_SPOTS_FIELD));
            String spot_hotels = cursor.getString(cursor
                    .getColumnIndex(HOTELS_INFO));
            String spot_location = cursor.getString(cursor
                    .getColumnIndex(LOCATION_FIELD));
            String spot_url_link = cursor.getString(cursor
                    .getColumnIndex(URL_LINK_INFO));
            spotInfo = new SpotInfo(spot_name, spot_location, spot_discription,
                    spot_famous, spot_hotels, spot_url_link);
            Toast.makeText(context, spot_name, Toast.LENGTH_LONG).show();
        }
        cursorClose(cursor);
        closeDB();
        return spotInfo;
    }

    public LatLongInfo getLatLongInfo(String spotName, String spotType) {
        DB = getReadableDatabase();
        String selection = SPOT_NAME_FIELD + " = ? and " + SPOT_TYPE_FIELD
                + " = ?";
        String selectionArgs[] = new String[]{spotName, spotType};
        Cursor cursor = DB.query(LAT_LONG_INFO_OF_ALL_SPOTS_TABLE, null, selection,
                selectionArgs, null, null, null);
        LatLongInfo latLongInfo = null;
        Log.e(getClass().getName(), spotName + "\n" + spotType);

        if (cursor != null && cursor.getCount() > 0) {
            Log.e(getClass().getName(), String.valueOf(cursor.getCount()));
            cursor.moveToFirst();
            String spot_name = cursor.getString(cursor
                    .getColumnIndex(SPOT_NAME_FIELD));
            String spot_type = cursor.getString(cursor
                    .getColumnIndex(SPOT_TYPE_FIELD));
            String spot_snippet = cursor.getString(cursor
                    .getColumnIndex(SNIPPET_FIELD));
            double latitude = cursor.getDouble(cursor
                    .getColumnIndex(LATITUDE_FIELD));
            double longtitude = cursor.getDouble(cursor
                    .getColumnIndex(LONGTITUDE_FIELD));
            latLongInfo = new LatLongInfo(spot_name, spot_snippet, spot_type,
                    latitude, longtitude);
        }
        cursorClose(cursor);
        closeDB();
        return latLongInfo;
    }

    public ArrayList<FavouritesSpotInfo> getFavouritesSpotInfoList() {
        ArrayList<FavouritesSpotInfo> favouriteSpotInfolist = null;
        DB = getReadableDatabase();
        Cursor cursor = DB.query(DB_TABLE_NAME_3, new String[]{ID_FIELD,
                        SPOT_NAME_FIELD, SPOT_TYPE_FIELD}, null, null, null, null,
                null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            favouriteSpotInfolist = new ArrayList<FavouritesSpotInfo>();
            for (int i = 0; i < cursor.getCount(); i++) {

                int id = cursor.getInt(cursor.getColumnIndex(ID_FIELD));
                String spotName = cursor.getString(cursor
                        .getColumnIndex(SPOT_NAME_FIELD));
                String spotTypeField = cursor.getString(cursor
                        .getColumnIndex(SPOT_TYPE_FIELD));

                FavouritesSpotInfo favouritesSpotInfo = new FavouritesSpotInfo(
                        id, spotName, spotTypeField);
                favouriteSpotInfolist.add(favouritesSpotInfo);
                cursor.moveToNext();
            }

        }
        cursorClose(cursor);
        closeDB();
        return favouriteSpotInfolist;
    }

    public int removeSpotFrom(String spotName) {
        int isRemoved = 0;
        DB = getReadableDatabase();
        isRemoved = DB.delete(FAVOURITES_LIST_TABLE, SPOT_NAME_FIELD + " = ?",
                new String[]{spotName});
        return isRemoved;
    }

    public ArrayList<SpotInfo> getSpotNameList(String spotType) {
        ArrayList<SpotInfo> spotNameList = null;
        DB = getReadableDatabase();
        Cursor cursor = DB.query(LAT_LONG_INFO_OF_ALL_SPOTS_TABLE, new String[]{ID_FIELD,
                        SPOT_NAME_FIELD, SPOT_TYPE_FIELD, SPOT_SNIPPET_FIELD}, SPOT_TYPE_FIELD + " = ?",
                new String[]{spotType}, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            spotNameList = new ArrayList<SpotInfo>();
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                int id = cursor.getInt(cursor.getColumnIndex(ID_FIELD));
                String spotName = cursor.getString(cursor
                        .getColumnIndex(SPOT_NAME_FIELD));
                String spotTypeName = cursor.getString(cursor
                        .getColumnIndex(SPOT_TYPE_FIELD));
                String spotSnippet = cursor.getString(cursor.getColumnIndex(SPOT_SNIPPET_FIELD));
                SpotInfo spotInfo = new SpotInfo(id, spotName, spotTypeName, spotSnippet);
                spotNameList.add(spotInfo);
                cursor.moveToNext();
            }
        }
        cursorClose(cursor);
        closeDB();
        return spotNameList;
    }

    public ArrayList<LatLongInfo> getLatLongInfo() {
        ArrayList<LatLongInfo> latLongList = new ArrayList<LatLongInfo>();
        DB = getReadableDatabase();
        Cursor cursor = DB
                .query(LAT_LONG_INFO_OF_ALL_SPOTS_TABLE, null, null, null, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                int id = cursor.getInt(cursor.getColumnIndex(ID_FIELD));
                String spotName = cursor.getString(cursor
                        .getColumnIndex(SPOT_NAME_FIELD));
                String spotSnippet = cursor.getString(cursor
                        .getColumnIndex(SPOT_SNIPPET_FIELD));
                String spotType = cursor.getString(cursor
                        .getColumnIndex(SPOT_TYPE_FIELD));
                double latitude = cursor.getDouble(cursor
                        .getColumnIndex(SPOT_LATITUDE_FIELD));
                double longtitude = cursor.getDouble(cursor
                        .getColumnIndex(SPOT_LONGTITUDE_FIELD));
                LatLongInfo latLongInfo = new LatLongInfo(id, spotName,
                        spotSnippet, spotType, latitude, longtitude);
                latLongList.add(latLongInfo);
                cursor.moveToNext();
            }
        }

        cursorClose(cursor);
        closeDB();
        return latLongList;

    }

    private synchronized void cursorClose(Cursor cursor) {
        if (cursor != null) {
            cursor.close();
        }
    }

    public int getNumOfFavouriteList(String spotName, String spotType) {
        DB = getReadableDatabase();
        Cursor cursor = DB.query(FAVOURITES_LIST_TABLE, null, SPOT_NAME_FIELD
                + " = ? and " + SPOT_TYPE_FIELD + " = ?", new String[]{
                spotName, spotType}, null, null, null);
        int numRows = 0;
        if (cursor != null && cursor.getCount() > 0) {
            numRows = cursor.getCount();

        }
        cursorClose(cursor);
        closeDB();
        return numRows;
    }

    public long insertIntoFavouriteList(String spotName, String spotType) {
        DB = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SPOT_NAME_FIELD, spotName);
        contentValues.put(SPOT_TYPE_FIELD, spotType);
        long res = DB.insert(FAVOURITES_LIST_TABLE, null, contentValues);
        return res;
    }

    public ArrayList<DocumentaryVideoInfo> getDocVideoInfo() {
        ArrayList<DocumentaryVideoInfo> documentaryVideoInfoList = null;
        DB = getReadableDatabase();
        Cursor cursor = DB
                .query(DOC_VIDEO_TABLE, null, null, null, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            documentaryVideoInfoList = new ArrayList<DocumentaryVideoInfo>();
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                int id = cursor.getInt(cursor.getColumnIndex(ID_FIELD));
                String name = cursor.getString(cursor
                        .getColumnIndex(DOC_NAME_FIELD));
                String key = cursor.getString(cursor.getColumnIndex(KEY_FIELD));
                DocumentaryVideoInfo documentaryVideoInfo = new DocumentaryVideoInfo(
                        id, name, key);
                documentaryVideoInfoList.add(documentaryVideoInfo);
                cursor.moveToNext();
            }
        }
        cursorClose(cursor);
        closeDB();
        return documentaryVideoInfoList;
    }

}
