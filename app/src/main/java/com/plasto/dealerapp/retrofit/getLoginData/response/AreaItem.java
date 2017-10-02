package com.plasto.dealerapp.retrofit.getLoginData.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;


public class AreaItem extends RealmObject {

    @SerializedName("area_name")
    @Expose
    private String areaName;

    @SerializedName("area_id")
    @Expose
    private String areaId;

    @SerializedName("city_id")
    @Expose
    private String cityId;

    @SerializedName("status")
    @Expose
    private String status;

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}