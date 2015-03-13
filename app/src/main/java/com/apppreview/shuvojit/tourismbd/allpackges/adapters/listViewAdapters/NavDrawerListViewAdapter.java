package com.apppreview.shuvojit.tourismbd.allpackges.adapters.listViewAdapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.apppreview.shuvojit.tourismbd.R;
import com.apppreview.shuvojit.tourismbd.allpackges.interfaces.FontClient;


public class NavDrawerListViewAdapter extends BaseAdapter implements FontClient {

	private Context context;
	private String listItemsName[];
	private TextView textView;
    private Typeface typeface;

	public NavDrawerListViewAdapter(Context context) {
		this.context = context;
        this.typeface = Typeface.createFromAsset(context.getAssets(), UBUNTU_FONT_PATH );
		intialize();

	}

	private void intialize() {
		listItemsName = context.getResources().getStringArray(
				R.array.nav_drawer_categories);

	}

	@Override
	public int getCount() {

		return this.listItemsName.length;
	}

	@Override
	public Object getItem(int position) {

		return listItemsName[position];
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View adapterView = null;
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) this.context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			adapterView = inflater.inflate(R.layout.nav_list_item_layout, parent,
					false);
		} else {

			adapterView = convertView;
		}
		this.textView = (TextView) adapterView.findViewById(R.id.item_name);
		textView.setText(listItemsName[position]);
        textView.setTypeface(typeface);
		return adapterView;
	}

}
