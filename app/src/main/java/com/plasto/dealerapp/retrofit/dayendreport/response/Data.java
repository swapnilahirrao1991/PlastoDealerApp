
package com.plasto.dealerapp.retrofit.dayendreport.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("DelaerName")
    @Expose
    private String delaerName;
    @SerializedName("DelaerMobile")
    @Expose
    private String delaerMobile;
    @SerializedName("city_name")
    @Expose
    private String cityName;
    @SerializedName("DelaerAddress")
    @Expose
    private String delaerAddress;

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

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getDelaerAddress() {
        return delaerAddress;
    }

    public void setDelaerAddress(String delaerAddress) {
        this.delaerAddress = delaerAddress;
    }

}
