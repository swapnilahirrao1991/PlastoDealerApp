package com.plasto.dealerapp.retrofit.addDealer.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddDealerResponse {

    @SerializedName("response")
    @Expose
    private boolean response;

    @SerializedName("message")
    @Expose
    private String message;

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