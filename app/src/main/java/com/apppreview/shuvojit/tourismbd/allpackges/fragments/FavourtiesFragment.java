package com.apppreview.shuvojit.tourismbd.allpackges.fragments;


import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.apppreview.shuvojit.tourismbd.R;
import com.apppreview.shuvojit.tourismbd.allpackges.activities.TouristSpotInfoActivity;
import com.apppreview.shuvojit.tourismbd.allpackges.databases.TourismGuiderDatabase;
import com.apppreview.shuvojit.tourismbd.allpackges.infos.FavouritesSpotInfo;
import com.apppreview.shuvojit.tourismbd.allpackges.infos.LatLongInfo;
import com.apppreview.shuvojit.tourismbd.allpackges.infos.SpotInfo;
import com.apppreview.shuvojit.tourismbd.allpackges.interfaces.AdapterInterface;
import com.apppreview.shuvojit.tourismbd.allpackges.interfaces.Intializer;

import java.util.ArrayList;


public class FavourtiesFragment extends Fragment implements
        OnItemClickListener, OnItemLongClickListener, Intializer {

    private TourismGuiderDatabase tourismGuiderDatabase;
    private View fragmentView;
    private ArrayList<FavouritesSpotInfo> favouritesSpotInfoList;
    private ListView listView;
    private Context context;
    private String[] allSpotsName;
    private int[] allSpotsIcon;
    private ListViewAdapter listViewAdapter;
    private Intent intent;
    private TextView txtPopUpWindowMessage;
    private Button btnRemoveSpotYes;
    private Button btnRemoveSpotNo;
    private PopupWindow popupWindow;
    private String removedSpotName;
    private boolean isChanged;
    private int removedPosition;

    private OnClickListener btnOnClickListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            int id = v.getId();
            switch (id) {
                case R.id.btn_remove_spot_yes:
                    if (removedSpotName != null) {
                        favouritesSpotInfoList.remove(removedPosition);
                        int isRemoved = tourismGuiderDatabase.removeSpotFrom(removedSpotName);
                        if (isRemoved == 1) {
                            Log.e(getClass().getName(), "Removed");
                            isChanged = true;
                            onResume();
                        } else {
                            Log.e(getClass().getName(), "not Removed");
                        }
                        onResume();
                    }
                    break;
                case R.id.btn_remove_spot_no:
                    break;
                default:
                    break;
            }
            popupWindow.dismiss();

        }

    };

    public FavourtiesFragment() {


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.favourites_fragment_layout,
                container, false);
        intialize();
        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(this);
        return fragmentView;

    }

    @Override
    public void intialize() {
        isChanged = false;
        context = getActivity();
        allSpotsName = context.getResources().getStringArray(
                R.array.all_spots_name);
        listView = (ListView) fragmentView.findViewById(R.id.favourites_list);
        tourismGuiderDatabase = TourismGuiderDatabase.getTourismGuiderDatabase(context);
        favouritesSpotInfoList = tourismGuiderDatabase.getFavouritesSpotInfoList();
        TypedArray ar = getResources()
                .obtainTypedArray(R.array.ic_of_all_spots);
        allSpotsIcon = new int[ar.length()];
        for (int i = 0; i < ar.length(); i++) {
            allSpotsIcon[i] = ar.getResourceId(i, 0);
        }
        ar.recycle();
        if (favouritesSpotInfoList == null) {
            Toast.makeText(context, "No favourites has been added",
                    Toast.LENGTH_LONG).show();
        } else {
            sortFavouriteSpotNameList();
            listViewAdapter = new ListViewAdapter();
            listView.setAdapter(listViewAdapter);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        if (isChanged) {
            listViewAdapter.notifyDataSetChanged();
            if (favouritesSpotInfoList.size() == 0) {
                Toast.makeText(context, "No favourites has been added",
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        tourismGuiderDatabase.close();
        Log.e(getClass().getName(), "Database close");
    }

    private void sortFavouriteSpotNameList() {
        FavouritesSpotInfo favouritesSpotInfoForPresentIndex = null;
        FavouritesSpotInfo favouritesSpotInfoForForwardIndex = null;
        String spotNamePresentIndex = null;
        String spotNameForwardIndex = null;
        int k = favouritesSpotInfoList.size() - 1;
        while (k != 0) {
            int t = 0;
            for (int i = 0; i <= k - 1; i++) {
                favouritesSpotInfoForPresentIndex = favouritesSpotInfoList
                        .get(i);
                favouritesSpotInfoForForwardIndex = favouritesSpotInfoList
                        .get(i + 1);
                spotNamePresentIndex = favouritesSpotInfoForPresentIndex
                        .getSpotName();
                spotNameForwardIndex = favouritesSpotInfoForForwardIndex
                        .getSpotName();
                int j = 0;
                int l = 0;
                boolean flag = false;

                while (j < spotNamePresentIndex.length()
                        && l < spotNameForwardIndex.length()) {

                    if (spotNamePresentIndex.charAt(j) > spotNameForwardIndex
                            .charAt(l)) {
                        flag = true;
                        break;
                    } else if (spotNamePresentIndex.charAt(j) < spotNameForwardIndex
                            .charAt(l)) {
                        flag = false;
                        break;
                    }
                    j++;
                    l++;
                }

                if (flag) {
                    favouritesSpotInfoList.set(i,
                            favouritesSpotInfoForForwardIndex);
                    favouritesSpotInfoList.set(i + 1,
                            favouritesSpotInfoForPresentIndex);
                    t = i;
                }

            }
            k = t;

        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        setSpotActivity(position);

    }

    private void setSpotActivity(int position) {
        FavouritesSpotInfo fSpotInfo = favouritesSpotInfoList.get(position);
        String spotName = fSpotInfo.getSpotName();
        String spotType = fSpotInfo.getSpotTypeInfo();
        SpotInfo spotInfo = tourismGuiderDatabase.getSpotInfo(spotName, spotType);
        LatLongInfo latLongInfo = tourismGuiderDatabase.getLatLongInfo(spotName, spotType);
        if (spotInfo != null && latLongInfo != null) {
            Log.d(getClass().getName(), "found");
            intent = new Intent(getActivity(), TouristSpotInfoActivity.class);
            intent.putExtra("SpotInfo", spotInfo);
            intent.putExtra("SpotLatLongInfo", latLongInfo);
            startActivity(intent);
        }

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view,
                                   int position, long id) {
        setPopUpWindow(position);
        return true;
    }

    @SuppressLint("InflateParams")
    private void setPopUpWindow(int position) {
        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popUpView = layoutInflater.inflate(R.layout.pop_up_window_layout,
                null);
        popupWindow = new PopupWindow(popUpView, 400, 300, true);
        popupWindow.showAtLocation(fragmentView, Gravity.CENTER, 0, 0);
        intializePopupWindowElements(popUpView, position);

    }

    private void intializePopupWindowElements(View popUpView, int position) {
        txtPopUpWindowMessage = (TextView) popUpView
                .findViewById(R.id.txt_pop_up_window_message);
        btnRemoveSpotNo = (Button) popUpView
                .findViewById(R.id.btn_remove_spot_no);
        btnRemoveSpotYes = (Button) popUpView
                .findViewById(R.id.btn_remove_spot_yes);
        FavouritesSpotInfo favouritesSpotInfo = favouritesSpotInfoList
                .get(position);
        removedSpotName = favouritesSpotInfo.getSpotName();
        txtPopUpWindowMessage.setText("Do you want to remove "
                + favouritesSpotInfo.getSpotName()
                + " from your favourites list?");
        removedPosition = position;
        btnRemoveSpotNo.setOnClickListener(btnOnClickListener);
        btnRemoveSpotYes.setOnClickListener(btnOnClickListener);

    }

    private class ListViewAdapter extends BaseAdapter implements
            AdapterInterface {

        private TextView txtSpotName;
        private ImageView imgSpotIcon;
        private FavouritesSpotInfo favouritesSpotInfo;

        public ListViewAdapter() {

        }

        @Override
        public int getCount() {

            return favouritesSpotInfoList.size();
        }

        @Override
        public Object getItem(int position) {

            return favouritesSpotInfoList.get(position);
        }

        @Override
        public long getItemId(int position) {

            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View adapterView = null;
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                adapterView = inflater.inflate(R.layout.spot_item_list_view_layout,
                        parent, false);
            } else {
                adapterView = convertView;
            }

            intializeAdapterView(adapterView);
            favouritesSpotInfo = favouritesSpotInfoList.get(position);
            txtSpotName.setText(favouritesSpotInfo.getSpotName());
            setIcon(favouritesSpotInfo.getSpotName());
            return adapterView;
        }

        @Override
        public void setIcon(String spotName) {
            int position = 0;
            for (int i = 0; i < allSpotsName.length; i++) {
                if (spotName.equalsIgnoreCase(allSpotsName[i])) {
                    position = i;
                    break;
                }
            }
            imgSpotIcon.setImageResource((int) allSpotsIcon[position]);

        }

        @Override
        public void intializeAdapterView(View adapterView) {
            txtSpotName = (TextView) adapterView
                    .findViewById(R.id.txt_site_name);
            imgSpotIcon = (ImageView) adapterView
                    .findViewById(R.id.img_site_ic);

        }

    }

}
