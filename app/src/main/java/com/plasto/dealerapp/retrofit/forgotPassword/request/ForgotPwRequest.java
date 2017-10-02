package com.plasto.dealerapp.retrofit.forgotPassword.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ForgotPwRequest {

    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("imei")
    @Expose
    private String imei;

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public ForgotPwRequest(String email, String imei) {
        this.email = email;
        this.imei = imei;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}