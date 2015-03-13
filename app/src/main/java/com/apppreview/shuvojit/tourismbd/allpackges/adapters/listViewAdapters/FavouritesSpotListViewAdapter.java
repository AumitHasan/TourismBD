package com.apppreview.shuvojit.tourismbd.allpackges.adapters.listViewAdapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.apppreview.shuvojit.tourismbd.R;
import com.apppreview.shuvojit.tourismbd.allpackges.activities.TouristSpotInfoActivity;
import com.apppreview.shuvojit.tourismbd.allpackges.databaseTablesModel.FavouritesListsTable;
import com.apppreview.shuvojit.tourismbd.allpackges.databaseTablesModel.LatLongInfoOfAllSpotsTable;
import com.apppreview.shuvojit.tourismbd.allpackges.databaseTablesModel.SpotInfoTable;
import com.apppreview.shuvojit.tourismbd.allpackges.interfaces.FontClient;
import com.apppreview.shuvojit.tourismbd.allpackges.interfaces.InitializerClient;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by shuvojit on 3/5/15.
 */
public class FavouritesSpotListViewAdapter extends BaseAdapter implements InitializerClient,
        FontClient {

    private static List<FavouritesListsTable> favouritesSpotInfoArrayList;
    private Button btnSeeTouristSpot;
    private Button btnDiscardTouristSpot;
    private TextView txtSpotName;
    private ImageView imgSpotIcon;
    private Context context;
    private View adapterView;
    private String[] allSpotsName;
    private int[] allSpotsImages;
    private Typeface typeface;


    public FavouritesSpotListViewAdapter(Context context, List<FavouritesListsTable>
            favouritesSpotInfoArrayList) {
        this.context = context;
        this.favouritesSpotInfoArrayList = favouritesSpotInfoArrayList;
        this.typeface = Typeface.createFromAsset(context.getAssets(), UBUNTU_FONT_PATH);
        allSpotsName = context.getResources().getStringArray(R.array.all_spots_name);
        TypedArray typedArray = context.getResources().
                obtainTypedArray(R.array.ic_of_all_spots);
        allSpotsImages = new int[typedArray.length()];
        for (int i = 0; i < allSpotsImages.length; i++) {
            allSpotsImages[i] = typedArray.getResourceId(i, 0);
        }
        typedArray.recycle();

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
        FavouritesListsTable favouritesSpotInfo = favouritesSpotInfoArrayList.get(position);
        txtSpotName.setText(favouritesSpotInfo.getSpotName());
        txtSpotName.setTypeface(typeface);
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
            btnDiscardTouristSpot.setTypeface(typeface);
            btnSeeTouristSpot = (Button) adapterView.
                    findViewById(R.id.btn_see_tourist_spot);
            btnSeeTouristSpot.setTypeface(typeface);

        }

    }

    public void notifyListView() {
        notifyDataSetChanged();
    }

    public void setButtonClickListener(final int position) {

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int viewID = v.getId();
                switch (viewID) {
                    case R.id.btn_discard_tourist_spot:
                        showDeleteTouristSpotProgressDialog(position);
                        Log.e(getClass().getName(), " button discard is clicked");
                        break;
                    case R.id.btn_see_tourist_spot:
                        FavouritesListsTable favouritesSpotInfo = favouritesSpotInfoArrayList.
                                get(position);
                        setTourismSpotInfoActivity(favouritesSpotInfo.getSpotName(),
                                favouritesSpotInfo.getSpotType());
                        Log.e(getClass().getName(), " button see is clicked");
                        break;
                }
            }
        };

        btnDiscardTouristSpot.setOnClickListener(onClickListener);
        btnSeeTouristSpot.setOnClickListener(onClickListener);
    }

    private void showDeleteTouristSpotProgressDialog(final int position) {
        FavouritesListsTable favouritesSpotInfo = favouritesSpotInfoArrayList.get(position);
        final String spotName = favouritesSpotInfo.getSpotName();
        final String spotType = favouritesSpotInfo.getSpotType();
        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(context,
                SweetAlertDialog.WARNING_TYPE);
        sweetAlertDialog.setTitleText("Delete")
                .setContentText(spotName + " From favourites?")
                .setConfirmText("Yes")
                .setCancelText("No")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.showCancelButton(false);
                        deleteTouristSpotFromDatabase(spotName, spotType, position);
                        sweetAlertDialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                        sweetAlertDialog.setTitleText("Deleted!")
                                .setConfirmText("Ok")
                                .setConfirmClickListener(null);

                    }
                })
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismissWithAnimation();
                    }
                })
                .show();
    }

    private void deleteTouristSpotFromDatabase(String spotName, String spotType, int position) {
        boolean res = FavouritesListsTable.removeSpotFrom(spotName, spotType);
        if (res) {
            favouritesSpotInfoArrayList.remove(position);
            notifyDataSetChanged();
            Log.e(getClass().getName(), "Favourites list item deleted");
            if (favouritesSpotInfoArrayList.size() == 0) {
                Toast.makeText(context, "No tourist spot has been added to favourites list",
                        Toast.LENGTH_LONG).show();
            }

        }
    }


    private void setTourismSpotInfoActivity(String spotName, String spotType) {
        SpotInfoTable spotInfo = SpotInfoTable.getSpotInfoTableData(spotName, spotType);
        LatLongInfoOfAllSpotsTable latLongInfo = LatLongInfoOfAllSpotsTable.
                getLatLongInfoOfSpotTableData(spotName, spotType);
        if (spotInfo != null && latLongInfo != null) {
            Log.d(getClass().getName(), "found");
            Intent intent = new Intent(context, TouristSpotInfoActivity.class);
            intent.putExtra("SpotInfo", spotInfo);
            intent.putExtra("SpotLatLongInfo", latLongInfo);
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


}











