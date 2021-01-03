package com.ehb.androiddevapp;

import android.app.Application;
import android.util.Log;

import com.ehb.androiddevapp.domain.Utils;
import com.treebo.internetavailabilitychecker.InternetAvailabilityChecker;
import com.treebo.internetavailabilitychecker.InternetConnectivityListener;

//this class will always run when app start
public class MyApplication extends Application implements InternetConnectivityListener {

    @Override
    public void onCreate() {
        super.onCreate();
        //internet listener init
        InternetAvailabilityChecker.init(getApplicationContext());
        InternetAvailabilityChecker mInternetAvailabilityChecker = InternetAvailabilityChecker.getInstance();
        //register listener
        mInternetAvailabilityChecker.addInternetConnectivityListener(this);
    }

    @Override
    public void onInternetConnectivityChanged(boolean isConnected) {
        // it will help us to check internet through the app
        Utils.isInternetConnected = isConnected;
        Log.d("testinfor", String.valueOf(isConnected));

    }
}
