package com.plasto.dealerapp.retrofit.tashilList.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TashilListRequest {
    public TashilListRequest(String imei, String id, String userToken, String cityId) {
        this.imei = imei;
        this.id = id;
        this.userToken = userToken;
        this.cityId = cityId;
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

    @SerializedName("city_id")
    @Expose
    private String cityId;

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

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCityId() {
        return cityId;
    }
}