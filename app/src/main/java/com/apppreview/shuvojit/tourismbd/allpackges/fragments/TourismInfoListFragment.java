package com.apppreview.shuvojit.tourismbd.allpackges.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apppreview.shuvojit.tourismbd.R;
import com.apppreview.shuvojit.tourismbd.allpackges.adapters.recyclerViewAdapter.TourismInfoListRecyclerViewAdapter;
import com.apppreview.shuvojit.tourismbd.allpackges.databases.TourismGuiderDatabase;
import com.apppreview.shuvojit.tourismbd.allpackges.infos.DocumentaryVideoInfo;
import com.apppreview.shuvojit.tourismbd.allpackges.infos.SpotInfo;
import com.apppreview.shuvojit.tourismbd.allpackges.interfaces.InitializerClient;

import java.util.ArrayList;


public class TourismInfoListFragment extends Fragment implements InitializerClient {

    private TourismGuiderDatabase tourismGuiderDatabase;
    private View fragmentView;
    private Context context;
    private String infoType;
    private ArrayList<SpotInfo> spotInfoList;
    private RecyclerView recyclerView;
    private TourismInfoListRecyclerViewAdapter tourismInfoListRecyclerViewAdapter;
    private ArrayList<DocumentaryVideoInfo> documentaryVideoInfoArrayList;

    public TourismInfoListFragment() {


    }

    public static TourismInfoListFragment getNewInstance(String infoType) {
        TourismInfoListFragment tourismInfoListFragment = new TourismInfoListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("Info Type", infoType);
        tourismInfoListFragment.setArguments(bundle);
        return tourismInfoListFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            infoType = bundle.getString("Info Type");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.tourism_info_list_fragment_layout,
                container, false);
        initialize();
        return fragmentView;
    }

    @Override
    public void initialize() {
        if (fragmentView != null) {
            context = getActivity();
            recyclerView = (RecyclerView) fragmentView.findViewById(R.id.recyclerView);
            tourismGuiderDatabase = TourismGuiderDatabase.getTourismGuiderDatabase(context);
            if (infoType.equalsIgnoreCase("Documentry Videos")) {
                documentaryVideoInfoArrayList = tourismGuiderDatabase.getDocVideoInfo();
                sortDocumentaryVideoList();
                tourismInfoListRecyclerViewAdapter = new TourismInfoListRecyclerViewAdapter(context,
                        documentaryVideoInfoArrayList, "Documentary Video Infos");
            } else {
                spotInfoList = tourismGuiderDatabase.getSpotNameList(infoType);
                sortSpotNameList();
                tourismInfoListRecyclerViewAdapter = new TourismInfoListRecyclerViewAdapter(context,
                        spotInfoList, "Tourist Spot Infos");

            }
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(tourismInfoListRecyclerViewAdapter);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(linearLayoutManager);

        }
    }

        private void sortDocumentaryVideoList() {
            DocumentaryVideoInfo documentaryVideoInfoForPresentIndex = null;
            DocumentaryVideoInfo documentaryVideoInfoForForwardIndex = null;
            String documentryVideoNamePresentIndex = null;
            String documentryVideoNameForwardIndex = null;
            int k = documentaryVideoInfoArrayList.size() - 1;
            while (k != 0) {
                int t = 0;
                for (int i = 0; i <= k - 1; i++) {
                    documentaryVideoInfoForPresentIndex = documentaryVideoInfoArrayList
                            .get(i);
                    documentaryVideoInfoForForwardIndex = documentaryVideoInfoArrayList
                            .get(i + 1);
                    documentryVideoNamePresentIndex = documentaryVideoInfoForPresentIndex
                            .getDocName();

                    documentryVideoNameForwardIndex = documentaryVideoInfoForForwardIndex
                            .getDocName();
                    int j = 0;
                    int l = 0;
                    boolean flag = false;

                    while (j < documentryVideoNamePresentIndex.length()
                            && l < documentryVideoNameForwardIndex.length()) {

                        if (documentryVideoNamePresentIndex.charAt(j) > documentryVideoNameForwardIndex
                                .charAt(l)) {
                            flag = true;
                            break;
                        } else if (documentryVideoNamePresentIndex.charAt(j) < documentryVideoNameForwardIndex
                                .charAt(l)) {
                            flag = false;
                            break;
                        }
                        j++;
                        l++;
                    }

                    if (flag) {
                        documentaryVideoInfoArrayList.set(i,
                                documentaryVideoInfoForForwardIndex);
                        documentaryVideoInfoArrayList.set(i + 1,
                                documentaryVideoInfoForPresentIndex);
                        t = i;
                    }

                }
                k = t;

            }

        }





    @Override
    public void onDestroy() {
        super.onDestroy();
        if (tourismGuiderDatabase != null) {
            tourismGuiderDatabase.close();
            Log.e(getClass().getName(), "Database close");
        }

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
