package com.plasto.dealerapp.retrofit.getLoginData.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Data {

    @SerializedName("area")
    @Expose
    private List<AreaItem> area;

    @SerializedName("city")
    @Expose
    private List<CityItem> city;

    @SerializedName("sub_category")
    @Expose
    private List<SubCategoryItem> subCategory;

    @SerializedName("state")
    @Expose
    private List<StateItem> state;

    @SerializedName("tashil")
    @Expose
    private List<TashilItem> tashil;

    @SerializedName("category")
    @Expose
    private List<CategoryItem> category;


    public List<AreaItem> getArea() {
        return area;
    }

    public void setArea(List<AreaItem> area) {
        this.area = area;
    }

    public List<TashilItem> getTashil() {
        return tashil;
    }

    public void setTashil(List<TashilItem> tashil) {
        this.tashil = tashil;
    }

    public void setCity(List<CityItem> city) {
        this.city = city;
    }

    public List<CityItem> getCity() {
        return city;
    }

    public void setSubCategory(List<SubCategoryItem> subCategory) {
        this.subCategory = subCategory;
    }

    public List<SubCategoryItem> getSubCategory() {
        return subCategory;
    }

    public void setState(List<StateItem> state) {
        this.state = state;
    }

    public List<StateItem> getState() {
        return state;
    }


    public void setCategory(List<CategoryItem> category) {
        this.category = category;
    }

    public List<CategoryItem> getCategory() {
        return category;
    }
}