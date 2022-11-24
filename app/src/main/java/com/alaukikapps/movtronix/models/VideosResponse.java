package com.alaukikapps.movtronix.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VideosResponse {

    @SerializedName("TITLE")
    @Expose
    private String tITLE;
    @SerializedName("DURATION")
    @Expose
    private String dURATION;
    @SerializedName("VIDID")
    @Expose
    private String vIDID;

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

    public String getVIDID() {
        return vIDID;
    }

    public void setVIDID(String vIDID) {
        this.vIDID = vIDID;
    }

}