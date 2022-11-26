package com.alaukikapps.movtronix.activities;

import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.Bundle;

import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

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

public class WallpaperCaptain extends AbstractBaseActivity {

    private WallpapersAdapter captainAdapter;
    private ArrayList<WallpapersModel> WallpapersModelArrayList;
    private ProgressDialog simpleWaitDialog;

    private Callback<List<WallpapersModel>> captainWallpaperCallback = new Callback<List<WallpapersModel>>() {
        @Override
        public void onResponse(Call<List<WallpapersModel>> call, retrofit2.Response<List<WallpapersModel>> response) {
            if (simpleWaitDialog != null && simpleWaitDialog.isShowing()) {
                simpleWaitDialog.dismiss();
            }
            if (response.isSuccessful()) {
                List<WallpapersModel> cwl = response.body();
                WallpapersModelArrayList.addAll(cwl);
                captainAdapter.notifyDataSetChanged();
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
        setContentView(R.layout.activity_wallpaper_captain);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        simpleWaitDialog = new ProgressDialog(this);
        simpleWaitDialog.setTitle("Wait...");
        simpleWaitDialog.setMessage("Loading Images");

        RecyclerView recViewCaptain = findViewById(R.id.recViewCaptain);
        recViewCaptain.setHasFixedSize(true);
        WallpapersModelArrayList = new ArrayList<>();
        captainAdapter = new WallpapersAdapter(this, WallpapersModelArrayList);
        recViewCaptain.setLayoutManager(new GridLayoutManager(this, Config.numOfColumns));
        recViewCaptain.setAdapter(captainAdapter);

        TextView textView = findViewById(R.id.textCaptain);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Exo-Medium.otf");
        textView.setTypeface(typeface);

        callWallpaperCaptainAPI();
    }

    private void callWallpaperCaptainAPI() {
        if (connectionChecker.checkInternet()) {
            Toast.makeText(getApplicationContext(), getString(R.string.checkInternetMessage), Toast.LENGTH_LONG).show();
        } else {
            simpleWaitDialog.show();
//            apiIntegrationHelper.captainWallpapers(captainWallpaperCallback);
        }
    }

}
