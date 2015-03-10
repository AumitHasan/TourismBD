package com.apppreview.shuvojit.tourismbd.allpackges.databaseTablesModel;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.apppreview.shuvojit.tourismbd.allpackges.interfaces.DatabaseClient;

import java.util.List;

/**
 * Created by shuvojit on 3/8/15.
 */
@Table(name = "favourite_lists")

public class FavouritesListsTable extends Model implements DatabaseClient {


    @Column(name = "spotname")
    private String spotName;

    @Column(name = "spottypefield")
    private String spotType;


    public FavouritesListsTable() {
        super();
    }


    public FavouritesListsTable(String spotName, String spotType) {
        super();
    }

    public static List<FavouritesListsTable> getfavouritesListsTableModelDataList() {
        List<FavouritesListsTable> favouritesListsTableDataList = new Select()
                .from(FavouritesListsTable.class)
                .orderBy(SPOT_NAME_FIELD + " ASC")
                .execute();
        return favouritesListsTableDataList;
    }


    public static boolean isFavouriteSpotNameExist(String spotName, String spotType) {
        boolean isExist = false;
        isExist = new Select().from(FavouritesListsTable.class).where(SPOT_NAME_FIELD +
                " = ? " + "and " + SPOT_TYPE_FIELD + " = ?", spotName, spotType).exists();
        return isExist;
    }

    public static boolean removeSpotFrom(String spotName, String spotType) {
        boolean isRemoved = false;
        new Delete().
                from(FavouritesListsTable.class).
                where(SPOT_NAME_FIELD + " = ? and " + SPOT_TYPE_FIELD + " = ?", spotName, spotType)
                .execute();
        boolean isSpotExist = isFavouriteSpotNameExist(spotName, spotType);
        if (!isSpotExist) {
            isRemoved = true;
        }
        return isRemoved;
    }

    public String getSpotName() {
        return spotName;
    }

    public void setSpotName(String spotName) {
        this.spotName = spotName;
    }

    public String getSpotType() {
        return spotType;
    }

    public void setSpotType(String spotType) {
        this.spotType = spotType;
    }
}
