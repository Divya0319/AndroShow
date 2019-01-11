package com.marvellous.avengersuniverse.staticclasses;

import android.app.Application;
import android.content.Context;

import com.google.android.gms.ads.MobileAds;
import com.marvellous.avengersuniverse.R;

public class App extends Application {
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();

        MobileAds.initialize(this, getResources().getString(R.string.adUnitId));
    }
}
