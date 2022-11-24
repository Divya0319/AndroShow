package com.alaukikapps.movtronix.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.alaukikapps.movtronix.R;

public class AutoSwipePicsPagerAdapter extends PagerAdapter {
    private String promo_images_url[], promo_desc[];
    private Context ctx;
    private int currentPage = 0;

    public AutoSwipePicsPagerAdapter(Context ctx, String[] promo_images_url, String[] promo_desc) {
        this.ctx = ctx;
        this.promo_images_url = promo_images_url;
        this.promo_desc = promo_desc;
    }

    @Override
    public int getCount() {
        return promo_images_url.length;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull final ViewGroup container, int position) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.view_pager_photos_item, container, false);

        ImageView promo_image = view.findViewById(R.id.pager_image_photos);
        TextView tv = view.findViewById(R.id.pager_title_photos);
        tv.setText(promo_desc[position]);
        RequestOptions options = new RequestOptions().placeholder(R.drawable.wallpaper_icon);
        Glide.with(ctx).load(promo_images_url[position]).apply(options).into(promo_image);
        container.addView(view);

        return view;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view == object);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.invalidate();
    }
}
