package com.alaukikapps.movtronix.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.alaukikapps.movtronix.staticclasses.App;

public class ConnectionChecker {
    public boolean checkInternet() {
        ConnectivityManager conMgr = (ConnectivityManager) App.context.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert conMgr != null;
        NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();
        return activeNetwork == null || !activeNetwork.isConnected();
    }
}
