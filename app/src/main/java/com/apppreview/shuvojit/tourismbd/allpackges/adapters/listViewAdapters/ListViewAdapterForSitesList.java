package com.apppreview.shuvojit.tourismbd.allpackges.adapters.listViewAdapters;



import android.content.Context;
import android.content.res.TypedArray;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.apppreview.shuvojit.tourismbd.R;
import com.apppreview.shuvojit.tourismbd.allpackges.infos.SpotInfo;
import com.apppreview.shuvojit.tourismbd.allpackges.interfaces.AdapterInterface;

import java.util.ArrayList;


public class ListViewAdapterForSitesList extends BaseAdapter implements
        AdapterInterface {

	private int allSpotsIcon[];
	private Context context;
	private TextView spotName;
	private ImageView spotIcon;
	private ArrayList<SpotInfo> spotInfoList;
	private String[] allSpotsName;

	public ListViewAdapterForSitesList(Context context,
			ArrayList<SpotInfo> spotInfoList) {
		this.context = context;
		this.spotInfoList = spotInfoList;
		intialize();
		Log.e(getClass().getName(), String.valueOf(spotInfoList.size()));

	}

	private void intialize() {
		TypedArray typedArray = context.getResources().obtainTypedArray(
				R.array.ic_of_all_spots);
		allSpotsIcon = new int[typedArray.length()];
		for (int i = 0; i < typedArray.length(); i++) {
			allSpotsIcon[i] = typedArray.getResourceId(i, 0);
		}
		typedArray.recycle();
		allSpotsName = context.getResources().getStringArray(
				R.array.all_spots_name);
	}

	@Override
	public int getCount() {
		return spotInfoList.size();
	}

	@Override
	public Object getItem(int position) {
		return spotInfoList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View adapterView;
		if (convertView == null) {
			LayoutInflater layoutInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			adapterView = layoutInflater.inflate(R.layout.spot_item_list_view_layout,
					parent, false);
		} else {
			adapterView = convertView;
		}
		intializeAdapterView(adapterView);
		SpotInfo spotInfo = spotInfoList.get(position);
		spotName.setText(spotInfo.getSpotName());
		setIcon(spotInfo.getSpotName());
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
		spotIcon.setImageResource((int) allSpotsIcon[position]);

	}

	@Override
	public void intializeAdapterView(View adapterView) {
		spotName = (TextView) adapterView.findViewById(R.id.txt_site_name);
		spotIcon = (ImageView) adapterView.findViewById(R.id.img_site_ic);

	}

}
