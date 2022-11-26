package com.alaukikapps.movtronix.adapters;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.alaukikapps.movtronix.R;
import com.alaukikapps.movtronix.activities.WallpaperPoster;
import com.alaukikapps.movtronix.models.AsyncParams;
import com.alaukikapps.movtronix.models.WallpapersModel;
import com.alaukikapps.movtronix.staticclasses.Config;
import com.alaukikapps.movtronix.staticclasses.SetAsHomeWallpaperAsync;
import com.alaukikapps.movtronix.staticclasses.SetAsLockWallpaperAsync;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class WallpapersAdapter extends RecyclerView.Adapter<WallpapersAdapter.ViewHolder> implements ActivityCompat.OnRequestPermissionsResultCallback {

    private Activity contextGlobal;
    private View mView;
    private static int w, h;
    public static int height, width;
    private ArrayList<WallpapersModel> imagesArrayList;
    private View snackView;
    private String selectedWallpaperDownloadUrl = "";

    public WallpapersAdapter(Activity context, ArrayList<WallpapersModel> imagesArrayList) {
        this.imagesArrayList = imagesArrayList;
        this.contextGlobal = context;
    }

    public WallpapersAdapter() {

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_wallpaper_item, parent, false);
        snackView = view;
        this.mView = parent;
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        Glide.with(contextGlobal).asBitmap().load(imagesArrayList.get(position).getURL()).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                w = resource.getWidth();
                h = resource.getHeight();
                holder.imageview.setImageBitmap(resource);
            }
        });


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadPhoto(holder.imageview, w, h, contextGlobal, holder.getAdapterPosition());
            }
        });

    }

    @Override
    public int getItemCount() {
        return imagesArrayList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageview;
        private View mView;

        ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            imageview = mView.findViewById(R.id.grid_item_image);
        }
    }


    private void loadPhoto(final ImageView imageView, final int width, final int height, final Context context, final int position) {

        AlertDialog.Builder imageDialog = new AlertDialog.Builder(context);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.custom_fullimage_dialog, mView.findViewById(R.id.layout_root), false);
        ImageView image = layout.findViewById(R.id.fullimage);
        TextView tvPosterTitle = layout.findViewById(R.id.poster_title);
        tvPosterTitle.setText(imagesArrayList.get(position).getTitle());
        image.setImageDrawable(imageView.getDrawable());
        image.setMinimumHeight(height);
        image.setMinimumWidth(width);
        imageDialog.setView(layout);
        imageDialog.create();

        final String[] wallOptions = {"Download", "Set as Home Screen wallpaper", "Set as Lock Screen wallpaper"};
        image.setOnClickListener(v -> {
            AlertDialog.Builder optionsDialog = new AlertDialog.Builder(context);
            optionsDialog.setTitle("");
            optionsDialog.setItems(wallOptions, (dialogInterface, i) -> {
                if(wallOptions[0].equals(wallOptions[i])) {

                    selectedWallpaperDownloadUrl = imagesArrayList.get(position).getURL();

                    if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(contextGlobal,
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, Config.MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE);
                    } else {
                        ((WallpaperPoster)contextGlobal).startFileDownload(selectedWallpaperDownloadUrl);
                        Toast.makeText(contextGlobal, "Downloading...", Toast.LENGTH_SHORT).show();
                    }
                } else if(wallOptions[1].equals(wallOptions[i])) {
                    final RequestOptions options = new RequestOptions().placeholder(R.drawable.wallpaper_icon);
                    Glide.with(context).asBitmap()
                            .load(imagesArrayList.get(position).getURL())
                            .apply(options)
                            .into(new SimpleTarget<Bitmap>() {
                                @Override
                                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                    new SetAsHomeWallpaperAsync()
                                            .execute(new AsyncParams(
                                                    resource,
                                                    WallpapersAdapter.width,
                                                    WallpapersAdapter.height, context));
                                }
                            });
                } else if(wallOptions[2].equals(wallOptions[i])) {
                    RequestOptions options = new RequestOptions().placeholder(R.drawable.wallpaper_icon);
                    Glide.with(context).asBitmap()
                            .load(imagesArrayList.get(position).getURL())
                            .apply(options)
                            .into(new SimpleTarget<Bitmap>() {
                                @Override
                                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                        new SetAsLockWallpaperAsync()
                                                .execute(new AsyncParams(resource,
                                                        WallpapersAdapter.width,
                                                        WallpapersAdapter.height, context));
                                        Toast.makeText(context, "Wallpaper Set Successfully", Toast.LENGTH_LONG).show();
                                    } else {
                                        Snackbar.make(snackView, "For Marshmallow and below, LockScreen wallpapers can only be downloaded",
                                                Snackbar.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
            });

            optionsDialog.show();

        });
        imageDialog.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case Config.MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    ((WallpaperPoster)contextGlobal).startFileDownload(selectedWallpaperDownloadUrl);
                    Toast.makeText(contextGlobal, "Downloading...", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(contextGlobal, "Create Directory permission was not granted", Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }

    }

}
