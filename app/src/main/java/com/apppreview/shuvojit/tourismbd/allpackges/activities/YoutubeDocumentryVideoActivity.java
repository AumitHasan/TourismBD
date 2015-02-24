package com.apppreview.shuvojit.tourismbd.allpackges.activities;

import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.apppreview.shuvojit.tourismbd.R;
import com.apppreview.shuvojit.tourismbd.allpackges.infos.DocumentryVideoInfo;
import com.apppreview.shuvojit.tourismbd.allpackges.interfaces.Intializer;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;


public class YoutubeDocumentryVideoActivity extends ActionBarActivity implements
        YouTubePlayer.OnInitializedListener, Intializer {

    private YouTubePlayerFragment youTubePlayerFragment;
    private FragmentManager fragmentManager;
    private TextView txtVideoName;
    private Intent intent;
    private DocumentryVideoInfo documentryVideoInfo;
    private String API_KEY;
    private String videoID;
    private ActionBar actionBar;
    private YouTubePlayer.PlaybackEventListener playbackEventListener = new YouTubePlayer.PlaybackEventListener() {

        @Override
        public void onStopped() {
            Log.e(getClass().getName(), "stopped");

        }

        @Override
        public void onSeekTo(int arg0) {
            Log.e(getClass().getName(), "on seeking");
        }

        @Override
        public void onPlaying() {
            Log.e(getClass().getName(), "playing");

        }

        @Override
        public void onPaused() {
            Log.e(getClass().getName(), "paused");
        }

        @Override
        public void onBuffering(boolean arg0) {
            Log.e(getClass().getName(), "buffering");

        }
    };
    private YouTubePlayer.PlayerStateChangeListener playerStateChangeListener =
            new YouTubePlayer.PlayerStateChangeListener() {

                @Override
                public void onVideoStarted() {
                    Log.e(getClass().getName(), "started");

                }

                @Override
                public void onVideoEnded() {
                    Log.e(getClass().getName(), "ended");

                }

                @Override
                public void onLoading() {

                    Log.e(getClass().getName(), "loading");

                }

                @Override
                public void onLoaded(String arg0) {

                    Log.e(getClass().getName(), "on loaded");

                }

                @Override
                public void onError(YouTubePlayer.ErrorReason arg0) {

                    Log.e(getClass().getName(), "on error");

                }

                @Override
                public void onAdStarted() {

                    Log.e(getClass().getName(), "ad started");
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.documentry_video_activity_layout);
        intialize();
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources()
                .getColor(R.color.darkred)));
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void intialize() {
        actionBar = getSupportActionBar();
        API_KEY = getResources().getString(
                R.string.google_play_services_API_KEY);
        fragmentManager = getFragmentManager();
        txtVideoName = (TextView) findViewById(R.id.doc_video_name);
        youTubePlayerFragment = (YouTubePlayerFragment) fragmentManager
                .findFragmentById(R.id.frag_doc_video);
        intent = getIntent();
        documentryVideoInfo = (DocumentryVideoInfo) intent
                .getSerializableExtra("Video_Info");
        if (documentryVideoInfo != null) {
            videoID = documentryVideoInfo.getDocKey();
            setTxtVideoName(documentryVideoInfo.getDocName());
            initializeYouttubeFragment();
        } else {
            Toast.makeText(getApplicationContext(), "Null Found",
                    Toast.LENGTH_LONG).show();
        }

    }

    private void initializeYouttubeFragment() {
        youTubePlayerFragment.initialize(API_KEY, this);
    }

    private void setTxtVideoName(String videoName) {
        this.txtVideoName.setText(videoName);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.documentry_video_activity, menu);
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

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                        YouTubeInitializationResult res) {

    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                        YouTubePlayer player, boolean wasRestored) {

        if (!wasRestored) {
            player.cueVideo(videoID);
            player.setPlaybackEventListener(playbackEventListener);
            player.setPlayerStateChangeListener(playerStateChangeListener);
        }

    }

}
