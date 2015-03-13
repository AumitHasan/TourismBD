package com.apppreview.shuvojit.tourismbd.allpackges.applications;


import android.util.Log;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.app.Application;

/**
 * Created by shuvojit on 3/7/15.
 */
public class BootStrapper extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);
        Log.e(getClass().getName(), "Active Android Started");

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ActiveAndroid.dispose();
        Log.e(getClass().getName(), "Active Android dispose");
    }
}
