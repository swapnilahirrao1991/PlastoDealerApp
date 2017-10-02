package com.plasto.dealerapp.retrofit.submitVisit.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class SubmitVisitResponse {

    @SerializedName("data")
    @Expose
    private String data;

    @SerializedName("response")
    @Expose
    private boolean response;

    @SerializedName("message")
    @Expose
    private String message;

    public void setData(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
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