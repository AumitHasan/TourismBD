package com.apppreview.shuvojit.tourismbd.allpackges.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apppreview.shuvojit.tourismbd.R;


public class AboutOurAppFragment extends Fragment {

    private Context context;
    private View fragmentView;

    public AboutOurAppFragment() {

    }

    public static AboutOurAppFragment getInstance() {
        return new AboutOurAppFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.about_our_app_fragment_layout,
                container, false);
        return fragmentView;
    }


}
