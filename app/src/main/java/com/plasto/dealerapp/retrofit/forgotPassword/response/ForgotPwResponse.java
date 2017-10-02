package com.plasto.dealerapp.retrofit.forgotPassword.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ForgotPwResponse {

    @SerializedName("returndata")
    @Expose
    private String returndata;

    @SerializedName("response")
    @Expose
    private boolean response;

    @SerializedName("message")
    @Expose
    private String message;

    public void setReturndata(String returndata) {
        this.returndata = returndata;
    }

    public String getReturndata() {
        return returndata;
    }

    public void setResponse(boolean response) {
        this.response = response;
    }

    public boolean isResponse() {
        return response;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}