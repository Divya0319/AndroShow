package com.marvellous.avengersuniverse.activities;

import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.marvellous.avengersuniverse.R;
import com.marvellous.avengersuniverse.adapters.VideosAdapter;
import com.marvellous.avengersuniverse.common.AbstractBaseActivity;
import com.marvellous.avengersuniverse.models.VideosResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class VideosActivity extends AbstractBaseActivity {

    private ArrayList<VideosResponse> videoArrayList;
    private ProgressDialog mProgressDialog;
    private VideosAdapter videosAdapter;

    private Callback<List<VideosResponse>> videosResponseCallback = new Callback<List<VideosResponse>>() {
        @Override
        public void onResponse(Call<List<VideosResponse>> call, retrofit2.Response<List<VideosResponse>> response) {
            if (mProgressDialog != null && mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }
            if (response.isSuccessful()) {
                List<VideosResponse> vrl = response.body();
                videoArrayList.addAll(vrl);
                videosAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onFailure(Call<List<VideosResponse>> call, Throwable t) {
            mProgressDialog.dismiss();
            Log.d(getString(R.string.somethingWentWrong), t.getMessage());
        }
    };

    @Override
    public void onCreatingBase(Bundle savedInstanceState) {
        setContentView(R.layout.activity_videos_list);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        videoArrayList = new ArrayList<>();
        videosAdapter = new VideosAdapter(this, videoArrayList);
        RecyclerView recView_videos = findViewById(R.id.recView_videos);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recView_videos.setHasFixedSize(true);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle("Wait..");
        mProgressDialog.setMessage("Loading Videos");
        recView_videos.setLayoutManager(llm);
        recView_videos.setAdapter(videosAdapter);
        TextView tvVideoText = findViewById(R.id.VideoText);
        Typeface mycustomFont = Typeface.createFromAsset(getAssets(), "fonts/Exo-Medium.otf");

        tvVideoText.setTypeface(mycustomFont);
        callVideosAPI();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void callVideosAPI() {
        if (connectionChecker.checkInternet()) {
            Toast.makeText(getApplicationContext(), getString(R.string.checkInternetMessage), Toast.LENGTH_LONG).show();
        } else {
            mProgressDialog.show();
            apiIntegrationHelper.videosData(videosResponseCallback);
        }
    }
}
