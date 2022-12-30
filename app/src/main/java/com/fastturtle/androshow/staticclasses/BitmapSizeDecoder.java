package com.fastturtle.androshow.staticclasses;

import android.content.Context;
import android.graphics.BitmapFactory;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.ResourceDecoder;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.resource.SimpleResource;
import com.bumptech.glide.load.resource.transcode.ResourceTranscoder;

import java.io.File;
import java.io.IOException;

/**
 * @Author: Divya Gupta
 * @Date: 30-Dec-22
 */
public class BitmapSizeDecoder implements ResourceDecoder<File, BitmapFactory.Options> {
    @Override
    public boolean handles(@NonNull File source, @NonNull Options options) throws IOException {
        return true;
    }

    @Nullable
    @Override
    public Resource<BitmapFactory.Options> decode(@NonNull File source, int width, int height, @NonNull Options options) throws IOException {
        BitmapFactory.Options bmpOptions = new BitmapFactory.Options();
        bmpOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(source.getAbsolutePath(), bmpOptions);
        return new SimpleResource<>(bmpOptions);
    }

    private void registerComponents(Context context, Glide glide, Registry registry) {
        registry.prepend(File.class, BitmapFactory.Options.class, new BitmapSizeDecoder());
        registry.register(BitmapFactory.Options.class, Size2.class, new OptionsSizeResourceTranscoder());

    }

    class OptionsSizeResourceTranscoder implements ResourceTranscoder<BitmapFactory.Options, Size2> {

        @Nullable
        @Override
        public Resource<Size2> transcode(@NonNull Resource<BitmapFactory.Options> toTranscode, @NonNull Options options) {
            BitmapFactory.Options bmpOptions = toTranscode.get();
            Size2 size2 = new Size2(bmpOptions.outWidth, bmpOptions.outHeight);
            return new SimpleResource<>(size2);
        }
    }
}