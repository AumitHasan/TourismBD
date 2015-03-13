package com.apppreview.shuvojit.tourismbd.allpackges.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.apppreview.shuvojit.tourismbd.R;
import com.apppreview.shuvojit.tourismbd.allpackges.databaseTablesModel.FavouritesListsTable;
import com.apppreview.shuvojit.tourismbd.allpackges.databaseTablesModel.LatLongInfoOfAllSpotsTable;
import com.apppreview.shuvojit.tourismbd.allpackges.databaseTablesModel.SpotInfoTable;
import com.apppreview.shuvojit.tourismbd.allpackges.infos.SpotImagesResourceInfo;
import com.apppreview.shuvojit.tourismbd.allpackges.interfaces.FontClient;
import com.apppreview.shuvojit.tourismbd.allpackges.interfaces.InitializerClient;
import com.apppreview.shuvojit.tourismbd.allpackges.popUpWindows.progressDialogs.UserChoicePromptPopUpWindow;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;


public class TouristSpotInfoActivity extends ActionBarActivity implements OnClickListener,
        InitializerClient, FontClient{

    public static boolean isSpotNameExist = false;
    private TextView txtSpotLocation, txtSpotDiscrip, txtSpotFamousThings,
            txtSpotHotels;
    private Button btnMap, btnDirection;
    private Intent intent;
    private SpotInfoTable spotInfo;
    private LatLongInfoOfAllSpotsTable latLongInfo;
    private String spotType;
    private String spotName;
    /*private ImageFlipperGestureDetector imageFlipperGestoreDetector;
    private GestureDetector gestureDetector;
    private ViewFlipper imageFlipper;*/
    private SpotImagesResourceInfo spotImagesResourceInfo;
    private ActionBar actionBar;
    private MenuItem favouritesMenu;
    private SliderLayout imageSliderLayout;
    private Typeface fontTypeface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tourist_spot_info_activity_layout);
        initialize();
        isSpotNameExist = isExist(spotName, spotType);
        setAllInfoForSpot();
        setImageSlider();
        //getOverFlowMenu();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public void initialize() {
        actionBar = getSupportActionBar();
        fontTypeface = Typeface.createFromAsset(getAssets(), UBUNTU_FONT_PATH );
        TextView labelSpotlocation = (TextView) findViewById(R.id.label_location);
        labelSpotlocation.setTypeface(fontTypeface);
        TextView labelDiscrip = (TextView) findViewById(R.id.label_discription);
        labelDiscrip.setTypeface(fontTypeface);
        TextView labelFamousThings = (TextView) findViewById(R.id.label_famous_things);
        labelFamousThings.setTypeface(fontTypeface);
        TextView labelSpotHotels = (TextView) findViewById(R.id.label_hotels);
        labelSpotHotels.setTypeface(fontTypeface);
        txtSpotLocation = (TextView) findViewById(R.id.txt_spot_location);
        txtSpotLocation.setTypeface(fontTypeface);
        txtSpotDiscrip = (TextView) findViewById(R.id.txt_spot_discrip);
        txtSpotDiscrip.setTypeface(fontTypeface);
        txtSpotFamousThings = (TextView) findViewById(R.id.txt_spot_famous_things);
        txtSpotFamousThings.setTypeface(fontTypeface);
        txtSpotHotels = (TextView) findViewById(R.id.txt_spot_hotels);
        txtSpotHotels.setTypeface(fontTypeface);
        btnMap = (Button) findViewById(R.id.btn_map);
        btnMap.setTypeface(fontTypeface);
        btnDirection = (Button) findViewById(R.id.btn_direction);
        btnDirection.setTypeface(fontTypeface);
        btnMap.setOnClickListener(this);
        btnDirection.setOnClickListener(this);
        intent = getIntent();
        spotInfo = (SpotInfoTable) intent.getSerializableExtra("SpotInfo");
        latLongInfo = (LatLongInfoOfAllSpotsTable) intent
                .getSerializableExtra("SpotLatLongInfo");
        spotName = latLongInfo.getSpotName();
        spotType = latLongInfo.getSpotTypeField();
        //imageFlipper = (ViewFlipper) findViewById(R.id.viewImageFlipper);
        spotImagesResourceInfo = new SpotImagesResourceInfo(TouristSpotInfoActivity.this);
        imageSliderLayout = (SliderLayout) findViewById(R.id.image_slider);

    }

    private void setImageSlider() {
        int imageResources[] = spotImagesResourceInfo
                .getSpotImageResources(spotName);
        for (int i = 0; i < imageResources.length; i++) {
            TextSliderView textSliderView = new TextSliderView(this);
            textSliderView.image(imageResources[i])
                    .setScaleType(BaseSliderView.ScaleType.Fit);
            imageSliderLayout.addSlider(textSliderView);
            /*ImageView spotImageView = new ImageView(getApplicationContext());
            spotImageView.setLayoutParams(new LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            spotImageView.setScaleType(ImageView.ScaleType.FIT_XY);
            spotImageView.setImageResource(imageResources[i]);
            imageFlipper.addView(spotImageView);*/
        }
        imageSliderLayout.setPresetTransformer(SliderLayout.Transformer.FlipHorizontal);
        imageSliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        imageSliderLayout.setCustomAnimation(new DescriptionAnimation());
        imageSliderLayout.setDuration(4000);

/*        imageFlipper.setInAnimation(this, android.R.anim.fade_in);
        imageFlipper.setOutAnimation(this, android.R.anim.fade_out);
        imageFlipperGestoreDetector = new ImageFlipperGestureDetector();
        gestureDetector = new GestureDetector(this, imageFlipperGestoreDetector);
        imageFlipper.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                gestureDetector.onTouchEvent(event);
                return false;
            }
        });*/

		/*
         * imageFlipper.setAutoStart(true); imageFlipper.setFlipInterval(10000);
		 */

    }

    private void setAllInfoForSpot() {
        setTitle(spotInfo.getSpotName());
        txtSpotDiscrip.setText(spotInfo.getDiscription().trim());
        txtSpotLocation.setText(spotInfo.getLocation().trim());
        txtSpotHotels.setText(spotInfo.getHotelsInfo().trim());
        txtSpotFamousThings.setText(spotInfo.getFamousSpots().trim());
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
        getMenuInflater().inflate(R.menu.tourist_spot_info_activity_menu, menu);
        Menu mainMenu = menu;
        if (mainMenu != null) {
            favouritesMenu = mainMenu.findItem(R.id.add_favourites);
            if (isSpotNameExist) {
                favouritesMenu.setIcon(R.drawable.ic_action_favorite);
            }
        }
        return super.onCreateOptionsMenu(menu);

    }

    /*private void getOverFlowMenu() {
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
*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();
                break;
            case R.id.add_favourites:
                if (favouritesMenu != null) {
                    if (!isSpotNameExist) {
                        UserChoicePromptPopUpWindow.
                                showInsertTouristSpotIntoFavouritesListProgressDialog
                                        (favouritesMenu, TouristSpotInfoActivity.this,
                                                spotName, spotType);
                    } else {
                        UserChoicePromptPopUpWindow.
                                showDeleteTouristSpotFromFavouritesListProgressDialog
                                        (favouritesMenu, TouristSpotInfoActivity.this,
                                                spotName, spotType);
                    }
                }
                /*else
                {
                    userChoicePromptPopUpWindow = new
                            UserChoicePromptPopUpWindow
                            (TouristSpotInfoActivity.this, "Delete Data", favouritesMenu);
                }
                userChoicePromptPopUpWindow.setPopUpWindow();
                userChoicePromptPopUpWindow.showPopUpWindow(parentView, spotType, spotName);*/
                break;
        }
        return true;
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
        return FavouritesListsTable.isFavouriteSpotNameExist(spotName, spotType);
    }


   /* private class ImageFlipperGestureDetector extends
            GestureDetector.SimpleOnGestureListener {

        public ImageFlipperGestureDetector() {

        }

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

    }*/

}

