package com.plasto.dealerapp.retrofit.editdealer.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EditDealerRequest {

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

    @SerializedName("mobile")
    @Expose
    private String mobile;

    @SerializedName("imei")
    @Expose
    private String imei;

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("user_token")
    @Expose
    private String userToken;

    @SerializedName("other_mobile")
    @Expose
    private String otherMobile;

    @SerializedName("dealr_id")
    @Expose
    private String dealrId;

    @SerializedName("dealer_id")
    @Expose
    private String dealerId;


    @SerializedName("area")
    @Expose
    private String area;



    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

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

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMobile() {
        return mobile;
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

    public void setOtherMobile(String otherMobile) {
        this.otherMobile = otherMobile;
    }

    public String getOtherMobile() {
        return otherMobile;
    }

    public void setDealrId(String dealrId) {
        this.dealrId = dealrId;
    }

    public String getDealrId() {
        return dealrId;
    }

    public void setDealerId(String dealerId) {
        this.dealerId = dealerId;
    }

    public String getDealerId() {
        return dealerId;
    }
}