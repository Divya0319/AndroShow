package com.alaukikapps.movtronix.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WallpapersModel {

    @SerializedName("URL")
    @Expose
    private String uRL;

    public String getURL() {
        return uRL;
    }

    public void setURL(String uRL) {
        this.uRL = uRL;
    }
}
