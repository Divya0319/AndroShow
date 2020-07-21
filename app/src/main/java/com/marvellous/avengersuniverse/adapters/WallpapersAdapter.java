package com.marvellous.avengersuniverse.adapters;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.snackbar.Snackbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.marvellous.avengersuniverse.R;
import com.marvellous.avengersuniverse.models.AsyncParams;
import com.marvellous.avengersuniverse.models.WallpapersModel;
import com.marvellous.avengersuniverse.staticclasses.SetAsHomeWallpaperAsync;
import com.marvellous.avengersuniverse.staticclasses.SetAsLockWallpaperAsync;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class WallpapersAdapter extends RecyclerView.Adapter<WallpapersAdapter.ViewHolder> implements ActivityCompat.OnRequestPermissionsResultCallback {

    private Activity contextGlobal;
    private View mView;
    private static int w, h;
    public static int height, width;
    private DownloadManager downloadManager;
    private ArrayList<WallpapersModel> imagesArrayList;
    private View snackView;
    private DownloadManager.Request request;
    private static final int MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE = 123;

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
        imageDialog.setPositiveButton("Download", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm'.jpg'", Locale.ENGLISH);
                String fileName = sdf.format(new Date());

                String downloadFileUrl = imagesArrayList.get(position).getURL();
                downloadManager = (DownloadManager) contextGlobal.getSystemService(Context.DOWNLOAD_SERVICE);

                Uri uri = Uri.parse(downloadFileUrl);
                request = new DownloadManager.Request(uri);
                request.setTitle("Wallpaper Download");
                request.setDescription("in progress");
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,
                        "/AvengersWallpaperDownloads/" + fileName);
                request.setVisibleInDownloadsUi(true);
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

                if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(contextGlobal,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE);
                } else {

                    downloadManager.enqueue(request);
                    Toast.makeText(contextGlobal, "Downloading...", Toast.LENGTH_SHORT).show();
                }

            }
        });
        imageDialog.setNegativeButton("Set As HomeScreen", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
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

            }
        });
        imageDialog.setNeutralButton("Set As LockScreen", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
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

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.custom_fullimage_dialog, (ViewGroup) mView.findViewById(R.id.layout_root), false);
        ImageView image = layout.findViewById(R.id.fullimage);
        image.setImageDrawable(imageView.getDrawable());
        image.setMinimumHeight(height);
        image.setMinimumWidth(width);
        imageDialog.setView(layout);
        imageDialog.create();
        imageDialog.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    downloadManager.enqueue(request);
                    Toast.makeText(contextGlobal, "Downloading...", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(contextGlobal, "Create Directory permission was not granted", Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }

    }
}
