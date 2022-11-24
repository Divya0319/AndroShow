package com.alaukikapps.movtronix.activities;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.graphics.Typeface;
import android.os.Bundle;

import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alaukikapps.movtronix.R;
import com.alaukikapps.movtronix.adapters.RingTonesAdapter;
import com.alaukikapps.movtronix.common.AbstractBaseActivity;
import com.alaukikapps.movtronix.models.RingtonesResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class RingTonesActivity extends AbstractBaseActivity {

    private DownloadManager downloadManager;
    private RingTonesAdapter ringtonesAdapter;
    private BroadcastReceiver receiverDownloadComplete;
    private BroadcastReceiver receiverNotificationClicked;
    private RingtonesResponse RingtonesResponse;
    private ArrayList<RingtonesResponse> ringtoneList;
    private ProgressDialog mProgressDialog;

    // JSON Node names
    private static final String TAG_TITLE = "TITLE";
    private static final String TAG_DURATION = "DURATION";
    private static final String TAG_URL = "URL";

    private Callback<List<RingtonesResponse>> ringtonesCallback = new Callback<List<com.alaukikapps.movtronix.models.RingtonesResponse>>() {
        @Override
        public void onResponse(Call<List<com.alaukikapps.movtronix.models.RingtonesResponse>> call, retrofit2.Response<List<com.alaukikapps.movtronix.models.RingtonesResponse>> response) {
            if (mProgressDialog.isShowing() && mProgressDialog != null) {
                mProgressDialog.dismiss();
            }
            if (response.isSuccessful()) {
                List<RingtonesResponse> rrl = response.body();
                ringtoneList.addAll(rrl);
                ringtonesAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onFailure(Call<List<com.alaukikapps.movtronix.models.RingtonesResponse>> call, Throwable t) {
            mProgressDialog.dismiss();
            Log.d(getString(R.string.somethingWentWrong), t.getMessage());

        }
    };

    @Override
    public void onCreatingBase(Bundle savedInstanceState) {
        setContentView(R.layout.activity_ringtones);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        TextView tvRingtonesText = findViewById(R.id.RingtonesText);
        Typeface myCustomFont = Typeface.createFromAsset(getAssets(), "fonts/Exo-Medium.otf");

        Toolbar toolbar = findViewById(R.id.tool_bar);

        toolbar.setNavigationOnClickListener(view -> {
            onBackPressed();
        });

        ringtoneList = new ArrayList<>();
        ringtonesAdapter = new RingTonesAdapter(this, ringtoneList);
        RecyclerView recView_ringtone = findViewById(R.id.recView_ringtones);
        recView_ringtone.setHasFixedSize(true);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.divider_recview));
        recView_ringtone.setLayoutManager(new LinearLayoutManager(this));
        recView_ringtone.setItemAnimator(new DefaultItemAnimator());
        recView_ringtone.addItemDecoration(itemDecoration);

        recView_ringtone.setAdapter(ringtonesAdapter);

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle("Wait...");
        mProgressDialog.setMessage("Loading Ringtones");

        tvRingtonesText.setTypeface(myCustomFont);

        callRingtonesAPI();

    }

    private void callRingtonesAPI() {
        if (connectionChecker.checkInternet()) {
            Toast.makeText(getApplicationContext(), getString(R.string.checkInternetMessage), Toast.LENGTH_LONG).show();
        } else {
            mProgressDialog.show();
            apiIntegrationHelper.ringtonesData(ringtonesCallback);
        }
    }

}
