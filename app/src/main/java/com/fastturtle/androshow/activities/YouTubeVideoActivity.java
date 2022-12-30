package com.fastturtle.androshow.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import com.fastturtle.androshow.BuildConfig;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.fastturtle.androshow.R;

public class YouTubeVideoActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    private static final int RECOVERY_REQUEST = 1;
    private YouTubePlayerView youTubeView;
    Intent intent;
    String videoIdFromIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videos);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        intent = getIntent();
        videoIdFromIntent = intent.getStringExtra("videoId");
        youTubeView = findViewById(R.id.youtube_view);
        youTubeView.initialize(BuildConfig.YOUTUBE_API_KEY_PART_1 +
                BuildConfig.YOUTUBE_API_KEY_PART_2 +
                BuildConfig.YOUTUBE_API_KEY_PART_3 +
                BuildConfig.YOUTUBE_API_KEY_PART_4, this);

    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
//        if (!wasRestored) {
//            player.cueVideo(VideoIdBundle);
//        }
        player.loadVideo(videoIdFromIntent);
        player.play();
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_REQUEST).show();
        } else {
            String error = getString(R.string.player_error);
            Toast.makeText(this, error, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_REQUEST) {
            // Retry initialization if user performed a recovery action
            getYouTubePlayerProvider().initialize(BuildConfig.YOUTUBE_API_KEY_PART_1 +
                    BuildConfig.YOUTUBE_API_KEY_PART_2 +
                    BuildConfig.YOUTUBE_API_KEY_PART_3 +
                    BuildConfig.YOUTUBE_API_KEY_PART_4, this);
        }
    }

    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return youTubeView;
    }

}