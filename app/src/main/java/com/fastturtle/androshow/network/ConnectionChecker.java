package com.fastturtle.androshow.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.fastturtle.androshow.staticclasses.App;

public class ConnectionChecker {
    public boolean checkInternet() {
        ConnectivityManager conMgr = (ConnectivityManager) App.context.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert conMgr != null;
        NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();
        return activeNetwork == null || !activeNetwork.isConnected();
    }
}
