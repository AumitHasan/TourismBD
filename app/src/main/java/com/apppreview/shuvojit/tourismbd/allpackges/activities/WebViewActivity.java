package com.apppreview.shuvojit.tourismbd.allpackges.activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.apppreview.shuvojit.tourismbd.R;
import com.apppreview.shuvojit.tourismbd.allpackges.interfaces.InitializerClient;


public class WebViewActivity extends ActionBarActivity implements InitializerClient {

    private WebView webView;
    private Intent intent;
    private String url;
    private String actionBarName;
    private ProgressDialog progressDialog;
    private ActionBar actionBar;
    private Toolbar toolbar;

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
        toolbar = new Toolbar(WebViewActivity.this);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBarName = intent.getStringExtra("ACTION_BAR_NAME");
        progressDialog = new ProgressDialog(WebViewActivity.this);

    }

    private void showProgressDialog() {
        progressDialog.setMessage("Loading ...");
        progressDialog.setCancelable(false);
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

    private WebViewClient webViewClient = new WebViewClient() {
        public void onPageFinished(WebView view, String url) {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        };
    };

}


