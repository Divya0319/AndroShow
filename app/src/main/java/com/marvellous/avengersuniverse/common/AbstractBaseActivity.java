package com.marvellous.avengersuniverse.common;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.marvellous.avengersuniverse.network.APIIntegrationHelper;
import com.marvellous.avengersuniverse.network.ConnectionChecker;

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
