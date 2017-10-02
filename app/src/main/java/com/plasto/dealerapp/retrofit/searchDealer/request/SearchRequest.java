package com.plasto.dealerapp.retrofit.searchDealer.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class SearchRequest {
    public SearchRequest(String imei, String id, String userToken, String stateId, String cityId, String tashilId) {
        this.tashilId = tashilId;
        this.imei = imei;
        this.id = id;
        this.userToken = userToken;
        this.stateId = stateId;
        this.cityId = cityId;
    }

    @SerializedName("tashil_id")
    @Expose
    private String tashilId;

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

    @SerializedName("city_id")
    @Expose
    private String cityId;

    public void setTashilId(String tashilId) {
        this.tashilId = tashilId;
    }

    public String getTashilId() {
        return tashilId;
    }

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

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCityId() {
        return cityId;
    }
}