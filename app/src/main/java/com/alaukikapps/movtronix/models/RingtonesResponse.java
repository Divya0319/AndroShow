package com.alaukikapps.movtronix.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RingtonesResponse {

    @SerializedName("TITLE")
    @Expose
    private String tITLE;
    @SerializedName("DURATION")
    @Expose
    private String dURATION;
    @SerializedName("URL")
    @Expose
    private String uRL;

    public String getTITLE() {
        return tITLE;
    }

    public void setTITLE(String tITLE) {
        this.tITLE = tITLE;
    }

    public String getDURATION() {
        return dURATION;
    }

    public void setDURATION(String dURATION) {
        this.dURATION = dURATION;
    }

    public String getURL() {
        return uRL;
    }

    public void setURL(String uRL) {
        this.uRL = uRL;
    }

}