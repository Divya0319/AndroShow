package com.marvellous.avengersuniverse.adapters;

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
import com.marvellous.avengersuniverse.R;

public class AutoSwipeVideoPagerAdapter extends PagerAdapter {
    private Context ctx;
    private String[] trending_vidId, video_desc;

    public AutoSwipeVideoPagerAdapter(Context ctx, String trending_vidId[], String[] video_desc) {
        this.ctx = ctx;
        this.trending_vidId = trending_vidId;
        this.video_desc = video_desc;
    }

    @Override
    public int getCount() {
        return trending_vidId.length;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull final ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.view_pager_videos_item, container, false);

        ImageView img = view.findViewById(R.id.pager_image_videos);
        TextView tv = view.findViewById(R.id.pager_title_videos);
        tv.setText(video_desc[position]);
        RequestOptions options = new RequestOptions().placeholder(R.drawable.wallpaper_icon);
        Glide.with(ctx).load("http://img.youtube.com/vi/" + trending_vidId[position] + "/0.jpg").apply(options).into(img);

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
