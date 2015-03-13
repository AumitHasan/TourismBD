package com.apppreview.shuvojit.tourismbd.allpackges.fragments;


import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.apppreview.shuvojit.tourismbd.R;
import com.apppreview.shuvojit.tourismbd.allpackges.interfaces.FontClient;
import com.apppreview.shuvojit.tourismbd.allpackges.interfaces.InitializerClient;


public class AboutOurAppFragment extends Fragment implements InitializerClient, FontClient {

    private Context context;
    private View fragmentView;
    private Typeface typeface;

    public AboutOurAppFragment() {

    }

    public static AboutOurAppFragment getInstance() {
        return new AboutOurAppFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getActivity();
        typeface = Typeface.createFromAsset(context.getAssets(), UBUNTU_FONT_PATH);
        fragmentView = inflater.inflate(R.layout.about_our_app_fragment_layout,
                container, false);
        initialize();
        return fragmentView;
    }


    @Override
    public void initialize() {
        if (fragmentView != null) {
            TextView labelAboutOurApp = (TextView)
                    fragmentView.findViewById(R.id.label_about_our_app);
            labelAboutOurApp.setTypeface(typeface);
            TextView txtTourismInfo = (TextView) fragmentView.
                    findViewById(R.id.txt_tourism_discrip_in_BD);
            txtTourismInfo.setTypeface(typeface);
            TextView txtDiscrip_1 = (TextView) fragmentView.findViewById(R.id.txt_app_def_1);
            txtDiscrip_1.setTypeface(typeface);
            TextView txtDiscrip_2 = (TextView) fragmentView.findViewById(R.id.txt_app_def_2);
            txtDiscrip_2.setTypeface(typeface);
            TextView txtDiscrip_3 = (TextView) fragmentView.findViewById(R.id.txt_app_def_3);
            txtDiscrip_3.setTypeface(typeface);
            TextView txtDiscrip_4 = (TextView) fragmentView.findViewById(R.id.txt_app_def_4);
            txtDiscrip_4.setTypeface(typeface);
            TextView txtDiscrip_5 = (TextView) fragmentView.findViewById(R.id.txt_app_def_5);
            txtDiscrip_5.setTypeface(typeface);
        }
    }
}
