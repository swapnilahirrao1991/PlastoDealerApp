
package com.plasto.dealerapp.retrofit.dayendreport.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("DelaerName")
    @Expose
    private String delaerName;
    @SerializedName("DelaerMobile")
    @Expose
    private String delaerMobile;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("tashilName")
    @Expose
    private String tashilName;
    @SerializedName("desc_data")
    @Expose
    private String descData;

    public String getDelaerName() {
        return delaerName;
    }

    public void setDelaerName(String delaerName) {
        this.delaerName = delaerName;
    }

    public String getDelaerMobile() {
        return delaerMobile;
    }

    public void setDelaerMobile(String delaerMobile) {
        this.delaerMobile = delaerMobile;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTashilName() {
        return tashilName;
    }

    public void setTashilName(String tashilName) {
        this.tashilName = tashilName;
    }

    public String getDescData() {
        return descData;
    }

    public void setDescData(String descData) {
        this.descData = descData;
    }

}
