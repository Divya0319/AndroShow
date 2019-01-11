package com.marvellous.avengersuniverse.activities;

import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.marvellous.avengersuniverse.R;
import com.marvellous.avengersuniverse.adapters.WallpapersAdapter;
import com.marvellous.avengersuniverse.common.AbstractBaseActivity;
import com.marvellous.avengersuniverse.models.WallpapersModel;
import com.marvellous.avengersuniverse.staticclasses.Config;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class WallpaperIronMan extends AbstractBaseActivity {

    private WallpapersAdapter ironManAdapter;
    private ArrayList<WallpapersModel> wallpapersModelArrayList;
    private ProgressDialog simpleWaitDialog;

    private Callback<List<WallpapersModel>> wallpaperCallback = new Callback<List<WallpapersModel>>() {
        @Override
        public void onResponse(Call<List<WallpapersModel>> call, retrofit2.Response<List<WallpapersModel>> response) {
            if (simpleWaitDialog != null && simpleWaitDialog.isShowing()) {
                simpleWaitDialog.dismiss();
            }
            if (response.isSuccessful()) {
                List<WallpapersModel> wm = response.body();
                wallpapersModelArrayList.addAll(wm);
                ironManAdapter.notifyDataSetChanged();
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
        setContentView(R.layout.activity_wallpaper_iron_man);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        simpleWaitDialog = new ProgressDialog(this);
        simpleWaitDialog.setTitle("Wait...");
        simpleWaitDialog.setMessage("Loading Images");

        RecyclerView recViewIronman = findViewById(R.id.recViewIronMan);
        recViewIronman.setHasFixedSize(true);
        wallpapersModelArrayList = new ArrayList<>();
        ironManAdapter = new WallpapersAdapter(this, wallpapersModelArrayList);
        recViewIronman.setLayoutManager(new GridLayoutManager(this, Config.numOfColumns));
        recViewIronman.setAdapter(ironManAdapter);

        TextView textView = findViewById(R.id.textIronMan);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Exo-Medium.otf");
        textView.setTypeface(typeface);

        callWallpaperIronManAPI();
    }

    private void callWallpaperIronManAPI() {
        if (connectionChecker.checkInternet()) {
            Toast.makeText(getApplicationContext(), getString(R.string.checkInternetMessage), Toast.LENGTH_LONG).show();
        } else {
            apiIntegrationHelper.wallpaperIronman(wallpaperCallback);
        }
    }
}
