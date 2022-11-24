package com.alaukikapps.movtronix.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.alaukikapps.movtronix.R;
import com.alaukikapps.movtronix.common.AbstractBaseActivity;

public class AboutUsActivity extends AbstractBaseActivity {

    @Override
    public void onCreatingBase(Bundle savedInstanceState) {
        setContentView(R.layout.activity_about_us);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        TextView link = findViewById(R.id.link_to_wiki);
        Toolbar toolbar = findViewById(R.id.tool_bar);

        toolbar.setNavigationOnClickListener(view -> {
            onBackPressed();
        });
        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AboutUsActivity.this, WikiActivity.class);
                startActivity(i);
            }
        });

    }
}
