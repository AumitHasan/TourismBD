package com.apppreview.shuvojit.tourismbd.allpackges.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.apppreview.shuvojit.tourismbd.R;
import com.apppreview.shuvojit.tourismbd.allpackges.interfaces.InitializerClient;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class WebViewActivity extends ActionBarActivity implements InitializerClient {

    private WebView webView;
    private Intent intent;
    private String url;
    private String actionBarName;
    private SweetAlertDialog progressDialog;
    private ActionBar actionBar;
    private WebViewClient webViewClient = new WebViewClient() {
        public void onPageFinished(WebView view, String url) {
            if (progressDialog.isShowing()) {
                progressDialog.dismissWithAnimation();
            }
        }

        ;
    };

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_view_activity_layout);
        initialize();
        webView.getSettings().setJavaScriptEnabled(true);
        if (url != null && actionBarName != null) {
            setTitle(actionBarName);
            webView.loadUrl(url);
            showProgressDialog();
        }
        webView.setWebViewClient(webViewClient);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void initialize() {
        url = null;
        actionBarName = null;
        webView = (WebView) findViewById(R.id.webView);
        intent = getIntent();
        url = intent.getStringExtra("URL");
        actionBar = getSupportActionBar();
        actionBarName = intent.getStringExtra("ACTION_BAR_NAME");
        progressDialog = new SweetAlertDialog(WebViewActivity.this,
                SweetAlertDialog.PROGRESS_TYPE);

    }

    private void showProgressDialog() {
        progressDialog.setTitleText("Loading...");
        progressDialog.setCancelable(true);
        progressDialog.show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.web_view_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}


