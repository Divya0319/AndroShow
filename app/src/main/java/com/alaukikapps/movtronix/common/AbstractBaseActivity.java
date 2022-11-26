package com.alaukikapps.movtronix.common;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alaukikapps.movtronix.network.APIIntegrationHelper;
import com.alaukikapps.movtronix.network.ConnectionChecker;

public abstract class AbstractBaseActivity extends AppCompatActivity {
    protected APIIntegrationHelper apiIntegrationHelper;
    protected ConnectionChecker connectionChecker;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        apiIntegrationHelper = APIIntegrationHelper.getsIntance();
        connectionChecker = new ConnectionChecker();
        onCreatingBase(savedInstanceState);

    }

    protected abstract void onCreatingBase(Bundle savedInstanceState);
}
