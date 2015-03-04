package com.apppreview.shuvojit.tourismbd.allpackges.popUpWindows;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.apppreview.shuvojit.tourismbd.R;
import com.apppreview.shuvojit.tourismbd.allpackges.activities.TouristSpotInfoActivity;
import com.apppreview.shuvojit.tourismbd.allpackges.databases.TourismGuiderDatabase;
import com.apppreview.shuvojit.tourismbd.allpackges.interfaces.InitializerClient;

/**
 * Created by shuvojit on 3/3/15.
 */
public class UserChoicePromptPopUpWindow implements InitializerClient, View.OnClickListener {

    private Context context;
    private String popUpWindowType;
    private PopupWindow popupWindow;
    private View popUpWindowView;
    private TextView txtPopUpWindowText;
    private Button btnYes;
    private Button btnNo;
    private String spotType;
    private String spotName;
    private boolean dataInserted = false;
    private boolean dataDeleted = false;
    private MenuItem favourtiesMenu;


    public UserChoicePromptPopUpWindow(Context context, String popUpWindowType ,
                                       MenuItem favourtiesMenu) {
        this.context = context;
        this.popUpWindowType = popUpWindowType;
        this.favourtiesMenu = favourtiesMenu;
    }


    public void setPopUpWindow() {
        if (context != null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            popUpWindowView = layoutInflater.inflate(R.layout.pop_up_window_layout, null);
            popupWindow = new PopupWindow(popUpWindowView, ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT, true);
            initialize();
            if (popUpWindowType.equalsIgnoreCase("Insert Data")) {
                btnYes.setText("INSERT");
            }
            btnNo.setOnClickListener(this);
            btnYes.setOnClickListener(this);

        }
    }


    @Override
    public void initialize() {
        if (popUpWindowView != null) {
            txtPopUpWindowText = (TextView) popUpWindowView.
                    findViewById(R.id.txt_pop_up_window_message);
            btnYes = (Button) popUpWindowView.findViewById(R.id.btn_yes);
            btnNo = (Button) popUpWindowView.findViewById(R.id.btn_no);
        }
    }


    public void showPopUpWindow(View parentView, String spotType, String spotName) {
        if (parentView != null && popupWindow != null && txtPopUpWindowText != null) {
            String msg = null;
            this.spotName = spotName;
            this.spotType = spotType;
            if (popUpWindowType.equalsIgnoreCase("Insert Data")) {
                msg = "Do you want to add " + spotName + " to your favourites list";
            } else {
                msg = "Do you want to remove " + spotName + " from your favourites list";
            }
            txtPopUpWindowText.setText(msg);
            popupWindow.showAtLocation(parentView, Gravity.CENTER, 0, 0);
        }
    }


    @Override
    public void onClick(View v) {
        int viewID = v.getId();
        switch (viewID) {
            case R.id.btn_yes:
                TourismGuiderDatabase tourismGuiderDatabase =
                        TourismGuiderDatabase.getTourismGuiderDatabase(context);
                if (tourismGuiderDatabase != null) {
                    if (popUpWindowType.equalsIgnoreCase("Insert Data")) {
                        insertDataIntoDataBase(tourismGuiderDatabase);
                    } else {
                        deleteDataFromDatabase(tourismGuiderDatabase);
                    }
                    tourismGuiderDatabase.close();
                }
                break;
            case R.id.btn_no:
                break;
        }
        dismissPopUpWindow();

    }

    private void deleteDataFromDatabase(TourismGuiderDatabase tourismGuiderDatabase) {
        int res = tourismGuiderDatabase.removeSpotFrom(spotName);
        if (res == 1) {
            dataDeleted = true;
            Log.e(getClass().getName(), "Data is deleted");
            TouristSpotInfoActivity.isSpotNameExist = false;
            favourtiesMenu.setIcon(R.drawable.ic_action_favorite_dark);
        }
    }

    private void insertDataIntoDataBase(TourismGuiderDatabase tourismGuiderDatabase) {
        long res = tourismGuiderDatabase.insertIntoFavouriteList(spotName, spotType);
        if (res >= 1) {
            dataInserted = true;
            Log.e(getClass().getName(), "Data is inserted");
            TouristSpotInfoActivity.isSpotNameExist = true;
            favourtiesMenu.setIcon(R.drawable.ic_action_favorite);

        }
    }


    public boolean isDataInserted() {
        return dataInserted;
    }

    public boolean isDataDeleted() {
        return dataDeleted;
    }

    public void dismissPopUpWindow() {
        if (popupWindow.isShowing()) {

            popupWindow.dismiss();
        }
    }
}
