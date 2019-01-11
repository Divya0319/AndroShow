package com.marvellous.avengersuniverse.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.gigamole.infinitecycleviewpager.HorizontalInfiniteCycleViewPager;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.marvellous.avengersuniverse.R;
import com.marvellous.avengersuniverse.adapters.AutoSwipePicsPagerAdapter;
import com.marvellous.avengersuniverse.adapters.AutoSwipeVideoPagerAdapter;
import com.marvellous.avengersuniverse.common.AbstractBaseActivity;

public class DashBoardActivity extends AbstractBaseActivity {

    private DrawerLayout mDrawerLayout;
    private AdView adView;

    boolean backToExitPressedOnce = false;
    Intent i;
    private String trending_videos_id[] = {
            "Im4PuR5Ib8Y",
            "WVyvjF7Lr3s",
            "Fy3Mi8rOB3U",
            "em2Ho2t0OMw"
    };
    private String videos_desc[] = {
            "Chris Evans - Funny moments",
            "Infinity War Cast Goes Crazy with Thanos' Glove!",
            "Chris Pratt Knows The Best Card Trick Ever",
            "Tom Holland Making People Laugh"

    };
    private String[] promo_images_url = {
            "https://divyaandroidapps.000webhostapp.com/Avengers/wallpapers/blackwidow/black_widow8.jpg",
            "https://divyaandroidapps.000webhostapp.com/Avengers/wallpapers/blackpanther/black_panther5.jpg",
            "https://divyaandroidapps.000webhostapp.com/Avengers/wallpapers/spiderman/spidersign.jpg",
            "https://divyaandroidapps.000webhostapp.com/Avengers/wallpapers/captain/captains_shield.jpg"
    };
    private String[] promo_desc = {
            "Black Widow",
            "Black Panther",
            "Spider-Man",
            "Captain's Shield"
    };

    @Override
    protected void onCreatingBase(Bundle savedInstanceState) {
        setContentView(R.layout.activity_dashboard);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("");
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);
        adView = findViewById(R.id.adView_dashboard);

        AdRequest adRequest = new AdRequest.Builder().build();

        mDrawerLayout = findViewById(R.id.drawer_layout);
        mDrawerLayout.closeDrawers();
        i = new Intent();
        HorizontalInfiniteCycleViewPager viewPagerVideos = findViewById(R.id.viewPagerVideos);
        HorizontalInfiniteCycleViewPager viewPagerPhotos = findViewById(R.id.viewPagerPhotos);
        AutoSwipeVideoPagerAdapter pagerAdapterVideos = new AutoSwipeVideoPagerAdapter(this, trending_videos_id, videos_desc);
        AutoSwipePicsPagerAdapter pagerAdapterPics = new AutoSwipePicsPagerAdapter(this, promo_images_url, promo_desc);

        viewPagerVideos.setAdapter(pagerAdapterVideos);
        viewPagerVideos.setCurrentItem(1);
        new AsyncAutoSwipeVideos().execute(viewPagerVideos);
        viewPagerPhotos.setAdapter(pagerAdapterPics);
        viewPagerPhotos.setCurrentItem(2);
        new AsyncAutoSwipePhoto().execute(viewPagerPhotos);

        NavigationView navView = findViewById(R.id.nav_view);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                mDrawerLayout.closeDrawers();

                switch (item.getItemId()) {
                    case R.id.menu_song:
                        i.setClass(DashBoardActivity.this, SongsActivity.class);
                        startActivity(i);
                        break;
                    case R.id.menu_gallery:
                        i.setClass(DashBoardActivity.this, WallpaperActivity.class);
                        startActivity(i);
                        break;
                    case R.id.menu_ringtones:
                        i.setClass(DashBoardActivity.this, RingTonesActivity.class);
                        startActivity(i);
                        break;
                    case R.id.menu_videos:
                        i.setClass(DashBoardActivity.this, VideosActivity.class);
                        startActivity(i);
                        break;
                    case R.id.menu_aboutUs:
                        i.setClass(DashBoardActivity.this, AboutUsActivity.class);
                        startActivity(i);
                        break;
                }
                return true;
            }
        });

        adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {

            }

            @Override
            public void onAdClosed() {
                Toast.makeText(getApplicationContext(), "Ad is closed", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onAdFailedToLoad(int i) {
                Toast.makeText(getApplicationContext(), "Ad failed to open, error code:" + i, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
            }
        });
        adView.loadAd(adRequest);
    }

    @Override
    protected void onPause() {
        if (adView != null) {
            adView.pause();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (adView != null) {
            adView.resume();
        }
    }

    @Override
    protected void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (backToExitPressedOnce) {
            super.onBackPressed();
        }
        this.backToExitPressedOnce = true;
        final RelativeLayout relativeLayout = findViewById(R.id.relativeLayout);
        Snackbar.make(relativeLayout, "Press back again to exit", Snackbar.LENGTH_LONG).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                backToExitPressedOnce = false;
            }
        }, 2000);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(Gravity.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    static class AsyncAutoSwipeVideos extends AsyncTask<HorizontalInfiniteCycleViewPager, Void, Void> {

        @Override
        protected Void doInBackground(HorizontalInfiniteCycleViewPager... viewPagers) {
            viewPagers[0].setScrollDuration(7000);
            viewPagers[0].setMediumScaled(true);
            viewPagers[0].setMaxPageScale(0.8F);
            viewPagers[0].setMinPageScale(0.5F);
            viewPagers[0].setCenterPageScaleOffset(30.0F);
            viewPagers[0].setMinPageScaleOffset(5.0F);
            return null;
        }
    }

    static class AsyncAutoSwipePhoto extends AsyncTask<HorizontalInfiniteCycleViewPager, Void, Void> {

        @Override
        protected Void doInBackground(HorizontalInfiniteCycleViewPager... viewPagers) {
            viewPagers[0].setScrollDuration(7000);
            viewPagers[0].setMediumScaled(true);
            viewPagers[0].setMaxPageScale(0.8F);
            viewPagers[0].setMinPageScale(0.5F);
            viewPagers[0].setCenterPageScaleOffset(30.0F);
            viewPagers[0].setMinPageScaleOffset(5.0F);
            return null;
        }
    }
}
