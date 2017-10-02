
package com.plasto.dealerapp.retrofit.pdfresponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("srno")
    @Expose
    private Integer srno;
    @SerializedName("DelaerName")
    @Expose
    private String delaerName;
    @SerializedName("DelaerMobile")
    @Expose
    private String delaerMobile;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("area_name")
    @Expose
    private String areaName;
    @SerializedName("lat_lng")
    @Expose
    private String latLng;
    @SerializedName("google_address")
    @Expose
    private String googleAddress;
    @SerializedName("desc_data")
    @Expose
    private String descData;

    public Integer getSrno() {
        return srno;
    }

    public void setSrno(Integer srno) {
        this.srno = srno;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getLatLng() {
        return latLng;
    }

    public void setLatLng(String latLng) {
        this.latLng = latLng;
    }

    public String getGoogleAddress() {
        return googleAddress;
    }

    public void setGoogleAddress(String googleAddress) {
        this.googleAddress = googleAddress;
    }

    public String getDescData() {
        return descData;
    }

    public void setDescData(String descData) {
        this.descData = descData;
    }

}
