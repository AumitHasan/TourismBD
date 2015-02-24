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
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.apppreview.shuvojit.tourismbd.R;
import com.apppreview.shuvojit.tourismbd.allpackges.activities.YoutubeDocumentryVideoActivity;
import com.apppreview.shuvojit.tourismbd.allpackges.databases.TourismGuiderDatabase;
import com.apppreview.shuvojit.tourismbd.allpackges.infos.DocumentryVideoInfo;
import com.apppreview.shuvojit.tourismbd.allpackges.interfaces.AdapterInterface;
import com.apppreview.shuvojit.tourismbd.allpackges.interfaces.Intializer;

import java.util.ArrayList;


public class DocumentryVideoListFragment extends Fragment implements Intializer {

    private Context context;
    private View fragmentView;
    private ListView docVideoListView;
    private TourismGuiderDatabase tourismGuiderDatabase;
    private DocumentryVideoInfo documentryVideoInfo;
    private Intent intent;
    private OnItemClickListener clickListener = new OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            documentryVideoInfo = documentryVideoInfoList.get(position);
            if (documentryVideoInfo != null) {
                intent = new Intent(getActivity(),
                        YoutubeDocumentryVideoActivity.class);
                intent.putExtra("Video_Info", documentryVideoInfo);
                startActivity(intent);
            } else {
                Log.e(getClass().getName(),
                        "Video name and video key not found");
            }

        }
    };
    private ArrayList<DocumentryVideoInfo> documentryVideoInfoList;

    public DocumentryVideoListFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.doc_video_fragment_layout,
                container, false);
        intialize();
        sortDocumentryVideoList();
        setAdapter();
        return fragmentView;
    }

    @Override
    public void intialize() {
        context = getActivity();
        tourismGuiderDatabase = TourismGuiderDatabase.getTourismGuiderDatabase(context);
        documentryVideoInfoList = tourismGuiderDatabase.getDocVideoInfo();

    }

    private void sortDocumentryVideoList() {
        DocumentryVideoInfo documentryVideoInfoForPresentIndex = null;
        DocumentryVideoInfo documentryVideoInfoForForwardIndex = null;
        String documentryVideoNamePresentIndex = null;
        String documentryVideoNameForwardIndex = null;
        int k = documentryVideoInfoList.size() - 1;
        while (k != 0) {
            int t = 0;
            for (int i = 0; i <= k - 1; i++) {
                documentryVideoInfoForPresentIndex = documentryVideoInfoList
                        .get(i);
                documentryVideoInfoForForwardIndex = documentryVideoInfoList
                        .get(i + 1);
                documentryVideoNamePresentIndex = documentryVideoInfoForPresentIndex
                        .getDocName();

                documentryVideoNameForwardIndex = documentryVideoInfoForForwardIndex
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
                    documentryVideoInfoList.set(i,
                            documentryVideoInfoForForwardIndex);
                    documentryVideoInfoList.set(i + 1,
                            documentryVideoInfoForPresentIndex);
                    t = i;
                }

            }
            k = t;

        }

    }

    private void setAdapter() {
        docVideoListView = (ListView) fragmentView
                .findViewById(R.id.list_view_doc_videos);
        DocVideoListAdpater adapter = new DocVideoListAdpater();
        docVideoListView.setAdapter(adapter);
        docVideoListView.setOnItemClickListener(clickListener);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        tourismGuiderDatabase.close();
        Log.e(getClass().getName(), "Database close");
    }

    private class DocVideoListAdpater extends BaseAdapter implements
            AdapterInterface {

        private TextView txtDocVideo;
        private ImageView videoIcon;

        public DocVideoListAdpater() {

        }

        @Override
        public int getCount() {

            return documentryVideoInfoList.size();
        }

        @Override
        public Object getItem(int position) {

            return documentryVideoInfoList.get(position);
        }

        @Override
        public long getItemId(int position) {

            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View adapterView;
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                adapterView = inflater.inflate(R.layout.doc_video_list_layout,
                        parent, false);
            } else {
                adapterView = convertView;
            }
            intializeAdapterView(adapterView);
            DocumentryVideoInfo documentryVideoInfo = documentryVideoInfoList
                    .get(position);
            String docVideoName = documentryVideoInfo.getDocName();
            txtDocVideo.setText(docVideoName);
            setIcon(docVideoName);
            return adapterView;
        }

        @Override
        public void setIcon(String docVideoName) {
            videoIcon.setImageResource(R.drawable.ic_youtube_video);
        }

        @Override
        public void intializeAdapterView(View adapterView) {
            txtDocVideo = (TextView) adapterView
                    .findViewById(R.id.txt_doc_video);
            videoIcon = (ImageView) adapterView.findViewById(R.id.img_video);

        }

    }

}
