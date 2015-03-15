package com.apppreview.shuvojit.tourismbd.allpackges.popUpWindows.progressDialogs;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;

import com.apppreview.shuvojit.tourismbd.R;
import com.apppreview.shuvojit.tourismbd.allpackges.activities.TouristSpotInfoActivity;
import com.apppreview.shuvojit.tourismbd.allpackges.databaseTablesModel.FavouritesListsTable;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by shuvojit on 3/3/15.
 */
public final class UserChoicePromptPopUpWindow {


    public static void showInsertTouristSpotIntoFavouritesListProgressDialog
            (final MenuItem favouritesMenuItem, final Context context,
             final String spotName, final String spotType) {
        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(context,
                SweetAlertDialog.WARNING_TYPE);
        sweetAlertDialog.setTitleText("Add")
                .setContentText(spotName + " to favourites")
                .setConfirmText("Add")
                .setCancelText("Cancel")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.showCancelButton(false);
                        insertTouristSpotIntoDataBase(favouritesMenuItem, spotName, spotType);
                        sweetAlertDialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                        sweetAlertDialog.setTitleText("Added in favourites!")
                                .setContentText(null)
                                .setConfirmText("Ok")
                                .setConfirmClickListener(null);


                    }
                })
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismissWithAnimation();
                    }
                })
                .show();
    }



    public static void showDeleteTouristSpotFromFavouritesListProgressDialog
            (final MenuItem favouritesMenuItem, final Context context,
             final String spotName, final String spotType) {
        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(context,
                SweetAlertDialog.WARNING_TYPE);
        sweetAlertDialog.setTitleText("Delete")
                .setContentText(spotName + " From favourites")
                .setConfirmText("Yes")
                .setCancelText("No")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.showCancelButton(false);
                        deleteTouristSpotFromDatabase(favouritesMenuItem, spotName, spotType);
                        sweetAlertDialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                        sweetAlertDialog.setTitleText("Deleted!")
                                .setConfirmText("Ok")
                                .setConfirmClickListener(null);

                    }
                })
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismissWithAnimation();
                    }
                })
                .show();
    }

    private static void deleteTouristSpotFromDatabase(MenuItem favouritesMenu, String spotName,
                                                      String spotType) {
        boolean res = FavouritesListsTable.removeSpotFrom(spotName, spotType);
        if (res && favouritesMenu != null) {
            TouristSpotInfoActivity.isSpotNameExist = false;
            favouritesMenu.setIcon(R.drawable.ic_action_favorite_dark);
        }

    }


    private static void insertTouristSpotIntoDataBase(MenuItem favouritesMenu, String spotName,
                                                      String spotType) {
        FavouritesListsTable favouritesListsTable = new
                FavouritesListsTable();
        favouritesListsTable.setSpotName(spotName);
        favouritesListsTable.setSpotType(spotType);
        favouritesListsTable.save();
        TouristSpotInfoActivity.isSpotNameExist = true;
        favouritesMenu.setIcon(R.drawable.ic_action_favorite);
    }

    public static void closeApp(final ActionBarActivity activity)
    {
        if(activity != null)
        {
            SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(activity,
                    SweetAlertDialog.WARNING_TYPE);
            sweetAlertDialog
                    .setTitleText("Exit?")
                    .setConfirmText("Yes")
                    .setCancelText("No")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            activity.finish();
                            sweetAlertDialog.dismissWithAnimation();

                        }
                    })
                    .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismissWithAnimation();
                        }
                    });
            sweetAlertDialog.setCancelable(true);
            sweetAlertDialog.show();

        }
    }


}
