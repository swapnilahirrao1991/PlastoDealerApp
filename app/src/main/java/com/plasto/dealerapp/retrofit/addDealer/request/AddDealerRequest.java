package com.plasto.dealerapp.retrofit.addDealer.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class AddDealerRequest {
    public AddDealerRequest(String otherMobile2, String otherMobile4, String otherMobile3, String address, String tashilId, String mobile, String otherMobile, String categoryId, String subCategoryId, String name, String imei, String id, String userToken, String stateId, String email, String cityId, String area) {
        this.otherMobile2 = otherMobile2;
        this.otherMobile4 = otherMobile4;
        this.otherMobile3 = otherMobile3;
        this.address = address;
        this.tashilId = tashilId;
        this.mobile = mobile;
        this.otherMobile = otherMobile;
        this.categoryId = categoryId;
        this.subCategoryId = subCategoryId;
        this.name = name;
        this.imei = imei;
        this.id = id;
        this.userToken = userToken;
        this.stateId = stateId;
        this.email = email;
        this.cityId = cityId;
        this.area = area;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    @SerializedName("area_id")
    @Expose
    private String area;

    @SerializedName("other_mobile2")
    @Expose
    private String otherMobile2;

    @SerializedName("other_mobile4")
    @Expose
    private String otherMobile4;

    @SerializedName("other_mobile3")
    @Expose
    private String otherMobile3;

    @SerializedName("address")
    @Expose
    private String address;

    @SerializedName("tashil_id")
    @Expose
    private String tashilId;

    @SerializedName("mobile")
    @Expose
    private String mobile;

    @SerializedName("other_mobile")
    @Expose
    private String otherMobile;

    @SerializedName("category_id")
    @Expose
    private String categoryId;

    @SerializedName("sub_category_id")
    @Expose
    private String subCategoryId;

    @SerializedName("name")
    @Expose
    private String name;

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

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("city_id")
    @Expose
    private String cityId;

    public void setOtherMobile2(String otherMobile2) {
        this.otherMobile2 = otherMobile2;
    }

    public String getOtherMobile2() {
        return otherMobile2;
    }

    public void setOtherMobile4(String otherMobile4) {
        this.otherMobile4 = otherMobile4;
    }

    public String getOtherMobile4() {
        return otherMobile4;
    }

    public void setOtherMobile3(String otherMobile3) {
        this.otherMobile3 = otherMobile3;
    }

    public String getOtherMobile3() {
        return otherMobile3;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setTashilId(String tashilId) {
        this.tashilId = tashilId;
    }

    public String getTashilId() {
        return tashilId;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMobile() {
        return mobile;
    }

    public void setOtherMobile(String otherMobile) {
        this.otherMobile = otherMobile;
    }

    public String getOtherMobile() {
        return otherMobile;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setSubCategoryId(String subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public String getSubCategoryId() {
        return subCategoryId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
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

    @Override
    public String toString() {
        return "AddDealerRequest{" +
                "otherMobile2='" + otherMobile2 + '\'' +
                ", otherMobile4='" + otherMobile4 + '\'' +
                ", otherMobile3='" + otherMobile3 + '\'' +
                ", address='" + address + '\'' +
                ", tashilId='" + tashilId + '\'' +
                ", mobile='" + mobile + '\'' +
                ", otherMobile='" + otherMobile + '\'' +
                ", categoryId='" + categoryId + '\'' +
                ", subCategoryId='" + subCategoryId + '\'' +
                ", name='" + name + '\'' +
                ", imei='" + imei + '\'' +
                ", id='" + id + '\'' +
                ", userToken='" + userToken + '\'' +
                ", stateId='" + stateId + '\'' +
                ", email='" + email + '\'' +
                ", cityId='" + cityId + '\'' +
                '}';
    }
}