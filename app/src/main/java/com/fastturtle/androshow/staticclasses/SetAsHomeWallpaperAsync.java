package com.fastturtle.androshow.staticclasses;

import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.widget.Toast;

import com.fastturtle.androshow.models.AsyncParams;

import java.io.IOException;

public class SetAsHomeWallpaperAsync extends AsyncTask<AsyncParams, Void, Void> {

    @Override
    protected void onPreExecute() {
        Toast.makeText(App.context, "Setting wallpaper...", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected Void doInBackground(AsyncParams... params) {
        Bitmap bitmap = Bitmap.createScaledBitmap(params[0].bitmap, params[0].bitmapWidth, params[0].bitmapHeight, true);
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                WallpaperManager.getInstance(params[0].context).setBitmap(bitmap, null, true, WallpaperManager.FLAG_SYSTEM);
            } else {
                WallpaperManager.getInstance(params[0].context).setBitmap(bitmap);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        Toast.makeText(App.context, "Wallpaper Set Successfully", Toast.LENGTH_SHORT).show();
    }
}
