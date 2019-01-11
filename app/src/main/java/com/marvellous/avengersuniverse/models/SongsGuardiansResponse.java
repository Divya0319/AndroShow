package com.marvellous.avengersuniverse.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SongsGuardiansResponse {

    @SerializedName("TITLE")
    @Expose
    private String tITLE;
    @SerializedName("SINGER")
    @Expose
    private String sINGER;
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

    public String getSINGER() {
        return sINGER;
    }

    public void setSINGER(String sINGER) {
        this.sINGER = sINGER;
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