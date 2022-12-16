package com.fastturtle.androshow.adapters;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.fastturtle.androshow.R;
import com.fastturtle.androshow.activities.RingTonesActivity;
import com.fastturtle.androshow.models.RingtonesResponse;
import com.fastturtle.androshow.staticclasses.Config;

import java.util.ArrayList;

public class RingTonesAdapter extends RecyclerView.Adapter<RingTonesAdapter.ViewHolder> {

    private Activity context;
    private ArrayList<RingtonesResponse> ringtoneList;
    private String downloadFileUrl = "";
    private int pos;

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
                pos = holder.getBindingAdapterPosition();
                downloadFileUrl = ringtoneList.get(holder.getBindingAdapterPosition()).getURL();

                if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(context,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, Config.MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE);
                } else {
                    ((RingTonesActivity) context).startFileDownload(downloadFileUrl, ringtoneList.get(holder.getBindingAdapterPosition()).getTITLE());
                    Toast.makeText(context, "Download started...", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return ringtoneList.size();
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case Config.MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    ((RingTonesActivity) context).startFileDownload(downloadFileUrl, ringtoneList.get(pos).getTITLE());
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
