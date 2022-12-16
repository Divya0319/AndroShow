package com.fastturtle.androshow.adapters;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fastturtle.androshow.R;
import com.fastturtle.androshow.activities.WallpaperCaptain;
import com.fastturtle.androshow.activities.WallpaperDoctor;
import com.fastturtle.androshow.activities.WallpaperIronMan;
import com.fastturtle.androshow.activities.WallpaperLoki;
import com.fastturtle.androshow.activities.WallpaperMisc;
import com.fastturtle.androshow.activities.WallpaperPoster;
import com.fastturtle.androshow.activities.WallpaperSpidy;
import com.fastturtle.androshow.activities.WallpaperTchaala;
import com.fastturtle.androshow.activities.WallpaperThor;
import com.fastturtle.androshow.activities.WallpaperWanda;
import com.fastturtle.androshow.activities.WallpaperWidow;

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
                if (holder.getBindingAdapterPosition() == 0) {
                    i = new Intent(context, WallpaperIronMan.class);
                    context.startActivity(i);

                } else if (holder.getBindingAdapterPosition() == 1) {
                    i = new Intent(context, WallpaperCaptain.class);
                    context.startActivity(i);

                } else if (holder.getBindingAdapterPosition() == 2) {
                    i = new Intent(context, WallpaperWanda.class);
                    context.startActivity(i);

                } else if (holder.getBindingAdapterPosition() == 3) {
                    i = new Intent(context, WallpaperDoctor.class);
                    context.startActivity(i);

                } else if (holder.getBindingAdapterPosition() == 4) {
                    i = new Intent(context, WallpaperSpidy.class);
                    context.startActivity(i);

                } else if (holder.getBindingAdapterPosition() == 5) {
                    i = new Intent(context, WallpaperTchaala.class);
                    context.startActivity(i);

                } else if (holder.getBindingAdapterPosition() == 6) {
                    i = new Intent(context, WallpaperWidow.class);
                    context.startActivity(i);

                } else if (holder.getBindingAdapterPosition() == 7) {
                    i = new Intent(context, WallpaperThor.class);
                    context.startActivity(i);

                } else if (holder.getBindingAdapterPosition() == 8) {
                    i = new Intent(context, WallpaperLoki.class);
                    context.startActivity(i);

                } else if (holder.getBindingAdapterPosition() == 9) {
                    i = new Intent(context, WallpaperPoster.class);
                    context.startActivity(i);

                } else if (holder.getBindingAdapterPosition() == 10) {
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
