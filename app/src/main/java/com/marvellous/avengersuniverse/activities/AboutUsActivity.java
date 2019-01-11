package com.marvellous.avengersuniverse.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.marvellous.avengersuniverse.R;
import com.marvellous.avengersuniverse.common.AbstractBaseActivity;

public class AboutUsActivity extends AbstractBaseActivity {

    @Override
    public void onCreatingBase(Bundle savedInstanceState) {
        setContentView(R.layout.activity_about_us);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        TextView link = findViewById(R.id.link_to_wiki);
        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AboutUsActivity.this, WikiActivity.class);
                startActivity(i);
            }
        });

    }
}
