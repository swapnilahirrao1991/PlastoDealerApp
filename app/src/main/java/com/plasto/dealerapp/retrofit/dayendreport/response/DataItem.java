package com.plasto.dealerapp.retrofit.dayendreport.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataItem {
    public String getSr_no() {
        return sr_no;
    }

    public void setSr_no(String sr_no) {
        this.sr_no = sr_no;
    }

    @SerializedName("sr_no")
    @Expose

    private String sr_no;
    @SerializedName("DelaerMobile")
    @Expose
    private String delaerMobile;

    @SerializedName("DelaerAddress")
    @Expose
    private String delaerAddress;

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("DelaerName")
    @Expose
    private String delaerName;

    @SerializedName("dealer_id")
    @Expose
    private String dealerId;

    @SerializedName("time")
    @Expose
    private String time;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @SerializedName("date")
    @Expose
    private String date;

    public void setDelaerMobile(String delaerMobile) {
        this.delaerMobile = delaerMobile;
    }

    public String getDelaerMobile() {
        return delaerMobile;
    }

    public void setDelaerAddress(String delaerAddress) {
        this.delaerAddress = delaerAddress;
    }

    public String getDelaerAddress() {
        return delaerAddress;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setDelaerName(String delaerName) {
        this.delaerName = delaerName;
    }

    public String getDelaerName() {
        return delaerName;
    }

    public void setDealerId(String dealerId) {
        this.dealerId = dealerId;
    }

    public String getDealerId() {
        return dealerId;
    }
}