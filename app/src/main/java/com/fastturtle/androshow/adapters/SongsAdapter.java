package com.fastturtle.androshow.adapters;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import androidx.annotation.NonNull;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;

import com.fastturtle.androshow.R;
import com.fastturtle.androshow.models.DoctorSongsResponse;

import java.util.ArrayList;

public class SongsAdapter extends ArrayAdapter<DoctorSongsResponse> {

    private final Activity context;
    private ArrayList<DoctorSongsResponse> songList;

    public SongsAdapter(Activity context, ArrayList<DoctorSongsResponse> songList) {
        super(context, R.layout.songs_list_item);
        this.context = context;
        this.songList = songList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.songs_list_item, parent, false);
            holder = new ViewHolder();
            holder.tvChecked = convertView.findViewById(R.id.tvChecked);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String title = songList.get(position).getTITLE();
        String singer = "\nSinger:" + songList.get(position).getSINGER();
        String duration = "\nDuration:" + songList.get(position).getDURATION();

        SpannableStringBuilder builder = new SpannableStringBuilder();
        Spannable span = new SpannableString(title);
        span.setSpan(new RelativeSizeSpan(1.0f), 0, title.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        span.setSpan(new StyleSpan(Typeface.BOLD), 0, title.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        span.setSpan(new ForegroundColorSpan(Color.rgb(16, 96, 122)), 0, title.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.append(span);

        SpannableString darkBlueSpannable = new SpannableString(singer);
        darkBlueSpannable.setSpan(new ForegroundColorSpan(Color.rgb(0, 0, 30)), singer.indexOf(singer), singer.length(), 0);
        builder.append(darkBlueSpannable);

        SpannableString blueSpannable = new SpannableString(duration);
        blueSpannable.setSpan(new ForegroundColorSpan(Color.rgb(23, 163, 209)), duration.indexOf(duration), duration.length(), 0);
        builder.append(blueSpannable);

        holder.tvChecked.setText(builder);

        return convertView;
    }

    @Override
    public int getCount() {
        return songList.size();
    }

    private static class ViewHolder {
        private CheckedTextView tvChecked;
    }
}
