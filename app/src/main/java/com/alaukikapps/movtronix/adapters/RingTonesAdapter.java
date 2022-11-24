package com.alaukikapps.movtronix.adapters;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alaukikapps.movtronix.R;
import com.alaukikapps.movtronix.models.RingtonesResponse;

import java.util.ArrayList;

public class RingTonesAdapter extends RecyclerView.Adapter<RingTonesAdapter.ViewHolder> implements ActivityCompat.OnRequestPermissionsResultCallback {

    private DownloadManager downloadManager;
    private Activity context;
    private ArrayList<RingtonesResponse> ringtoneList;
    private DownloadManager.Request request;
    private static final int MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE = 123;

    public RingTonesAdapter(Activity context, ArrayList<RingtonesResponse> RingtonesResponseArrayList) {
        this.context = context;
        this.ringtoneList = RingtonesResponseArrayList;
    }

    @NonNull
    @Override
    public RingTonesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.ringtone_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RingTonesAdapter.ViewHolder holder, int position) {
        holder.title.setText(ringtoneList.get(position).getTITLE());
        holder.duration.setText("Duration: " + ringtoneList.get(position).getDURATION() + " mins");
        holder.download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String downloadFileUrl = ringtoneList.get(holder.getAdapterPosition()).getURL();
                downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);

                Uri uri = Uri.parse(downloadFileUrl);
                request = new DownloadManager.Request(uri);
                request.setTitle("Ringtone Download");
                request.setDescription(ringtoneList.get(holder.getAdapterPosition()).getTITLE());
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,
                        "/AvengersRingtoneDownloads/" + ringtoneList.get(holder.getAdapterPosition()).getTITLE() + ".mp3");
                request.setVisibleInDownloadsUi(true);
                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

                if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(context,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE);
                } else {

                    downloadManager.enqueue(request);
                    Toast.makeText(context, "Downloading...", Toast.LENGTH_SHORT).show();

                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return ringtoneList.size();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    downloadManager.enqueue(request);
                    Toast.makeText(context, "Downloading...", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(context, "Create Directory permission was not granted", Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }

    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title, duration;
        private Button download;

        ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.ringTones_title);
            duration = itemView.findViewById(R.id.ringTones_duration);
            download = itemView.findViewById(R.id.bt_download);
        }

    }
}
