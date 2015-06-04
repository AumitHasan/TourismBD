package com.apppreview.shuvojit.tourismbd.allpackges.dialogs;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.util.Log;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by shuvojit on 4/30/15.
 */
public class UserNotifiedDialog {

    public static Context context;
    private String dialogType;
    private String msg;
    private SweetAlertDialog sweetAlertDialog;
    private String phoneNumber;


    public UserNotifiedDialog(Context context, final String DIALOG_TYPE, String msg) {
        this.context = context;
        this.msg = msg;
        dialogType = DIALOG_TYPE;

    }

    public UserNotifiedDialog(
            Context context, final String DIALOG_TYPE, String msg, String phoneNumber) {
        this.context = context;
        this.msg = msg;
        dialogType = DIALOG_TYPE;
        this.phoneNumber = phoneNumber;
    }

    public void showDialog() {
        if (context != null && msg != null) {
            sweetAlertDialog = new SweetAlertDialog(context);
            if (dialogType.equals("Location Service Alert")) {
                setProgressDialogForLocationServiceStartAlert(sweetAlertDialog);
            } else if (dialogType.equals("Internet Connection Alert")) {
                setProgressDialogForInternetStartAlert();
            }
            Log.e(getClass().getName(), "Dialog is shown");
        }
    }


    private void setProgressDialogForInternetStartAlert() {
        if (sweetAlertDialog != null) {
            sweetAlertDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
            sweetAlertDialog.setTitleText("Internet connection turned off");
            sweetAlertDialog.setContentText(msg);
            sweetAlertDialog.setCancelText("Dismiss");
            sweetAlertDialog.showCancelButton(true);
            sweetAlertDialog.setConfirmText("Proceed");
            sweetAlertDialog.setCancelable(true);
            sweetAlertDialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    closeDialog();
                }
            });
            sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(final SweetAlertDialog sweetAlertDialog) {
                    openInternetSettingsActivity();
                    closeDialog();

                }
            });
            sweetAlertDialog.show();
        }

    }

    private void openInternetSettingsActivity() {
        Intent intent = new Intent(Settings.ACTION_SETTINGS);
        context.startActivity(intent);
    }

    private void setProgressDialogForLocationServiceStartAlert(SweetAlertDialog sweetAlertDialog) {
        if (sweetAlertDialog != null) {
            sweetAlertDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
            sweetAlertDialog.setTitleText("Location service turned off");
            sweetAlertDialog.setContentText(msg);
            sweetAlertDialog.setCancelText("Dismiss");
            sweetAlertDialog.showCancelButton(true);
            sweetAlertDialog.setConfirmText("Proceed");
            sweetAlertDialog.setCancelable(true);
            sweetAlertDialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    closeDialog();
                }
            });
            sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(final SweetAlertDialog sweetAlertDialog) {
                    openLocationSettingsActivity();
                    closeDialog();

                }
            });
            sweetAlertDialog.show();
        }

    }

    private void openLocationSettingsActivity() {
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        context.startActivity(intent);
    }


    public void closeDialog() {
        if (sweetAlertDialog != null) {
            sweetAlertDialog.dismiss();
            sweetAlertDialog = null;
        }
    }

    public boolean isShowing() {
        try {
            return sweetAlertDialog.isShowing();
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
