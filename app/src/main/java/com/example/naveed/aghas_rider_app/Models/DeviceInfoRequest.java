package com.example.naveed.aghas_rider_app.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeviceInfoRequest {

    @SerializedName("Date")
    @Expose
    private String date;
    @SerializedName("FCMToken")
    @Expose
    private String fCMToken;
    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("AppType")
    @Expose
    private Integer appType;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFCMToken() {
        return fCMToken;
    }

    public void setFCMToken(String fCMToken) {
        this.fCMToken = fCMToken;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAppType() {
        return appType;
    }

    public void setAppType(Integer appType) {
        this.appType = appType;
    }

}
