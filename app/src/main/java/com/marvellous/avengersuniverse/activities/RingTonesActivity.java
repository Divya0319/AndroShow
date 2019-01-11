package com.marvellous.avengersuniverse.activities;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.marvellous.avengersuniverse.R;
import com.marvellous.avengersuniverse.adapters.RingTonesAdapter;
import com.marvellous.avengersuniverse.common.AbstractBaseActivity;
import com.marvellous.avengersuniverse.models.RingtonesResponse;
import com.marvellous.avengersuniverse.network.APIIntegrationHelper;
import com.marvellous.avengersuniverse.network.ConnectionChecker;

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

    private Callback<List<RingtonesResponse>> ringtonesCallback = new Callback<List<com.marvellous.avengersuniverse.models.RingtonesResponse>>() {
        @Override
        public void onResponse(Call<List<com.marvellous.avengersuniverse.models.RingtonesResponse>> call, retrofit2.Response<List<com.marvellous.avengersuniverse.models.RingtonesResponse>> response) {
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
        public void onFailure(Call<List<com.marvellous.avengersuniverse.models.RingtonesResponse>> call, Throwable t) {
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
