package com.marvellous.avengersuniverse.activities;

import android.annotation.SuppressLint;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.marvellous.avengersuniverse.R;
import com.marvellous.avengersuniverse.common.AbstractBaseActivity;

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
