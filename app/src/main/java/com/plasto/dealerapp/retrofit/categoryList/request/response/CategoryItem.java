package com.plasto.dealerapp.retrofit.categoryList.request.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CategoryItem{

	@SerializedName("id")
	@Expose
	private String id;

	@SerializedName("category")
	@Expose
	private String category;

	@SerializedName("status")
	@Expose
	private String status;

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setCategory(String category){
		this.category = category;
	}

	public String getCategory(){
		return category;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}
}