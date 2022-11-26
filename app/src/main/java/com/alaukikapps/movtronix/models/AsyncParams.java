package com.alaukikapps.movtronix.models;

import android.content.Context;
import android.graphics.Bitmap;

public class AsyncParams {
    public Bitmap bitmap;
    public Context context;
    public int bitmapWidth, bitmapHeight;

    public AsyncParams(Bitmap bitmap, int width, int height, Context context) {
        this.bitmap = bitmap;
        this.bitmapWidth = width;
        this.bitmapHeight = height;
        this.context = context;
    }
}