package com.plasto.dealerapp.retrofit.categoryList.request.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data {

    @SerializedName("category")
    @Expose
    private List<CategoryItem> category;

    public void setCategory(List<CategoryItem> category) {
        this.category = category;
    }

    public List<CategoryItem> getCategory() {
        return category;
    }
}