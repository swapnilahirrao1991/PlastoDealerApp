package com.plasto.dealerapp.retrofit.login.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;


public class Data extends RealmObject {

    @SerializedName("is_active")
    @Expose
    private String isActive;

    @SerializedName("tashil_id")
    @Expose
    private String tashilId;

    @SerializedName("ngp")
    @Expose
    private String ngp;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("mobile")
    @Expose
    private String mobile;

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("user_token")
    @Expose
    private String userToken;

    public String getProfileImage() {
        return profileImage;
    }

    @SerializedName("dp")
    @Expose
    private String profileImage;
    @SerializedName("created_date")
    @Expose
    private String createdDate;

    @SerializedName("state_id")
    @Expose
    private String stateId;

    @SerializedName("area_id")
    @Expose
    private String areaId;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("city_id")
    @Expose
    private String cityId;

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setTashilId(String tashilId) {
        this.tashilId = tashilId;
    }

    public String getTashilId() {
        return tashilId;
    }

    public void setNgp(String ngp) {
        this.ngp = ngp;
    }

    public String getNgp() {
        return ngp;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMobile() {
        return mobile;
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

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setStateId(String stateId) {
        this.stateId = stateId;
    }

    public String getStateId() {
        return stateId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCityId() {
        return cityId;
    }
}