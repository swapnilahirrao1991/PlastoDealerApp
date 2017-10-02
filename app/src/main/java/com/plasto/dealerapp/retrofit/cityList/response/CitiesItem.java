package com.plasto.dealerapp.retrofit.cityList.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CitiesItem {

    @SerializedName("city_name")
    @Expose
    private String cityName;

    @SerializedName("state_id")
    @Expose
    private String stateId;

    @SerializedName("city_id")
    @Expose
    private String cityId;

    @SerializedName("status")
    @Expose
    private String status;

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setStateId(String stateId) {
        this.stateId = stateId;
    }

    public String getStateId() {
        return stateId;
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