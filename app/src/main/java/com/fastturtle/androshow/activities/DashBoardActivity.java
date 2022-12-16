package com.fastturtle.androshow.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.fastturtle.androshow.models.VideosResponse;
import com.fastturtle.androshow.models.WallpapersModel;
import com.fastturtle.androshow.network.NetworkKeys;
import com.gigamole.infinitecycleviewpager.HorizontalInfiniteCycleViewPager;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.fastturtle.androshow.R;
import com.fastturtle.androshow.adapters.AutoSwipePicsPagerAdapter;
import com.fastturtle.androshow.adapters.AutoSwipeVideoPagerAdapter;
import com.fastturtle.androshow.common.AbstractBaseActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashBoardActivity extends AbstractBaseActivity {

    private DrawerLayout mDrawerLayout;

    boolean backToExitPressedOnce = false;
    Intent i;
    private String[] trending_videos_id = new String[3];
    private String[] videos_desc = new String[3];
    private String[] promo_images_url = new String[3];
    private String[] promo_desc = new String[3];

    HorizontalInfiniteCycleViewPager viewPagerVideos;
    HorizontalInfiniteCycleViewPager viewPagerPhotos;

    AutoSwipeVideoPagerAdapter pagerAdapterVideos;
    AutoSwipePicsPagerAdapter pagerAdapterPics;


    private Callback<List<VideosResponse>> videosResponseCallback = new Callback<List<VideosResponse>>() {
        @Override
        public void onResponse(Call<List<VideosResponse>> call, retrofit2.Response<List<VideosResponse>> response) {

            if (response.isSuccessful()) {
                List<VideosResponse> vrl = response.body();
                for(int i = 0; i < 3; i++) {
                    trending_videos_id[i] = vrl.get(i).getVIDID();
                    videos_desc[i] = vrl.get(i).getTITLE();
                    pagerAdapterVideos = new AutoSwipeVideoPagerAdapter(DashBoardActivity.this, trending_videos_id, videos_desc);
                    viewPagerVideos.setAdapter(pagerAdapterVideos);
                    viewPagerVideos.setCurrentItem(1);
                    new AsyncAutoSwipeVideos().execute(viewPagerVideos);
                }
            }
        }

        @Override
        public void onFailure(Call<List<VideosResponse>> call, Throwable t) {

            Log.d(getString(R.string.somethingWentWrong), t.getMessage());
            Toast.makeText(DashBoardActivity.this, getString(R.string.somethingWentWrong), Toast.LENGTH_LONG).show();
        }
    };

    private Callback<List<WallpapersModel>> wallpaperCallback = new Callback<List<WallpapersModel>>() {
        @Override
        public void onResponse(Call<List<WallpapersModel>> call, Response<List<WallpapersModel>> response) {
            if (response.isSuccessful()) {
                List<WallpapersModel> wml = response.body();
                for(int i = 0; i < 3; i++) {
                    promo_images_url[i] = wml.get(i).getURL();
                    promo_desc[i] = wml.get(i).getTitle();
                    pagerAdapterPics = new AutoSwipePicsPagerAdapter(DashBoardActivity.this, promo_images_url, promo_desc);
                    viewPagerPhotos.setAdapter(pagerAdapterPics);
                    viewPagerPhotos.setCurrentItem(2);
                    new AsyncAutoSwipePhoto().execute(viewPagerPhotos);
                }
            }
        }

        @Override
        public void onFailure(Call<List<WallpapersModel>> call, Throwable t) {
            Log.d(getString(R.string.somethingWentWrong), t.getMessage());
            Toast.makeText(DashBoardActivity.this, getString(R.string.somethingWentWrong), Toast.LENGTH_LONG).show();
        }
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
        viewPagerVideos = findViewById(R.id.viewPagerVideos);
        viewPagerPhotos = findViewById(R.id.viewPagerPhotos);

        callVideosAPI();
        callWallpaperPosterAPI();

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

    private void callVideosAPI() {
        if (connectionChecker.checkInternet()) {
            Toast.makeText(getApplicationContext(), getString(R.string.checkInternetMessage), Toast.LENGTH_LONG).show();
        } else {
            apiIntegrationHelper.videosData(videosResponseCallback);
        }
    }

    private void callWallpaperPosterAPI() {
        if (connectionChecker.checkInternet()) {
            Toast.makeText(getApplicationContext(), getString(R.string.checkInternetMessage), Toast.LENGTH_LONG).show();
        } else {
            apiIntegrationHelper.wallpaperBg(wallpaperCallback);
        }
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
