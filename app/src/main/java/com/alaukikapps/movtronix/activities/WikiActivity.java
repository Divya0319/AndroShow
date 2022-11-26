package com.alaukikapps.movtronix.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.alaukikapps.movtronix.R;
import com.alaukikapps.movtronix.common.AbstractBaseActivity;

public class WikiActivity extends AbstractBaseActivity {

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreatingBase(Bundle savedInstanceState) {
        setContentView(R.layout.activity_wiki);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        final WebView browser = findViewById(R.id.about_us_webview);
        browser.getSettings().setJavaScriptEnabled(true);
        browser.getSettings().setSupportZoom(true);
        browser.getSettings().setBuiltInZoomControls(true);

        browser.setWebViewClient(new WebViewClient());
        browser.loadUrl(getResources().getString(R.string.wiki_url));
    }
}
