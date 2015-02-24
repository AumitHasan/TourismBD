package com.apppreview.shuvojit.tourismbd.allpackges.fragments;


import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.apppreview.shuvojit.tourismbd.R;
import com.apppreview.shuvojit.tourismbd.allpackges.activities.WebViewActivity;
import com.apppreview.shuvojit.tourismbd.allpackges.interfaces.Intializer;

public class HomeFragment extends Fragment implements OnClickListener,
        Intializer {

    private Context context;
    private Button btnGoTourismBangladesh, btnGoVisitBan, btnGoPorjatonCorp;
    private Intent intent;
    private View fragmentView;

    public HomeFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.home_fragment_layout,
                container, false);
        intialize();
        btnGoTourismBangladesh.setOnClickListener(this);
        btnGoVisitBan.setOnClickListener(this);
        btnGoPorjatonCorp.setOnClickListener(this);
        return fragmentView;
    }

    @Override
    public void intialize() {
        if (fragmentView != null) {
            context = getActivity();
            btnGoTourismBangladesh = (Button) fragmentView
                    .findViewById(R.id.btn_tourism_board);
            btnGoVisitBan = (Button) fragmentView
                    .findViewById(R.id.btn_visit_ban);
            btnGoPorjatonCorp = (Button) fragmentView
                    .findViewById(R.id.btn_parjaton_corp);
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
