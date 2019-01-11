package com.marvellous.avengersuniverse.activities;

import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.TextView;

import com.marvellous.avengersuniverse.R;
import com.marvellous.avengersuniverse.adapters.WallpaperMenuAdapter;
import com.marvellous.avengersuniverse.adapters.WallpapersAdapter;
import com.marvellous.avengersuniverse.common.AbstractBaseActivity;

public class WallpaperActivity extends AbstractBaseActivity {

    ProgressDialog simpleWaitDialog;
    private WallpaperMenuAdapter wallpaperAdapter;
    private String[] wallpaper_category = {"IronMan", "Captain America", "Scarlett Witch",
            "Doctor", "Spiderman", "Black Panther",
            "Black Widow", "Thor", "Loki",
            "Posters", "Miscellaneous"};


    @Override
    protected void onCreatingBase(Bundle savedInstanceState) {
        setContentView(R.layout.activity_wallpapers);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        WallpapersAdapter.height = metrics.heightPixels;
        WallpapersAdapter.width = metrics.widthPixels;
        RecyclerView recyclerView = findViewById(R.id.wallmenu_recView);
        wallpaperAdapter = new WallpaperMenuAdapter(this, wallpaper_category);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.rec_view_divider));
        //recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setAdapter(wallpaperAdapter);

        TextView tvgallery = findViewById(R.id.textWallpaper);
        Typeface mycustomFont = Typeface.createFromAsset(getAssets(), "fonts/Exo-Medium.otf");

        tvgallery.setTypeface(mycustomFont);
    }

}
