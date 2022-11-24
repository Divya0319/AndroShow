package com.alaukikapps.movtronix.activities;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.WindowManager;

import com.alaukikapps.movtronix.R;
import com.alaukikapps.movtronix.common.AbstractBaseActivity;

public class SplashActivity extends AbstractBaseActivity {
    MediaPlayer mySong;

    @Override
    protected void onCreatingBase(Bundle savedInstanceState) {
        setContentView(R.layout.activity_splash);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mySong = MediaPlayer.create(SplashActivity.this, R.raw.intro);
        mySong.start();


        Thread t1 = new Thread() {
            public void run() {
                try {
                    sleep(6 * 1000);

                    final ConnectivityManager conMgr = (ConnectivityManager) getSystemService(
                            Context.CONNECTIVITY_SERVICE);
                    final NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();
                    if (activeNetwork != null && activeNetwork.isConnected()) {
                        Intent i1 = new Intent(getApplicationContext(), DashBoardActivity.class);
                        startActivity(i1);
                        finish();

                    } else {
                        Intent i2 = new Intent(getApplicationContext(), NotConnectedActivity.class);
                        startActivity(i2);
                        finish();

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        };
        t1.start();
    }

    protected void onPause() {
        super.onPause();
        mySong.release();
        finish();
    }
}
