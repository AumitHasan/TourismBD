package com.apppreview.shuvojit.tourismbd.allpackges.activities;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewConfiguration;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.apppreview.shuvojit.tourismbd.R;
import com.apppreview.shuvojit.tourismbd.allpackges.databases.TourismGuiderDatabase;
import com.apppreview.shuvojit.tourismbd.allpackges.infos.LatLongInfo;
import com.apppreview.shuvojit.tourismbd.allpackges.infos.SpotImagesResourceInfo;
import com.apppreview.shuvojit.tourismbd.allpackges.infos.SpotInfo;
import com.apppreview.shuvojit.tourismbd.allpackges.interfaces.Intializer;

import java.lang.reflect.Field;


public class TouristSpotInfoActivity extends ActionBarActivity implements OnClickListener,
        Intializer {

    private TextView txtSpotLocation, txtSpotDiscrip, txtSpotFamousThings,
            txtSpotHotels;
    private Button btnMap, btnDirection;
    private Intent intent;
    private SpotInfo spotInfo;
    private LatLongInfo latLongInfo;
    private String spotType;
    private String spotName;
    private TourismGuiderDatabase tourismGuiderDatabase;
    private ImageFlipperGestoreDetector imageFlipperGestoreDetector;
    private GestureDetector gestureDetector;
    private ViewFlipper imageFlipper;
    private SpotImagesResourceInfo spotImagesResourceInfo;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spot_activity_layout);
        intialize();
        setAllInfoForSpot();
        setImageSlider();
        getOverFlowMenu();
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources()
                .getColor(R.color.darkred)));
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        Toast.makeText(getApplicationContext(),
                "Swipe image to see more images", Toast.LENGTH_LONG).show();

    }

    @Override
    public void intialize() {
        actionBar = getSupportActionBar();
        txtSpotLocation = (TextView) findViewById(R.id.txt_spot_location);
        txtSpotDiscrip = (TextView) findViewById(R.id.txt_spot_discrip);
        txtSpotFamousThings = (TextView) findViewById(R.id.txt_spot_famous_things);
        txtSpotHotels = (TextView) findViewById(R.id.txt_spot_hotels);
        btnMap = (Button) findViewById(R.id.btn_map);
        btnDirection = (Button) findViewById(R.id.btn_direction);
        btnMap.setOnClickListener(this);
        btnDirection.setOnClickListener(this);
        intent = getIntent();
        spotInfo = (SpotInfo) intent.getSerializableExtra("SpotInfo");
        latLongInfo = (LatLongInfo) intent
                .getSerializableExtra("SpotLatLongInfo");
        spotName = latLongInfo.getSpotName();
        spotType = latLongInfo.getSpotType();
        tourismGuiderDatabase = TourismGuiderDatabase.getTourismGuiderDatabase(TouristSpotInfoActivity.this);
        imageFlipper = (ViewFlipper) findViewById(R.id.viewImageFlipper);
        spotImagesResourceInfo = new SpotImagesResourceInfo(TouristSpotInfoActivity.this);

    }

    private void setImageSlider() {
        int imageResources[] = spotImagesResourceInfo
                .getSpotImageResources(spotName);
        for (int i = 0; i < imageResources.length; i++) {
            ImageView spotImageView = new ImageView(getApplicationContext());
            spotImageView.setLayoutParams(new LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            spotImageView.setScaleType(ImageView.ScaleType.FIT_XY);
            spotImageView.setImageResource(imageResources[i]);
            imageFlipper.addView(spotImageView);
        }

        imageFlipper.setInAnimation(this, android.R.anim.fade_in);
        imageFlipper.setOutAnimation(this, android.R.anim.fade_out);
        imageFlipperGestoreDetector = new ImageFlipperGestoreDetector();
        gestureDetector = new GestureDetector(this, imageFlipperGestoreDetector);
        imageFlipper.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                gestureDetector.onTouchEvent(event);
                return false;
            }
        });

		/*
         * imageFlipper.setAutoStart(true); imageFlipper.setFlipInterval(10000);
		 */

    }

    private void setAllInfoForSpot() {
        setTitle(spotInfo.getSpotName());
        txtSpotDiscrip.setText(spotInfo.getDiscripInfo().trim());
        txtSpotLocation.setText(spotInfo.getLocationInfo().trim());
        txtSpotHotels.setText(spotInfo.getHotelsInfo().trim());
        txtSpotFamousThings.setText(spotInfo.getFamousInfo().trim());
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {

        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.spot, menu);
        return super.onCreateOptionsMenu(menu);

    }

    private void getOverFlowMenu() {
        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class
                    .getDeclaredField("sHasPermanentMenuKey");
            if (menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.add_favourites:
                addToFavourites(spotName, spotType);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_map:
                Intent i = new Intent(this, GoogleMapForEachSpotActivity.class);
                i.putExtra("SpotLatLongInfo", this.latLongInfo);
                startActivity(i);
                break;
            case R.id.btn_direction:
                Intent i1 = new Intent(this, GoogleMapDirectionActivity.class);
                i1.putExtra("SpotLatLongInfo", this.latLongInfo);
                startActivity(i1);
                break;
            default:
                break;
        }

    }

    private boolean isExist(String spotName, String spotType) {
        int count = tourismGuiderDatabase.getNumOfFavouriteList(spotName, spotType);
        if (count == 0) {
            return true;
        } else {
            return false;
        }
    }

    private void addToFavourites(String spotName, String spotType) {
        boolean checked = isExist(spotName, spotType);
        if (!checked) {
            Toast.makeText(getApplicationContext(), "Already in favourites",
                    Toast.LENGTH_LONG).show();
        } else {
            long res = tourismGuiderDatabase.insertIntoFavouriteList(spotName, spotType);
            if (res >= 1) {
                Toast.makeText(getApplicationContext(), "added to favourites",
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    private class ImageFlipperGestoreDetector extends
            GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                               float velocityY) {
            try {
                if (e1.getX() > e2.getX()) {
                    imageFlipper.setInAnimation(getApplicationContext(),
                            R.anim.left_in);
                    imageFlipper.setOutAnimation(getApplicationContext(),
                            R.anim.left_out);
                    imageFlipper.showNext();
                    return true;
                } else if (e1.getX() < e2.getX()) {
                    imageFlipper.setInAnimation(getApplicationContext(),
                            R.anim.right_in);
                    imageFlipper.setOutAnimation(getApplicationContext(),
                            R.anim.right_out);
                    imageFlipper.showPrevious();
                    return true;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return false;
        }

    }

}

