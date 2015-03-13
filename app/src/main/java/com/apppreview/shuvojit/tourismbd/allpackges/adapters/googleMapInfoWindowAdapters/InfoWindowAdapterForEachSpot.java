package com.apppreview.shuvojit.tourismbd.allpackges.adapters.googleMapInfoWindowAdapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.apppreview.shuvojit.tourismbd.R;
import com.apppreview.shuvojit.tourismbd.allpackges.interfaces.FontClient;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.model.Marker;

public class InfoWindowAdapterForEachSpot implements InfoWindowAdapter, FontClient
        {

	private Context context;
	private View adapterView;
	private LayoutInflater layoutInflater;
	private TypedArray typedArray;
	private int[] allSpotIcons;
	private TextView spotTitle;
	private ImageView spotIcon;
	private String[] allSpotsName;
    private Typeface typeface;

	public InfoWindowAdapterForEachSpot(Context context) {
		this.context = context;
        this.typeface = Typeface.createFromAsset(context.getAssets(), UBUNTU_FONT_PATH);
		initialize();
	}

	private void initialize() {
		allSpotsName = context.getResources().getStringArray(
				R.array.all_spots_name);
		typedArray = context.getResources().obtainTypedArray(
				R.array.ic_of_all_spots);
		allSpotIcons = new int[typedArray.length()];
		for (int i = 0; i < typedArray.length(); i++) {
			allSpotIcons[i] = typedArray.getResourceId(i, 0);
		}
		typedArray.recycle();
	}

	@SuppressLint("InflateParams")
	@Override
	public View getInfoContents(Marker marker) {
		layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		adapterView = layoutInflater.inflate(
				R.layout.google_map_info_window_adapter_layout, null);
		intializeAdapterView(adapterView);
		String spotName = marker.getTitle();
		spotTitle.setText(spotName);
        spotTitle.setTypeface(typeface);
		if (spotName.equals("You are here")) {
			spotIcon.setImageResource(R.drawable.image_user);
		} else {
			setIcon(spotName);
		}
		return adapterView;
	}

	@Override
	public View getInfoWindow(Marker marker) {

		return null;
	}


	public void setIcon(String spotName) {
		int position = 0;
		for (int i = 0; i < allSpotsName.length; i++) {
			if (allSpotsName[i].equalsIgnoreCase(spotName)) {
				position = i;
				break;
			}
		}
		spotIcon.setImageResource(allSpotIcons[position]);

	}


	public void intializeAdapterView(View adapterView) {
		spotTitle = (TextView) adapterView
				.findViewById(R.id.info_window_adapter_spot_title_name);
		spotIcon = (ImageView) adapterView
				.findViewById(R.id.info_window_adapter_spot_image);

	}

}
