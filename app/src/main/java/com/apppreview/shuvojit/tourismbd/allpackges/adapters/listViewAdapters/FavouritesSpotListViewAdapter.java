package com.apppreview.shuvojit.tourismbd.allpackges.adapters.listViewAdapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.apppreview.shuvojit.tourismbd.R;
import com.apppreview.shuvojit.tourismbd.allpackges.activities.TouristSpotInfoActivity;
import com.apppreview.shuvojit.tourismbd.allpackges.databases.TourismGuiderDatabase;
import com.apppreview.shuvojit.tourismbd.allpackges.fragments.FavouritesFragment;
import com.apppreview.shuvojit.tourismbd.allpackges.infos.FavouritesSpotInfo;
import com.apppreview.shuvojit.tourismbd.allpackges.infos.LatLongInfo;
import com.apppreview.shuvojit.tourismbd.allpackges.infos.SpotInfo;
import com.apppreview.shuvojit.tourismbd.allpackges.interfaces.InitializerClient;

import java.util.ArrayList;

/**
 * Created by shuvojit on 3/5/15.
 */
public class FavouritesSpotListViewAdapter extends BaseAdapter implements InitializerClient {

    private ArrayList<FavouritesSpotInfo> favouritesSpotInfoArrayList;
    private Button btnSeeTouristSpot;
    private Button btnDiscardTouristSpot;
    private TextView txtSpotName;
    private ImageView imgSpotIcon;
    private Context context;
    private View adapterView;
    private String[] allSpotsName;
    private int[] allSpotsImages;
    private View popUpWindowView;
    private PopupWindow popupWindow;
    private Button btnDismiss;
    private Button btnCancel;
    private TextView txtPopUpWindowText;


    public FavouritesSpotListViewAdapter(Context context, ArrayList<FavouritesSpotInfo>
            favouritesSpotInfoArrayList) {
        this.context = context;
        this.favouritesSpotInfoArrayList = favouritesSpotInfoArrayList;
        allSpotsName = context.getResources().getStringArray(R.array.all_spots_name);
        TypedArray typedArray = context.getResources().
                obtainTypedArray(R.array.ic_of_all_spots);
        allSpotsImages = new int[typedArray.length()];
        for (int i = 0; i < allSpotsImages.length; i++) {
            allSpotsImages[i] = typedArray.getResourceId(i, 0);
        }
        typedArray.recycle();

    }

    public void notifyListView()
    {
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return favouritesSpotInfoArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return favouritesSpotInfoArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        adapterView = null;
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            adapterView = layoutInflater.
                    inflate(R.layout.favourites_spot_list_view_layout, null, false);

        } else {
            adapterView = convertView;
        }
        initialize();
        FavouritesSpotInfo favouritesSpotInfo = favouritesSpotInfoArrayList.get(position);
        txtSpotName.setText(favouritesSpotInfo.getSpotName());
        imgSpotIcon.setImageResource(getImageResourceID(favouritesSpotInfo.getSpotName()));
        if (btnSeeTouristSpot != null && btnDiscardTouristSpot != null) {
            setButtonClickListener(position);
        }
        return adapterView;
    }

    @Override
    public void initialize() {
        if (adapterView != null) {
            txtSpotName = (TextView) adapterView.findViewById(R.id.txt_tourist_spot_name);
            imgSpotIcon = (ImageView) adapterView.findViewById(R.id.ic_tourist_spot);
            btnDiscardTouristSpot = (Button) adapterView.
                    findViewById(R.id.btn_discard_tourist_spot);
            btnSeeTouristSpot = (Button) adapterView.
                    findViewById(R.id.btn_see_tourist_spot);
        }

    }

    public void setButtonClickListener(final int position) {

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int viewID = v.getId();
                switch (viewID) {
                    case R.id.btn_discard_tourist_spot:
                        Log.e(getClass().getName(), " button discard is clicked");
                        setPopUpWindow();
                        showPopUpWindow(FavouritesFragment.getParentView(), position);
                        break;
                    case R.id.btn_see_tourist_spot:
                        FavouritesSpotInfo favouritesSpotInfo = favouritesSpotInfoArrayList.
                                get(position);
                        setTourismSpotInfoActivity(favouritesSpotInfo.getSpotName(),
                                favouritesSpotInfo.getSpotTypeInfo());
                        Log.e(getClass().getName(), " button see is clicked");
                        break;
                }
            }
        };

        btnDiscardTouristSpot.setOnClickListener(onClickListener);
        btnSeeTouristSpot.setOnClickListener(onClickListener);
    }

    private void setTourismSpotInfoActivity(String spotName, String spotType) {
        TourismGuiderDatabase tourismGuiderDatabase = TourismGuiderDatabase.
                getTourismGuiderDatabase(context);
        SpotInfo spotInfo = tourismGuiderDatabase.getSpotInfo(spotName, spotType);
        LatLongInfo latLongInfo = tourismGuiderDatabase.getLatLongInfo(spotName, spotType);
        if (spotInfo != null && latLongInfo != null) {
            Log.d(getClass().getName(), "found");
            Intent intent = new Intent(context, TouristSpotInfoActivity.class);
            intent.putExtra("SpotInfo", spotInfo);
            intent.putExtra("SpotLatLongInfo", latLongInfo);
            tourismGuiderDatabase.close();
            context.startActivity(intent);
        }

    }


    private int getImageResourceID(String spotName) {
        int resourceID = 0;
        if (allSpotsName != null && allSpotsName.length > 0 && allSpotsImages != null &&
                allSpotsImages.length > 0) {
            for (int i = 0; i < allSpotsName.length; i++) {
                if (spotName.equalsIgnoreCase(allSpotsName[i])) {
                    resourceID = allSpotsImages[i];
                    break;
                }
            }
        }

        return resourceID;

    }

    private void removeListItem(int position) {
        FavouritesSpotInfo favouritesSpotInfo = favouritesSpotInfoArrayList.get(position);
        if (favouritesSpotInfo != null) {
            TourismGuiderDatabase tourismGuiderDatabase = TourismGuiderDatabase.
                    getTourismGuiderDatabase(context);
            int res = tourismGuiderDatabase.removeSpotFrom(favouritesSpotInfo.getSpotName());
            if (res == 1) {
                favouritesSpotInfoArrayList.remove(position);
                notifyDataSetChanged();
                Log.e(getClass().getName(), "Favourites list item deleted");
            }
            tourismGuiderDatabase.close();
        }
        if (favouritesSpotInfoArrayList.size() == 0) {
            Toast.makeText(context, "No tourist spot has been added to favourites list",
                    Toast.LENGTH_LONG).show();
        }

    }

    private void setPopUpWindow() {
        if (context != null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            popUpWindowView = layoutInflater.inflate(R.layout.pop_up_window_layout, null);
            popupWindow = new PopupWindow(popUpWindowView, ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT, true);
            initializePopUpwindowElements();
        }
    }

    private void initializePopUpwindowElements() {
        if (popUpWindowView != null) {
            txtPopUpWindowText = (TextView) popUpWindowView.
                    findViewById(R.id.txt_pop_up_window_message);
            btnDismiss = (Button) popUpWindowView.findViewById(R.id.btn_yes);
            btnCancel = (Button) popUpWindowView.findViewById(R.id.btn_no);
        }
    }

    private void showPopUpWindow(final View parentView, final int position) {
        FavouritesSpotInfo favouritesSpotInfo = favouritesSpotInfoArrayList.get(position);
        if (parentView != null && popupWindow != null && txtPopUpWindowText != null
                && favouritesSpotInfo != null) {
            String msg = null;
            String spotName = favouritesSpotInfo.getSpotName();
            msg = "Do you want to remove " + spotName + " from your favourites list";
            txtPopUpWindowText.setText(msg);
            popupWindow.showAtLocation(parentView, Gravity.CENTER, 0, 0);
            View.OnClickListener onClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int viewID = v.getId();
                    switch (viewID) {
                        case R.id.btn_yes:
                            removeListItem(position);
                            break;
                        case R.id.btn_no:
                            break;
                    }
                    if (popupWindow.isShowing()) {
                        popupWindow.dismiss();
                    }
                }
            };
            btnDismiss.setOnClickListener(onClickListener);
            btnCancel.setOnClickListener(onClickListener);
        }
    }


}











