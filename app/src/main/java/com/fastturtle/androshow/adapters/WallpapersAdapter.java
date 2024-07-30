package com.fastturtle.androshow.adapters;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
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
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.fastturtle.androshow.R;
import com.fastturtle.androshow.activities.WallpaperPoster;
import com.fastturtle.androshow.models.AsyncParams;
import com.fastturtle.androshow.models.WallpapersModel;
import com.fastturtle.androshow.staticclasses.Config;
import com.fastturtle.androshow.staticclasses.SetAsHomeWallpaperAsync;
import com.fastturtle.androshow.staticclasses.SetAsLockWallpaperAsync;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class WallpapersAdapter extends RecyclerView.Adapter<WallpapersAdapter.ViewHolder> {

    private Context contextGlobal;
    private View mView;
    private static int w, h;
    public static int height, width;
    private ArrayList<WallpapersModel> imagesArrayList;
    private View snackView;
    private String selectedWallpaperDownloadUrl = "";
    private int position;

    public WallpapersAdapter(Context context, ArrayList<WallpapersModel> imagesArrayList) {
        this.imagesArrayList = imagesArrayList;
        this.contextGlobal = context;
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

        Glide.with(contextGlobal)
                .asBitmap()
                .load(imagesArrayList.get(holder.getBindingAdapterPosition()).getURL())
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        w = resource.getWidth();
                        h = resource.getHeight();
                        holder.imageview.setImageBitmap(resource);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadPhoto(holder.imageview, w, h, contextGlobal, holder.getBindingAdapterPosition());
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
        this.position = position;

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
                if (wallOptions[0].equals(wallOptions[i])) {

                    selectedWallpaperDownloadUrl = imagesArrayList.get(position).getURL();

                    if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions((AppCompatActivity) contextGlobal,
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, Config.MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE);
                    } else {
                        ((WallpaperPoster) contextGlobal).startFileDownload(selectedWallpaperDownloadUrl, imagesArrayList.get(position).getTitle());
                        Toast.makeText(contextGlobal, "Download started...", Toast.LENGTH_SHORT).show();
                    }
                } else if (wallOptions[1].equals(wallOptions[i])) {
                    Glide.with(context).asBitmap()
                            .load(imagesArrayList.get(position).getURL())
                            .placeholder(R.drawable.wallpaper_icon)
                            .into(new CustomTarget<Bitmap>() {
                                @Override
                                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                    new SetAsHomeWallpaperAsync()
                                            .execute(new AsyncParams(
                                                    resource,
                                                    WallpapersAdapter.width,
                                                    WallpapersAdapter.height, context));
                                }

                                @Override
                                public void onLoadCleared(@Nullable Drawable placeholder) {

                                }
                            });

                } else if (wallOptions[2].equals(wallOptions[i])) {
                    Glide.with(context).asBitmap()
                            .placeholder(R.drawable.wallpaper_icon)
                            .load(imagesArrayList.get(position).getURL())
                            .into(new CustomTarget<Bitmap>() {
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

                                @Override
                                public void onLoadCleared(@Nullable Drawable placeholder) {

                                }
                            });
                }
            });

            optionsDialog.show();

        });
        imageDialog.show();
    }


    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case Config.MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    ((WallpaperPoster) contextGlobal).startFileDownload(selectedWallpaperDownloadUrl, imagesArrayList.get(position).getTitle());
                    Toast.makeText(contextGlobal, "Downloading...", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(contextGlobal, "Create Directory permission was not granted", Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }

    }

}
