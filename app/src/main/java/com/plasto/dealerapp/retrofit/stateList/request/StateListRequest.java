package com.plasto.dealerapp.retrofit.stateList.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StateListRequest {
    public StateListRequest(String imei, String id, String userToken) {
        this.imei = imei;
        this.id = id;
        this.userToken = userToken;
    }

    @SerializedName("imei")
    @Expose
    private String imei;

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("user_token")
    @Expose
    private String userToken;

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getImei() {
        return imei;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public String getUserToken() {
        return userToken;
    }
}