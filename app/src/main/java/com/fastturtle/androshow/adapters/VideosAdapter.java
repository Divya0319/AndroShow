package com.fastturtle.androshow.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.fastturtle.androshow.R;
import com.fastturtle.androshow.activities.YouTubeVideoActivity;
import com.fastturtle.androshow.models.VideosResponse;

import java.util.ArrayList;

public class VideosAdapter extends RecyclerView.Adapter<VideosAdapter.ViewHolder> {
    private ArrayList<VideosResponse> VideosResponseArrayList;
    private Context context;

    public VideosAdapter(Context context, ArrayList<VideosResponse> VideosResponseArrayList) {
        this.context = context;
        this.VideosResponseArrayList = VideosResponseArrayList;
    }

    @NonNull
    @Override
    public VideosAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.videos_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final VideosAdapter.ViewHolder holder, int position) {
        holder.title.setText(VideosResponseArrayList.get(position).getTITLE());
        holder.duration.setText(VideosResponseArrayList.get(position).getDURATION());

        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.video_icon_def)
                .error(R.drawable.video_icon);
        Glide.with(context).load("https://i.ytimg.com/vi/"
                + VideosResponseArrayList.get(position).getVIDID() + "/mqdefault.jpg")
                .apply(options)
                .into(holder.videoThumb);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String vidId = VideosResponseArrayList.get(holder.getBindingAdapterPosition()).getVIDID();
                Intent youTubeIntent = new Intent(context, YouTubeVideoActivity.class);
                youTubeIntent.putExtra("videoId", vidId);
                context.startActivity(youTubeIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return VideosResponseArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView videoThumb;
        private TextView title;
        private TextView duration;
        private View mView;

        ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            videoThumb = mView.findViewById(R.id.videothumb);
            duration = mView.findViewById(R.id.Duration);
            title = mView.findViewById(R.id.Title);
        }
    }
}
