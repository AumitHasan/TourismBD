package com.apppreview.shuvojit.tourismbd.allpackges.databaseTablesModel;

import android.util.Log;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.apppreview.shuvojit.tourismbd.allpackges.interfaces.DatabaseClient;

/**
 * Created by shuvojit on 3/8/15.
 */

@Table(name = "spotinfo")

public class SpotInfoTable extends Model implements DatabaseClient {

    @Column(name = "spotname")
    private String spotName;

    @Column(name = "spottypefield")
    private String spotTypeField;

    @Column(name = "discription")
    private String discription;

    @Column(name = "famous_spots")
    private String famousSpots;

    @Column(name = "hotels_info")
    private String hotelsInfo;

    @Column(name = "location")
    private String location;

    @Column(name = "url_link")
    private String urlLink;

    public SpotInfoTable() {
        super();
    }

    public static SpotInfoTable getSpotInfoTableData(String spotName, String spotType) {
        SpotInfoTable spotInfoTableData = null;
       /* String[] selectedColumns = new String[]{
                SPOT_NAME_FIELD, DISCRIPTION_FIELD, FAMOUS_SPOTS_FIELD, HOTELS_INFO,
                LOCATION_FIELD, URL_LINK_INFO
        };*/
        spotInfoTableData = new Select().
                from(SpotInfoTable.class)
                .where(SPOT_NAME_FIELD + " = ?", spotName)
                .executeSingle();
       /* if (spotInfoTableData != null) {
            Log.e(getClass().getName(), "Not Null");
        } else {
            Log.e(getClass().getName(), "Null");
        }*/
        return spotInfoTableData;
    }


    public String getSpotName() {
        return spotName;
    }

    public void setSpotName(String spotName) {
        this.spotName = spotName;
    }

    public String getSpotTypeField() {
        return spotTypeField;
    }

    public void setSpotTypeField(String spotTypeField) {
        this.spotTypeField = spotTypeField;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public String getFamousSpots() {
        return famousSpots;
    }

    public void setFamousSpots(String famousSpots) {
        this.famousSpots = famousSpots;
    }

    public String getHotelsInfo() {
        return hotelsInfo;
    }

    public void setHotelsInfo(String hotelsInfo) {
        this.hotelsInfo = hotelsInfo;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getUrlLink() {
        return urlLink;
    }

    public void setUrlLink(String urlLink) {
        this.urlLink = urlLink;
    }
}


