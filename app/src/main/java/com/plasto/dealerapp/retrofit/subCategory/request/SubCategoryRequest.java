package com.plasto.dealerapp.retrofit.subCategory.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubCategoryRequest {
    public SubCategoryRequest(String imei, String cateId, String id, String userToken) {
        this.imei = imei;
        this.cateId = cateId;
        this.id = id;
        this.userToken = userToken;
    }

    @SerializedName("imei")
    @Expose
    private String imei;

    @SerializedName("cate_id")
    @Expose
    private String cateId;

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

    public void setCateId(String cateId) {
        this.cateId = cateId;
    }

    public String getCateId() {
        return cateId;
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