package com.plasto.dealerapp.retrofit.register.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegisterRequest {
    public RegisterRequest(String name, String mobile, String descss, String imei, String email) {
        this.name = name;
        this.mobile = mobile;
        this.descss = descss;
        this.imei = imei;
        this.email = email;
    }

    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("mobile")
    @Expose
    private String mobile;

    @SerializedName("descss")
    @Expose
    private String descss;

    @SerializedName("imei")
    @Expose
    private String imei;

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

    public void setDescss(String descss) {
        this.descss = descss;
    }

    public String getDescss() {
        return descss;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getImei() {
        return imei;
    }
}