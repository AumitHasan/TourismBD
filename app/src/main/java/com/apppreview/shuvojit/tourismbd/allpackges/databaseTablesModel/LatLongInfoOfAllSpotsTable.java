package com.apppreview.shuvojit.tourismbd.allpackges.databaseTablesModel;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.apppreview.shuvojit.tourismbd.allpackges.interfaces.DatabaseClient;

import java.io.Serializable;
import java.util.List;

/**
 * Created by shuvojit on 3/7/15.
 */

@Table(name = "latlonginfoofallspots")
public class LatLongInfoOfAllSpotsTable extends Model implements DatabaseClient, Serializable {

    @Column(name = "spotname")
    private String spotName;

    @Column(name = "spotsnippet")
    private String spotSnippet;

    @Column(name = "spottypefield")
    private String spotTypeField;

    @Column(name = "spotlatitudefield")
    private double spotlatitudefield;

    @Column(name = "spotlongtitudefield")
    private double spotLongtitudeField;

    public LatLongInfoOfAllSpotsTable() {
        super();
    }

    public static List<LatLongInfoOfAllSpotsTable> getAllLatLongInfoOfAllSpotsTableData() {
        List<LatLongInfoOfAllSpotsTable> latLongInfoOfAllSpotsTableDataList = null;
        latLongInfoOfAllSpotsTableDataList = new Select().from
                (LatLongInfoOfAllSpotsTable.class).execute();
        /*if(latLongInfoOfAllSpotsTableDataList != null &&
                latLongInfoOfAllSpotsTableDataList.size() > 0)
        {
            Log.e(getClass().getName(),"Not Null");
        }
        else
        {
            Log.e(getClass().getName(),"NUll");
        }*/
        return latLongInfoOfAllSpotsTableDataList;
    }


    public static List<LatLongInfoOfAllSpotsTable> getAllLatLongInfoOfAllSpotsTableData
            (String spotType) {
        List<LatLongInfoOfAllSpotsTable> latLongInfoOfAllSpotsTableDataList = null;
        latLongInfoOfAllSpotsTableDataList = new Select()
                .from(LatLongInfoOfAllSpotsTable.class)
                .where(SPOT_TYPE_FIELD + " = ?", spotType)
                .orderBy(SPOT_NAME_FIELD + " ASC")
                .execute();
        return latLongInfoOfAllSpotsTableDataList;

    }

    public static LatLongInfoOfAllSpotsTable getLatLongInfoOfSpotTableData
            (String spotName, String spotType) {
        LatLongInfoOfAllSpotsTable latLongInfoOfAllSpotsTableData = null;
        latLongInfoOfAllSpotsTableData = new Select()
                .from(LatLongInfoOfAllSpotsTable.class)
                .where(SPOT_NAME_FIELD + " = ? and " + SPOT_TYPE_FIELD + " = ?", spotName,
                        spotType)
                .executeSingle();

        return latLongInfoOfAllSpotsTableData;

    }


    public String getSpotName() {
        return spotName;
    }

    public void setSpotName(String spotName) {
        this.spotName = spotName;
    }

    public String getSpotSnippet() {
        return spotSnippet;
    }

    public void setSpotSnippet(String spotSnippet) {
        this.spotSnippet = spotSnippet;
    }

    public String getSpotTypeField() {
        return spotTypeField;
    }

    public void setSpotTypeField(String spotTypeField) {
        this.spotTypeField = spotTypeField;
    }

    public double getSpotlatitudefield() {
        return spotlatitudefield;
    }

    public void setSpotlatitudefield(double spotlatitudefield) {
        this.spotlatitudefield = spotlatitudefield;
    }

    public double getSpotLongtitudeField() {
        return spotLongtitudeField;
    }

    public void setSpotLongtitudeField(double spotLongtitudeField) {
        this.spotLongtitudeField = spotLongtitudeField;
    }
}
