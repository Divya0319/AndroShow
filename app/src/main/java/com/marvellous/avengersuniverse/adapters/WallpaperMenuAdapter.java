package com.marvellous.avengersuniverse.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.marvellous.avengersuniverse.R;
import com.marvellous.avengersuniverse.activities.WallpaperCaptain;
import com.marvellous.avengersuniverse.activities.WallpaperDoctor;
import com.marvellous.avengersuniverse.activities.WallpaperIronMan;
import com.marvellous.avengersuniverse.activities.WallpaperLoki;
import com.marvellous.avengersuniverse.activities.WallpaperMisc;
import com.marvellous.avengersuniverse.activities.WallpaperPoster;
import com.marvellous.avengersuniverse.activities.WallpaperSpidy;
import com.marvellous.avengersuniverse.activities.WallpaperTchaala;
import com.marvellous.avengersuniverse.activities.WallpaperThor;
import com.marvellous.avengersuniverse.activities.WallpaperWanda;
import com.marvellous.avengersuniverse.activities.WallpaperWidow;

public class WallpaperMenuAdapter extends RecyclerView.Adapter<WallpaperMenuAdapter.ViewHolder> {
    private String[] wallpaper_list;
    private Context context;
    Intent i;

    public WallpaperMenuAdapter(Context context, String[] wallpaper_list) {
        this.context = context;
        this.wallpaper_list = wallpaper_list;
        i = new Intent();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wallpaper_menu_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.wallpaper_category.setText(wallpaper_list[position]);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.getAdapterPosition() == 0) {
                    i = new Intent(context, WallpaperIronMan.class);
                    context.startActivity(i);

                } else if (holder.getAdapterPosition() == 1) {
                    i = new Intent(context, WallpaperCaptain.class);
                    context.startActivity(i);

                } else if (holder.getAdapterPosition() == 2) {
                    i = new Intent(context, WallpaperWanda.class);
                    context.startActivity(i);

                } else if (holder.getAdapterPosition() == 3) {
                    i = new Intent(context, WallpaperDoctor.class);
                    context.startActivity(i);

                } else if (holder.getAdapterPosition() == 4) {
                    i = new Intent(context, WallpaperSpidy.class);
                    context.startActivity(i);

                } else if (holder.getAdapterPosition() == 5) {
                    i = new Intent(context, WallpaperTchaala.class);
                    context.startActivity(i);

                } else if (holder.getAdapterPosition() == 6) {
                    i = new Intent(context, WallpaperWidow.class);
                    context.startActivity(i);

                } else if (holder.getAdapterPosition() == 7) {
                    i = new Intent(context, WallpaperThor.class);
                    context.startActivity(i);

                } else if (holder.getAdapterPosition() == 8) {
                    i = new Intent(context, WallpaperLoki.class);
                    context.startActivity(i);

                } else if (holder.getAdapterPosition() == 9) {
                    i = new Intent(context, WallpaperPoster.class);
                    context.startActivity(i);

                } else if (holder.getAdapterPosition() == 10) {
                    i = new Intent(context, WallpaperMisc.class);
                    context.startActivity(i);

                }
            }
        });

    }


    @Override
    public int getItemCount() {
        return wallpaper_list.length;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView wallpaper_category;
        private View mView;

        ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            wallpaper_category = mView.findViewById(R.id.tv_wallpaper_category);
        }
    }
}
