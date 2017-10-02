package com.plasto.dealerapp.retrofit.login.request;

import com.google.gson.annotations.Expose;


import com.google.gson.annotations.SerializedName;


public class LoginRequest {

    public LoginRequest(String password, String imei, String email) {
        this.password = password;
        this.imei = imei;
        this.email = email;
    }

    @SerializedName("password")

    @Expose
    private String password;

    @SerializedName("imei")
    @Expose
    private String imei;

    @SerializedName("email")
    @Expose
    private String email;

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getImei() {
        return imei;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}