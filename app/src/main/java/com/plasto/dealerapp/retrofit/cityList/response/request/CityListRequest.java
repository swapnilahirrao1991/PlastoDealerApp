package com.plasto.dealerapp.retrofit.cityList.response.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CityListRequest {
    public CityListRequest(String imei, String id, String userToken, String stateId) {
        this.imei = imei;
        this.id = id;
        this.userToken = userToken;
        this.stateId = stateId;
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

    @SerializedName("state_id")
    @Expose
    private String stateId;

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

    public void setStateId(String stateId) {
        this.stateId = stateId;
    }

    public String getStateId() {
        return stateId;
    }
}