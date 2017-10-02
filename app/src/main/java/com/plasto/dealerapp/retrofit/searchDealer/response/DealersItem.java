package com.plasto.dealerapp.retrofit.searchDealer.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class DealersItem {

    @SerializedName("other_mobile2")
    @Expose
    private String otherMobile2;

    @SerializedName("other_mobile4")
    @Expose
    private String otherMobile4;

    @SerializedName("other_mobile3")
    @Expose
    private String otherMobile3;

    @SerializedName("address")
    @Expose
    private String address;

    @SerializedName("tashil_id")
    @Expose
    private String tashilId;

    @SerializedName("mobile")
    @Expose
    private String mobile;

    @SerializedName("dealr_id")
    @Expose
    private String dealrId;

    @SerializedName("other_mobile")
    @Expose
    private String otherMobile;

    @SerializedName("area_id")
    @Expose
    private Object areaId;


    @SerializedName("area_name")
    @Expose
    private String area_name;

    public String getArea_name() {
        return area_name;
    }

    public void setArea_name(String area_name) {
        this.area_name = area_name;
    }

    @SerializedName("category_id")
    @Expose
    private String categoryId;

    @SerializedName("sub_category_id")
    @Expose
    private String subCategoryId;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("state_id")
    @Expose
    private String stateId;

    @SerializedName("created_date")
    @Expose
    private String createdDate;

    @SerializedName("addby")
    @Expose
    private String addby;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("city_id")
    @Expose
    private String cityId;

    @SerializedName("status")
    @Expose
    private String status;

    public void setOtherMobile2(String otherMobile2) {
        this.otherMobile2 = otherMobile2;
    }

    public String getOtherMobile2() {
        return otherMobile2;
    }

    public void setOtherMobile4(String otherMobile4) {
        this.otherMobile4 = otherMobile4;
    }

    public String getOtherMobile4() {
        return otherMobile4;
    }

    public void setOtherMobile3(String otherMobile3) {
        this.otherMobile3 = otherMobile3;
    }

    public String getOtherMobile3() {
        return otherMobile3;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setTashilId(String tashilId) {
        this.tashilId = tashilId;
    }

    public String getTashilId() {
        return tashilId;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMobile() {
        return mobile;
    }

    public void setDealrId(String dealrId) {
        this.dealrId = dealrId;
    }

    public String getDealrId() {
        return dealrId;
    }

    public void setOtherMobile(String otherMobile) {
        this.otherMobile = otherMobile;
    }

    public String getOtherMobile() {
        return otherMobile;
    }

    public void setAreaId(Object areaId) {
        this.areaId = areaId;
    }

    public Object getAreaId() {
        return areaId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setSubCategoryId(String subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public String getSubCategoryId() {
        return subCategoryId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setStateId(String stateId) {
        this.stateId = stateId;
    }

    public String getStateId() {
        return stateId;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setAddby(String addby) {
        this.addby = addby;
    }

    public String getAddby() {
        return addby;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
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