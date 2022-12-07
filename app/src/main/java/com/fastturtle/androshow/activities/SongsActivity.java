package com.fastturtle.androshow.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.fastturtle.androshow.R;
import com.fastturtle.androshow.adapters.SongsAdapter;
import com.fastturtle.androshow.common.AbstractBaseActivity;
import com.fastturtle.androshow.models.DoctorSongsResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;

public class SongsActivity extends AbstractBaseActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {

    private ImageView imPlay;
    private ImageView imPause;
    private MediaPlayer mediaPlayer;
    private int mediaFileLengthInMilliseconds;
    private SongsAdapter songsAdapter;

    private SeekBar seekBar;
    private ArrayList<DoctorSongsResponse> docSongsList = new ArrayList<>();
    private ProgressDialog pDialog;
    private TextView tvElapsedTime, tvEndTime;
    private ListView listView_songs;
    private Handler handler;
    boolean itemClicked = false;
    Context activityContext;

    private String url, songTitle;

    private Callback<List<DoctorSongsResponse>> doctorSongResponseCallback = new Callback<List<DoctorSongsResponse>>() {
        @Override
        public void onResponse(Call<List<DoctorSongsResponse>> call, retrofit2.Response<List<DoctorSongsResponse>> response) {
            if (pDialog != null && pDialog.isShowing()) {
                pDialog.dismiss();
            }
            if (response.isSuccessful()) {
                List<DoctorSongsResponse> ldr = response.body();
                docSongsList.addAll(ldr);
                songsAdapter.notifyDataSetChanged();

            }
        }

        @Override
        public void onFailure(Call<List<DoctorSongsResponse>> call, Throwable t) {
            pDialog.dismiss();
            Log.d(getString(R.string.somethingWentWrong), t.getMessage());

        }
    };

    @Override
    public void onCreatingBase(Bundle savedInstanceState) {
        setContentView(R.layout.activity_songs);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        activityContext = this;
        handler = new Handler();
        initView();
        callDocSongsApi();
    }

    private void initView() {
        imPlay = findViewById(R.id.buttonPlay);
        imPause = findViewById(R.id.buttonPause);
        imPause.setVisibility(View.INVISIBLE);
        ImageView imStop = findViewById(R.id.buttonStop);
        seekBar = findViewById(R.id.seekBar);
        tvElapsedTime = findViewById(R.id.elapsedTime);
        tvEndTime = findViewById(R.id.endTime);
        songsAdapter = new SongsAdapter(this, docSongsList);
        listView_songs = findViewById(R.id.listView_songs);
        listView_songs.setAdapter(songsAdapter);
        TextView tvSongHeading = findViewById(R.id.songheading);
        tvElapsedTime.setText("--/--");
        tvEndTime.setText("--/--");

        Toolbar toolbar = findViewById(R.id.tool_bar);

        toolbar.setNavigationOnClickListener(view -> {
            onBackPressed();
        });

        pDialog = new ProgressDialog(this);
        pDialog.setTitle("Wait...");
        pDialog.setMessage("Loading Songs");

        Typeface myCustomFont = Typeface.createFromAsset(getAssets(), "fonts/Exo-Light.otf");
        tvSongHeading.setTypeface(myCustomFont);

        imPlay.setOnClickListener(this);
        imPause.setOnClickListener(this);
        imStop.setOnClickListener(this);
//        seekBar.getProgressDrawable().setColorFilter(Color.CYAN, PorterDuff.Mode.SRC_IN);
//        seekBar.getThumb().setColorFilter(Color.LTGRAY, PorterDuff.Mode.SRC_IN);

        seekBar.setOnSeekBarChangeListener(this);
    }

    private void primarySeekBarProgressUpdater() {
        seekBar.setProgress((int) (((float) mediaPlayer.getCurrentPosition() / mediaFileLengthInMilliseconds) * 100));
        tvElapsedTime.setText(getTimeString(mediaPlayer.getCurrentPosition()));
        if (mediaPlayer.isPlaying()) {
            Runnable notification = new Runnable() {
                @Override
                public void run() {
                    primarySeekBarProgressUpdater();
                }
            };
            handler.postDelayed(notification, 1000);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.buttonPlay) {
            if (mediaPlayer != null) {
                mediaPlayer.start();
                imPause.setVisibility(View.VISIBLE);
                imPlay.setVisibility(View.INVISIBLE);
                primarySeekBarProgressUpdater();
            }
        } else if (v.getId() == R.id.buttonPause) {
            mediaPlayer.pause();
            imPlay.setVisibility(View.VISIBLE);
            imPause.setVisibility(View.INVISIBLE);
            primarySeekBarProgressUpdater();
        } else if (v.getId() == R.id.buttonStop) {
            if (mediaPlayer != null || mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
                mediaPlayer.reset();
                seekBar.setProgress(0);
                seekBar.setSecondaryProgress(0);
                tvElapsedTime.setText("00:00");
                imPlay.setVisibility(View.VISIBLE);
                imPause.setVisibility(View.INVISIBLE);
                Log.d("Seekbar pos", "" + seekBar.getProgress());
            }
        } else if (v.getId() == R.id.buttonPlay && !itemClicked) {
            Toast.makeText(getApplicationContext(), "Please touch a song to start playing it.", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (mediaPlayer != null && fromUser) {
            int playPositionInMilliseconds = (mediaFileLengthInMilliseconds / 100) * seekBar.getProgress();
            mediaPlayer.seekTo(playPositionInMilliseconds);
            tvElapsedTime.setText(getTimeString(mediaPlayer.getCurrentPosition()));
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    public String getTimeString(long millis) {
        int mins = (int) ((millis % (1000 * 60 * 60)) / (1000 * 60));
        int secs = (int) (((millis % (1000 * 60 * 60)) % (1000 * 60)) / 1000);

        return String.format(Locale.getDefault(), "%02d", mins) + ":" +
                String.format(Locale.getDefault(), "%02d", secs);
    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {

        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.reset();
        }

        super.onBackPressed();
    }

    @Override
    protected void onStart() {
        super.onStart();
        listView_songs.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                listView_songs.setItemChecked(position, true);
                url = docSongsList.get(position).getURL(); // your URl
                songTitle = docSongsList.get(position).getTITLE();
                pDialog = ProgressDialog.show(activityContext, "Starting Song: ", songTitle);
                Log.d("Now Playing", songTitle);

                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                }
                mediaPlayer = new MediaPlayer();
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                try {
                    mediaPlayer.setDataSource(url);
                    mediaPlayer.prepareAsync();

                    itemClicked = true;

                } catch (IOException e) {
                    e.printStackTrace();
                }

                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        if (pDialog.isShowing()) {
                            pDialog.dismiss();
                        }
                        mp.start();
                        mediaFileLengthInMilliseconds = mp.getDuration();// gets the song length in milliseconds from URL
                        imPlay.setVisibility(View.INVISIBLE);
                        imPause.setVisibility(View.VISIBLE);
                        primarySeekBarProgressUpdater();
                        tvEndTime.setText(getTimeString(mediaFileLengthInMilliseconds));
                        mediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
                            @Override
                            public void onBufferingUpdate(MediaPlayer mp, int percent) {
                                seekBar.setSecondaryProgress(percent);
                                Log.i("Buffering", "" + percent);
                            }
                        });
                        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mp) {
                            }
                        });
                    }
                });
            }
        });
    }


    private void callDocSongsApi() {
        if (connectionChecker.checkInternet()) {
            Toast.makeText(getApplicationContext(), getString(R.string.checkInternetMessage), Toast.LENGTH_LONG).show();
        } else {
            pDialog.show();
            apiIntegrationHelper.doctorSongsData(doctorSongResponseCallback);
        }
    }
}
