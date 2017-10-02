package com.plasto.dealerapp.retrofit.submitVisit.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class SubmitVisitRequest {
    public SubmitVisitRequest(String dealerMobile, String imei, String id, String userToken, String lat, String jsonMemberLong, String dealerId, String desc) {
        this.dealerMobile = dealerMobile;
        this.imei = imei;
        this.id = id;
        this.userToken = userToken;
        this.lat = lat;
        this.desc = desc;
        this.jsonMemberLong = jsonMemberLong;
        this.dealerId = dealerId;
    }

    @SerializedName("desc")
    @Expose
    private String desc;
    @SerializedName("dealer_mobile")
    @Expose
    private String dealerMobile;

    @SerializedName("imei")
    @Expose
    private String imei;

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("user_token")
    @Expose
    private String userToken;

    @SerializedName("lat")
    @Expose
    private String lat;

    @SerializedName("long")
    @Expose
    private String jsonMemberLong;

    @SerializedName("dealer_id")
    @Expose
    private String dealerId;

    public void setDealerMobile(String dealerMobile) {
        this.dealerMobile = dealerMobile;
    }

    public String getDealerMobile() {
        return dealerMobile;
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

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLat() {
        return lat;
    }

    public void setJsonMemberLong(String jsonMemberLong) {
        this.jsonMemberLong = jsonMemberLong;
    }

    public String getJsonMemberLong() {
        return jsonMemberLong;
    }

    public void setDealerId(String dealerId) {
        this.dealerId = dealerId;
    }

    public String getDealerId() {
        return dealerId;
    }
}