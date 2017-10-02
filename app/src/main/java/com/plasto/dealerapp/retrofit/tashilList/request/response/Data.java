package com.plasto.dealerapp.retrofit.tashilList.request.response;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data{

	@SerializedName("tehsils")
	@Expose
	private List<TehsilsItem> tehsils;

	public void setTehsils(List<TehsilsItem> tehsils){
		this.tehsils = tehsils;
	}

	public List<TehsilsItem> getTehsils(){
		return tehsils;
	}
}