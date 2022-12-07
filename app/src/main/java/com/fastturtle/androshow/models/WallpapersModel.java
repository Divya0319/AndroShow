package com.fastturtle.androshow.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WallpapersModel {

    @SerializedName("TITLE")
    @Expose
    private String title;

    @SerializedName("URL")
    @Expose
    private String uRL;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getURL() {
        return uRL;
    }

    public void setURL(String uRL) {
        this.uRL = uRL;
    }
}
