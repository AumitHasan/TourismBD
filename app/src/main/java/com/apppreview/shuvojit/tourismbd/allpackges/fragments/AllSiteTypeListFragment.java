package com.apppreview.shuvojit.tourismbd.allpackges.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.apppreview.shuvojit.tourismbd.R;
import com.apppreview.shuvojit.tourismbd.allpackges.activities.TouristSpotInfoActivity;
import com.apppreview.shuvojit.tourismbd.allpackges.adapters.listViewAdapters.ListViewAdapterForSitesList;
import com.apppreview.shuvojit.tourismbd.allpackges.databases.TourismGuiderDatabase;
import com.apppreview.shuvojit.tourismbd.allpackges.infos.LatLongInfo;
import com.apppreview.shuvojit.tourismbd.allpackges.infos.SpotInfo;
import com.apppreview.shuvojit.tourismbd.allpackges.interfaces.Intializer;

import java.util.ArrayList;


public class AllSiteTypeListFragment extends Fragment implements Intializer {

    private static TourismGuiderDatabase tourismGuiderDatabase;
    OnItemClickListener itemClickListener = new OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            setSpotActivity(position);
        }
    };
    private View fragmentView;
    private Context context;
    private ListViewAdapterForSitesList listViewAdapterForSitesList;
    private ListView listView;
    private String spotType;
    private Intent intent;
    private ArrayList<SpotInfo> spotInfoList;

    public AllSiteTypeListFragment() {


    }

    public static AllSiteTypeListFragment newInstance(String spotType) {
        AllSiteTypeListFragment allSiteTypeListFragment = new AllSiteTypeListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("Spot Type", spotType);
        allSiteTypeListFragment.setArguments(bundle);
        return allSiteTypeListFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        spotType = bundle.getString("Spot Type");
        fragmentView = inflater.inflate(R.layout.spot_fragment_layout, container,
                false);
        intialize();
        return fragmentView;
    }

    @Override
    public void intialize() {
        if (fragmentView != null) {
            context = getActivity();
            listView = (ListView) fragmentView
                    .findViewById(R.id.list_view_for_sites);
            tourismGuiderDatabase = TourismGuiderDatabase.getTourismGuiderDatabase(context);
            spotInfoList = tourismGuiderDatabase.getSpotNameList(spotType);
            sortSpotNameList();
            listViewAdapterForSitesList = new ListViewAdapterForSitesList(
                    context, spotInfoList);
            listView.setAdapter(listViewAdapterForSitesList);
            listView.setOnItemClickListener(itemClickListener);
        }

    }

    private void setSpotActivity(int position) {
        SpotInfo spotInfoItem = spotInfoList.get(position);
        String spotName = spotInfoItem.getSpotName();
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
    public void onDestroy() {
        super.onDestroy();
        tourismGuiderDatabase.close();
        Log.e(getClass().getName(), "Database close");
    }

    private void sortSpotNameList() {

        SpotInfo spotInfoForPresentIndex = null;
        SpotInfo spotInfoForForwardIndex = null;
        String spotNamePresentIndex = null;
        String spotNameForwardIndex = null;
        int k = spotInfoList.size() - 1;
        while (k != 0) {
            int t = 0;
            for (int i = 0; i <= k - 1; i++) {
                spotInfoForPresentIndex = spotInfoList.get(i);
                spotInfoForForwardIndex = spotInfoList.get(i + 1);
                spotNamePresentIndex = spotInfoForPresentIndex.getSpotName();
                spotNameForwardIndex = spotInfoForForwardIndex.getSpotName();
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
                    spotInfoList.set(i, spotInfoForForwardIndex);
                    spotInfoList.set(i + 1, spotInfoForPresentIndex);
                    t = i;
                }

            }
            k = t;

        }

    }

}
