package com.alaukikapps.movtronix.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alaukikapps.movtronix.R;
import com.alaukikapps.movtronix.adapters.WallpapersAdapter;
import com.alaukikapps.movtronix.common.AbstractBaseActivity;
import com.alaukikapps.movtronix.models.WallpapersModel;
import com.alaukikapps.movtronix.staticclasses.Config;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WallpaperPoster extends AbstractBaseActivity {
    private WallpapersAdapter posterAdapter;
    private ArrayList<WallpapersModel> wallpapersModelArrayList;
    private ProgressDialog simpleWaitDialog;

    private Callback<List<WallpapersModel>> wallpaperCallback = new Callback<List<WallpapersModel>>() {
        @Override
        public void onResponse(Call<List<WallpapersModel>> call, Response<List<WallpapersModel>> response) {
            if (simpleWaitDialog != null && simpleWaitDialog.isShowing()) {
                simpleWaitDialog.dismiss();
            }
            if (response.isSuccessful()) {
                List<WallpapersModel> wm = response.body();
                wallpapersModelArrayList.addAll(wm);
                posterAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onFailure(Call<List<WallpapersModel>> call, Throwable t) {
            simpleWaitDialog.dismiss();
            Log.d(getString(R.string.somethingWentWrong), t.getMessage());
        }
    };

    @Override
    protected void onCreatingBase(Bundle savedInstanceState) {
        setContentView(R.layout.activity_wallpaper_poster);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        simpleWaitDialog = new ProgressDialog(this);
        simpleWaitDialog.setTitle("Wait...");
        simpleWaitDialog.setMessage("Loading Images");

        Toolbar toolbar = findViewById(R.id.tool_bar);

        toolbar.setNavigationOnClickListener(view -> {
            onBackPressed();
        });

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        WallpapersAdapter.height = metrics.heightPixels;
        WallpapersAdapter.width = metrics.widthPixels;
        RecyclerView recViewPoster = findViewById(R.id.recView_poster);
        recViewPoster.setHasFixedSize(true);
        wallpapersModelArrayList = new ArrayList<>();
        posterAdapter = new WallpapersAdapter(this, wallpapersModelArrayList);
        recViewPoster.setLayoutManager(new GridLayoutManager(this, Config.numOfColumns));
        recViewPoster.setAdapter(posterAdapter);

        callWallpaperPosterAPI();
    }

    private void callWallpaperPosterAPI() {
        if (connectionChecker.checkInternet()) {
            Toast.makeText(getApplicationContext(), getString(R.string.checkInternetMessage), Toast.LENGTH_LONG).show();
        } else {
            simpleWaitDialog.show();
            apiIntegrationHelper.wallpaperBg(wallpaperCallback);
        }
    }

}