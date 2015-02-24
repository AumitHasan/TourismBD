package com.apppreview.shuvojit.tourismbd.allpackges.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apppreview.shuvojit.tourismbd.R;
import com.apppreview.shuvojit.tourismbd.allpackges.interfaces.Intializer;


public class AboutOurAppFragment extends Fragment implements Intializer {

    private Context context;
    private View fragmentView;

    public AboutOurAppFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.about_our_app_fragment_layout,
                container, false);
        return fragmentView;
    }

    @Override
    public void intialize() {

    }
}
