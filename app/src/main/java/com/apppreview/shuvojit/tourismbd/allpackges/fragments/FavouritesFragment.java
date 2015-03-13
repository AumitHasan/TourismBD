package com.apppreview.shuvojit.tourismbd.allpackges.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.apppreview.shuvojit.tourismbd.R;
import com.apppreview.shuvojit.tourismbd.allpackges.adapters.listViewAdapters.FavouritesSpotListViewAdapter;
import com.apppreview.shuvojit.tourismbd.allpackges.databaseTablesModel.FavouritesListsTable;
import com.apppreview.shuvojit.tourismbd.allpackges.databaseTablesModel.LatLongInfoOfAllSpotsTable;
import com.apppreview.shuvojit.tourismbd.allpackges.interfaces.InitializerClient;

import java.util.ArrayList;
import java.util.List;


public class FavouritesFragment extends Fragment implements
        InitializerClient {

    private static Context context;
    private View fragmentView;
    private List<FavouritesListsTable> favouritesSpotInfoArrayList;
    private ListView listView;
    private FavouritesSpotListViewAdapter favouritesSpotListViewAdapter;
    private int arrayListSize = 0;


    public FavouritesFragment() {


    }

    public static FavouritesFragment getNewInstance() {
        return new FavouritesFragment();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.favourites_fragment_layout,
                container, false);
        initialize();

        return fragmentView;

    }

    @Override
    public void initialize() {
        context = getActivity();
        listView = (ListView) fragmentView.findViewById(R.id.favourites_list_view);
        favouritesSpotInfoArrayList = FavouritesListsTable.getfavouritesListsTableModelDataList();
        if (favouritesSpotInfoArrayList != null) {
            //sortFavouriteSpotNameList();
            arrayListSize = favouritesSpotInfoArrayList.size();
            favouritesSpotListViewAdapter = new FavouritesSpotListViewAdapter(context,
                    favouritesSpotInfoArrayList);
            listView.setAdapter(favouritesSpotListViewAdapter);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        List<FavouritesListsTable> newfavouritesSpotInfos = FavouritesListsTable.
                getfavouritesListsTableModelDataList();
        Log.e(getClass().getName(), newfavouritesSpotInfos.size()+"");
        if (newfavouritesSpotInfos != null && newfavouritesSpotInfos.size() > 0 &&
                newfavouritesSpotInfos.size() != arrayListSize) {
            Log.e(getClass().getName(), "new favourites list has been found");
            favouritesSpotInfoArrayList = newfavouritesSpotInfos;
            arrayListSize = favouritesSpotInfoArrayList.size();
            favouritesSpotListViewAdapter = new FavouritesSpotListViewAdapter(context,
                    favouritesSpotInfoArrayList);
            listView.setAdapter(favouritesSpotListViewAdapter);
        } else if (newfavouritesSpotInfos != null && newfavouritesSpotInfos.size() == 0) {
            if (favouritesSpotListViewAdapter != null) {
                favouritesSpotInfoArrayList.clear();
                favouritesSpotListViewAdapter.notifyListView();
                Log.e(getClass().getName(), "Not null is adapter");
            }
            Toast.makeText(context, "No tourist spot has been added to favourites list",
                    Toast.LENGTH_LONG).show();
            Log.e(getClass().getName(), "No list item is exist");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();


    }


    private void sortFavouriteSpotNameList() {
        FavouritesListsTable favouritesSpotInfoForPresentIndex = null;
        FavouritesListsTable favouritesSpotInfoForForwardIndex = null;
        String spotNamePresentIndex = null;
        String spotNameForwardIndex = null;
        int k = favouritesSpotInfoArrayList.size() - 1;
        while (k != 0) {
            int t = 0;
            for (int i = 0; i <= k - 1; i++) {
                favouritesSpotInfoForPresentIndex = favouritesSpotInfoArrayList
                        .get(i);
                favouritesSpotInfoForForwardIndex = favouritesSpotInfoArrayList
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
                    favouritesSpotInfoArrayList.set(i,
                            favouritesSpotInfoForForwardIndex);
                    favouritesSpotInfoArrayList.set(i + 1,
                            favouritesSpotInfoForPresentIndex);
                    t = i;
                }

            }
            k = t;

        }

    }


}

