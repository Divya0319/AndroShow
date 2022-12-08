package com.fastturtle.androshow.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.view.Gravity;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.fastturtle.androshow.network.NetworkKeys;
import com.gigamole.infinitecycleviewpager.HorizontalInfiniteCycleViewPager;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.fastturtle.androshow.R;
import com.fastturtle.androshow.adapters.AutoSwipePicsPagerAdapter;
import com.fastturtle.androshow.adapters.AutoSwipeVideoPagerAdapter;
import com.fastturtle.androshow.common.AbstractBaseActivity;

public class DashBoardActivity extends AbstractBaseActivity {

    private DrawerLayout mDrawerLayout;

    boolean backToExitPressedOnce = false;
    Intent i;
    private String trending_videos_id[] = {
            "HKmAex_qogE",
            "XfNj5c9bW-k",
            "e9gW3Ejg2uY",
    };
    private String videos_desc[] = {
            "Avengers the endgame glipmse",
            "(MCU) Stephen & Christine - Glimpse Of Us",
            "TOP 10 BEST ACTION SCENES FROM MARVEL MOVIES",

    };
    private String[] promo_images_url = {
            NetworkKeys.ANDROSHOW_BASE_URL + "/walls/green_lake.jpg",
            NetworkKeys.ANDROSHOW_BASE_URL + "/walls/hexagonal.jpg",
            NetworkKeys.ANDROSHOW_BASE_URL + "/walls/green_grass.jpg"
    };
    private String[] promo_desc = {
            "Green Lake",
            "Hexagonal",
            "Green Grass"
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
                        i.setClass(DashBoardActivity.this, WallpaperPoster.class);
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
                mDrawerLayout.openDrawer(Gravity.LEFT);
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
