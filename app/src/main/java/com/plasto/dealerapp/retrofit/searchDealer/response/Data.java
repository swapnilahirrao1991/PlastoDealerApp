package com.plasto.dealerapp.retrofit.searchDealer.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Data {

    @SerializedName("dealers")
    @Expose
    private List<DealersItem> dealers;

    public void setDealers(List<DealersItem> dealers) {
        this.dealers = dealers;
    }

    public List<DealersItem> getDealers() {
        return dealers;
    }
}