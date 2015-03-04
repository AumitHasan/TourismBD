package com.apppreview.shuvojit.tourismbd.allpackges.adapters.recyclerViewAdapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.apppreview.shuvojit.tourismbd.R;
import com.apppreview.shuvojit.tourismbd.allpackges.activities.TouristSpotInfoActivity;
import com.apppreview.shuvojit.tourismbd.allpackges.activities.YoutubeDocumentryVideoActivity;
import com.apppreview.shuvojit.tourismbd.allpackges.databases.TourismGuiderDatabase;
import com.apppreview.shuvojit.tourismbd.allpackges.infos.DocumentaryVideoInfo;
import com.apppreview.shuvojit.tourismbd.allpackges.infos.FavouritesSpotInfo;
import com.apppreview.shuvojit.tourismbd.allpackges.infos.LatLongInfo;
import com.apppreview.shuvojit.tourismbd.allpackges.infos.SpotInfo;
import com.apppreview.shuvojit.tourismbd.allpackges.interfaces.InitializerClient;

import java.util.ArrayList;

/**
 * Created by shuvojit on 3/2/15.
 */
public class TourismInfoListRecyclerViewAdapter extends
        RecyclerView.Adapter<TourismInfoListRecyclerViewAdapter.TourismInfoViewHolder>
        implements InitializerClient {

    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<SpotInfo> touristSpotInfoArrayList;
    private String[] allSpotsName;
    private int[] allSpotsImages;
    private ArrayList<DocumentaryVideoInfo> documentaryVideoInfoArrayList;
    private ArrayList<FavouritesSpotInfo> favouritesSpotInfoArrayList;
    private String adapterType;


    public TourismInfoListRecyclerViewAdapter(Context context,
                                              ArrayList infoArrayList,
                                              String adapterType) {
        this.context = context;
        this.adapterType = adapterType;
        if (adapterType.equalsIgnoreCase("Tourist Spot Infos")) {
            touristSpotInfoArrayList = (ArrayList<SpotInfo>) infoArrayList;
            initialize();
        } else if (adapterType.equalsIgnoreCase("Favourites Spot Infos")) {
            favouritesSpotInfoArrayList = (ArrayList<FavouritesSpotInfo>) infoArrayList;
            initialize();
        } else {
            documentaryVideoInfoArrayList = (ArrayList<DocumentaryVideoInfo>)
                    infoArrayList;
        }
        layoutInflater = LayoutInflater.from(context);
    }


    @Override
    public TourismInfoViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        TourismInfoViewHolder tourismInfoViewHolder = null;
        if (adapterType.equalsIgnoreCase("Tourist Spot Infos")) {
            View touristSpotView = layoutInflater.inflate
                    (R.layout.tourist_spot_card_view_layout, viewGroup, false);
            tourismInfoViewHolder = new TourismInfoViewHolder(touristSpotView);
            Log.e(getClass().getName(), "Adapter has been attached");
        } else if (adapterType.equalsIgnoreCase("Documentary Video Infos")) {
            View documentaryInfoView = layoutInflater.inflate(R.layout.doc_video_list_layout,
                    viewGroup, false);
            tourismInfoViewHolder = new TourismInfoViewHolder(documentaryInfoView);
            Log.e(getClass().getName(), "Adapter has been attached");
        } else {
            View favouritesSpotView = layoutInflater.inflate
                    (R.layout.favourites_spot_list_view_layout, viewGroup, false);
            tourismInfoViewHolder = new TourismInfoViewHolder(favouritesSpotView);
            Log.e(getClass().getName(), "Adapter has been attached");
        }
        return tourismInfoViewHolder;
    }

    @Override
    public void onBindViewHolder(TourismInfoViewHolder tourismInfoViewHolder, int position) {
        if (adapterType.equalsIgnoreCase("Tourist Spot Infos")) {
            setTouristSpotInfoView(tourismInfoViewHolder, position);
        } else if (adapterType.equalsIgnoreCase("Documentary Video Infos")) {
            setDocumentaryInfoView(tourismInfoViewHolder, position);
        } else {
            setFavouritesTouristSpotInfoView(tourismInfoViewHolder, position);
        }


    }

    private void setFavouritesTouristSpotInfoView(TourismInfoViewHolder tourismInfoViewHolder,
                                                  int position) {
        final FavouritesSpotInfo favouritesSpotInfo = favouritesSpotInfoArrayList.get(position);
        tourismInfoViewHolder.txtTouristSpotName.setText(favouritesSpotInfo.getSpotName());
        tourismInfoViewHolder.touristSpotImageView.setImageResource(
                getImageResourceID(favouritesSpotInfo.getSpotName()));
        tourismInfoViewHolder.btnSeeTouristSpot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSpotActivity(favouritesSpotInfo.getSpotName(),
                        favouritesSpotInfo.getSpotTypeInfo());
            }
        });
    }

    private void setDocumentaryInfoView(TourismInfoViewHolder tourismInfoViewHolder, int position) {
        final DocumentaryVideoInfo documentaryVideoInfo =
                documentaryVideoInfoArrayList.get(position);
        tourismInfoViewHolder.txtDocumentaryVideoName.setText(documentaryVideoInfo.getDocName());
        tourismInfoViewHolder.documentaryVideoIcon.setImageResource(R.drawable.ic_youtube_video);
        tourismInfoViewHolder.btnSeeVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,
                        YoutubeDocumentryVideoActivity.class);
                intent.putExtra("Video_Info", documentaryVideoInfo);
                context.startActivity(intent);
            }
        });

    }

    private void setTouristSpotInfoView(TourismInfoViewHolder tourismInfoViewHolder, int position) {
        final SpotInfo spotInfo = touristSpotInfoArrayList.get(position);
        tourismInfoViewHolder.txtTouristSpotName.setText(spotInfo.getSpotName());
        tourismInfoViewHolder.txtTouristSnippet.setText(spotInfo.getSpotSnippet());
        tourismInfoViewHolder.touristSpotImageView.setImageResource
                (getImageResourceID(spotInfo.getSpotName()));
        tourismInfoViewHolder.btnLearnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSpotActivity(spotInfo.getSpotName(), spotInfo.getSpotTypeInfo());
            }
        });
    }

    private void setSpotActivity(String spotName, String spotType) {
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

    @Override
    public int getItemCount() {
        if (adapterType.equalsIgnoreCase("Tourist Spot Infos")) {
            return touristSpotInfoArrayList.size();
        } else if (adapterType.equalsIgnoreCase("Documentary Video Infos")) {
            return documentaryVideoInfoArrayList.size();
        } else {
            return favouritesSpotInfoArrayList.size();
        }

    }

    @Override
    public void initialize() {
        if (context != null) {
            allSpotsName = context.getResources().getStringArray(R.array.all_spots_name);
            TypedArray typedArray = null;
            if (adapterType.equalsIgnoreCase("Tourist Spot Infos")) {
                typedArray = context.getResources().
                        obtainTypedArray(R.array.images_of_all_spots);
            } else {
                typedArray = context.getResources().
                        obtainTypedArray(R.array.ic_of_all_spots);
            }
            allSpotsImages = new int[typedArray.length()];
            for (int i = 0; i < allSpotsImages.length; i++) {
                allSpotsImages[i] = typedArray.getResourceId(i, 0);
            }
            typedArray.recycle();
        }
    }


    public class TourismInfoViewHolder extends RecyclerView.ViewHolder implements
            InitializerClient {

        ImageView touristSpotImageView;
        TextView txtTouristSpotName;
        TextView txtTouristSnippet;
        Button btnLearnMore;
        View tourismInfoView;
        TextView txtDocumentaryVideoName;
        ImageView documentaryVideoIcon;
        Button btnSeeVideo;
        Button btnSeeTouristSpot;
        Button btnDiscardTouristSpot;

        public TourismInfoViewHolder(View touristSpotView) {
            super(touristSpotView);
            this.tourismInfoView = touristSpotView;
            initialize();
        }

        @Override
        public void initialize() {
            if (tourismInfoView != null) {
                if (adapterType.equalsIgnoreCase("Tourist Spot Infos")) {
                    touristSpotImageView = (ImageView) tourismInfoView.
                            findViewById(R.id.touristSpotImage);
                    txtTouristSpotName = (TextView) tourismInfoView.
                            findViewById(R.id.txtSpotName);
                    txtTouristSnippet = (TextView) tourismInfoView.
                            findViewById(R.id.txtTouristSpotSnippet);
                    btnLearnMore = (Button) tourismInfoView.findViewById(R.id.btnLearnMore);
                } else if (adapterType.equalsIgnoreCase("Documentary Video Infos")) {
                    txtDocumentaryVideoName = (TextView) tourismInfoView.
                            findViewById(R.id.txt_doc_video_name);
                    documentaryVideoIcon = (ImageView) tourismInfoView.
                            findViewById(R.id.img_doc_video);
                    btnSeeVideo = (Button) tourismInfoView.findViewById(R.id.btnSeeVideo);
                } else {
                    txtTouristSpotName = (TextView) tourismInfoView.
                            findViewById(R.id.txt_tourist_spot_name);
                    touristSpotImageView = (ImageView) tourismInfoView.
                            findViewById(R.id.ic_tourist_spot);
                    btnSeeTouristSpot = (Button) tourismInfoView.
                            findViewById(R.id.btn_see_tourist_spot);
                    btnDiscardTouristSpot = (Button) tourismInfoView.
                            findViewById(R.id.btn_discard_tourist_spot);
                }
            }
        }
    }
}
