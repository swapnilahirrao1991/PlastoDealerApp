package com.plasto.dealerapp.retrofit.dayendreport.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DayEndReportResponse {

    @SerializedName("data")
    @Expose
    private List<DataItem> data;

    @SerializedName("response")
    @Expose
    private boolean response;

    @SerializedName("message")
    @Expose
    private String message;

    public void setData(List<DataItem> data) {
        this.data = data;
    }

    public List<DataItem> getData() {
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