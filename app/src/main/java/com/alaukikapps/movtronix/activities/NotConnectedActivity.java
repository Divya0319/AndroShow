package com.alaukikapps.movtronix.activities;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import com.alaukikapps.movtronix.R;
import com.alaukikapps.movtronix.common.AbstractBaseActivity;

public class NotConnectedActivity extends AbstractBaseActivity {

    @Override
    public void onCreatingBase(Bundle savedInstanceState) {
        setContentView(R.layout.notconnected);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        TextView tvNoInternet = findViewById(R.id.text_nointernet);
        TextView tvDontMiss = findViewById(R.id.text_hurryup);

        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/Exo-Medium.otf");
        tvDontMiss.setTypeface(tf);
        tvDontMiss.setTextSize((float) 30.0);
        tvNoInternet.setTextSize((float) 30.0);
        tvNoInternet.setTypeface(tf);
    }

}
