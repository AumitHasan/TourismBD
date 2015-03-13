package com.apppreview.shuvojit.tourismbd.allpackges.fragments;


import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.apppreview.shuvojit.tourismbd.R;
import com.apppreview.shuvojit.tourismbd.allpackges.activities.WebViewActivity;
import com.apppreview.shuvojit.tourismbd.allpackges.interfaces.FontClient;
import com.apppreview.shuvojit.tourismbd.allpackges.interfaces.InitializerClient;

public class HomeFragment extends Fragment implements OnClickListener,
        InitializerClient, FontClient {

    private Context context;
    private Button btnGoTourismBangladesh, btnGoVisitBan, btnGoPorjatonCorp;
    private Intent intent;
    private View fragmentView;
    private Typeface typeface;

    public HomeFragment() {

    }

    public static HomeFragment getNewInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getActivity();
        typeface = Typeface.createFromAsset(context.getAssets(), UBUNTU_FONT_PATH);
        fragmentView = inflater.inflate(R.layout.home_fragment_layout,
                container, false);
        initialize();
        btnGoTourismBangladesh.setOnClickListener(this);
        btnGoVisitBan.setOnClickListener(this);
        btnGoPorjatonCorp.setOnClickListener(this);
        return fragmentView;
    }

    @Override
    public void initialize() {
        if (fragmentView != null) {
            TextView txtTourismGuiderOFBangladesh = (TextView) fragmentView.
                    findViewById(R.id.txt_tourism_guider_of_bangladesh);
            txtTourismGuiderOFBangladesh.setTypeface(typeface);
            TextView txtIntro = (TextView) fragmentView.findViewById(R.id.txt_intro);
            txtIntro.setTypeface(typeface);
            TextView txtDiscrip = (TextView) fragmentView.findViewById(R.id.txt_discrip);
            txtDiscrip.setTypeface(typeface);
            TextView txtDiscrip2 = (TextView) fragmentView.findViewById(R.id.txt_discrip1);
            txtDiscrip2.setTypeface(typeface);
            TextView txtWebInfo = (TextView) fragmentView.findViewById(R.id.txt_web_info);
            txtWebInfo.setTypeface(typeface);
            btnGoTourismBangladesh = (Button) fragmentView
                    .findViewById(R.id.btn_tourism_board);
            btnGoTourismBangladesh.setTypeface(typeface);
            btnGoVisitBan = (Button) fragmentView
                    .findViewById(R.id.btn_visit_ban);
            btnGoVisitBan.setTypeface(typeface);
            btnGoPorjatonCorp = (Button) fragmentView
                    .findViewById(R.id.btn_parjaton_corp);
            btnGoPorjatonCorp.setTypeface(typeface);
        }

    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        intent = new Intent(getActivity(), WebViewActivity.class);
        intent.putExtra("ACTION_BAR_NAME", getActivity().getTitle());
        switch (id) {
            case R.id.btn_tourism_board:
                intent.putExtra("URL", "http://tourismboard.gov.bd/");
                break;
            case R.id.btn_visit_ban:
                intent.putExtra("URL", "http://visitbangladesh.gov.bd/");
                break;
            case R.id.btn_parjaton_corp:
                intent.putExtra("URL", "http://parjatan.portal.gov.bd/");
                break;
            default:
                break;
        }
        startActivity(intent);
    }

}
