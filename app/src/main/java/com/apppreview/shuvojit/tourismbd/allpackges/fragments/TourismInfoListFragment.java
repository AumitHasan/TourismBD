package com.apppreview.shuvojit.tourismbd.allpackges.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apppreview.shuvojit.tourismbd.R;
import com.apppreview.shuvojit.tourismbd.allpackges.adapters.recyclerViewAdapter.TourismInfoListRecyclerViewAdapter;
import com.apppreview.shuvojit.tourismbd.allpackges.databaseTablesModel.DocVideoTable;
import com.apppreview.shuvojit.tourismbd.allpackges.databaseTablesModel.LatLongInfoOfAllSpotsTable;
import com.apppreview.shuvojit.tourismbd.allpackges.interfaces.InitializerClient;

import java.util.List;

import jp.wasabeef.recyclerview.animators.OvershootInLeftAnimator;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;
import jp.wasabeef.recyclerview.animators.adapters.AlphaInAnimationAdapter;


public class TourismInfoListFragment extends Fragment implements InitializerClient {

    private View fragmentView;
    private Context context;
    private String infoType;
    private RecyclerView recyclerView;
    private TourismInfoListRecyclerViewAdapter tourismInfoListRecyclerViewAdapter;
    private List<DocVideoTable> docVideoTableDataList;
    private List<LatLongInfoOfAllSpotsTable> spotInfoList;

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
            if (infoType.equalsIgnoreCase("Documentary Videos")) {
                docVideoTableDataList = DocVideoTable.getAllDocVideoTableModelDataList();
                tourismInfoListRecyclerViewAdapter = new
                        TourismInfoListRecyclerViewAdapter(context,
                        docVideoTableDataList, "Documentary Video Infos");
                recyclerView.setItemAnimator(new SlideInUpAnimator());

            } else {
                spotInfoList = LatLongInfoOfAllSpotsTable.
                        getAllLatLongInfoOfAllSpotsTableData(infoType);
                tourismInfoListRecyclerViewAdapter = new
                        TourismInfoListRecyclerViewAdapter(context,
                        spotInfoList, "Tourist Spot Infos");
                recyclerView.setItemAnimator(new OvershootInLeftAnimator());
            }
            recyclerView.setAdapter(new AlphaInAnimationAdapter(
                    tourismInfoListRecyclerViewAdapter));

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(linearLayoutManager);

        }
    }


    private void sortDocumentaryVideoList() {
        DocVideoTable documentaryVideoInfoForPresentIndex = null;
        DocVideoTable documentaryVideoInfoForForwardIndex = null;
        String documentaryVideoNamePresentIndex;
        String documentaryVideoNameForwardIndex;
        int k = docVideoTableDataList.size() - 1;
        while (k != 0) {
            int t = 0;
            for (int i = 0; i <= k - 1; i++) {
                documentaryVideoInfoForPresentIndex = docVideoTableDataList
                        .get(i);
                documentaryVideoInfoForForwardIndex = docVideoTableDataList
                        .get(i + 1);
                documentaryVideoNamePresentIndex = documentaryVideoInfoForPresentIndex
                        .getDocName();

                documentaryVideoNameForwardIndex = documentaryVideoInfoForForwardIndex
                        .getDocName();
                int j = 0;
                int l = 0;
                boolean flag = false;

                while (j < documentaryVideoNamePresentIndex.length()
                        && l < documentaryVideoNameForwardIndex.length()) {

                    if (documentaryVideoNamePresentIndex.charAt(j) >
                            documentaryVideoNameForwardIndex
                            .charAt(l)) {
                        flag = true;
                        break;
                    } else if (documentaryVideoNamePresentIndex.charAt(j) <
                            documentaryVideoNameForwardIndex.charAt(l)) {
                        flag = false;
                        break;
                    }
                    j++;
                    l++;
                }

                if (flag) {
                    docVideoTableDataList.set(i,
                            documentaryVideoInfoForForwardIndex);
                    docVideoTableDataList.set(i + 1,
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


    }

    private void sortSpotNameList() {

        LatLongInfoOfAllSpotsTable spotInfoForPresentIndex = null;
        LatLongInfoOfAllSpotsTable spotInfoForForwardIndex = null;
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
