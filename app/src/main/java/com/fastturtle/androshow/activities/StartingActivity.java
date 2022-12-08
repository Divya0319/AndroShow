package com.fastturtle.androshow.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.fastturtle.androshow.R;
import com.fastturtle.androshow.common.AbstractBaseActivity;

public class StartingActivity extends AbstractBaseActivity {
//    MediaPlayer mySong;

    @Override
    protected void onCreatingBase(Bundle savedInstanceState) {
        setContentView(R.layout.activity_splash);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ImageView appIcon = findViewById(R.id.iv_app_icon);

//        mySong = MediaPlayer.create(StartingActivity.this, R.raw.intro);
//        mySong.start();


//        Thread t1 = new Thread() {
//            public void run() {
//                try {
//                    sleep(6 * 1000);

//                    mySong.stop();
        final ConnectivityManager conMgr = (ConnectivityManager) getSystemService(
                Context.CONNECTIVITY_SERVICE);
        final NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnected()) {
            new Handler().postDelayed(() -> {
                appIcon.setVisibility(View.VISIBLE);

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    AnimatedVectorDrawable drawable = (AnimatedVectorDrawable) appIcon.getDrawable();
                    drawable.start();
                } else {
                    appIcon.setImageResource(R.mipmap.ic_launcher);
                }

            }, 500);

            new Handler().postDelayed(() -> {
                Intent i1 = new Intent(getApplicationContext(), DashBoardActivity.class);
                startActivity(i1);
                finish();
            }, 2000);

        } else {
            Intent i2 = new Intent(getApplicationContext(), NotConnectedActivity.class);
            startActivity(i2);
            finish();

        }

//                } catch (Exception e) {
//                    e.printStackTrace();
//                }

//            }
//        };
//        t1.start();
    }

//    protected void onPause() {
//        super.onPause();
//        mySong.release();
//        finish();
//    }
}