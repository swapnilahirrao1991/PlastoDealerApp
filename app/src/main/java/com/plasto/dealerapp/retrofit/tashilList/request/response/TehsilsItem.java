package com.plasto.dealerapp.retrofit.tashilList.request.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TehsilsItem{

	@SerializedName("tehsil_id")
	@Expose
	private String tehsilId;

	@SerializedName("cities_id")
	@Expose
	private String citiesId;

	@SerializedName("tehsil_name")
	@Expose
	private String tehsilName;

	@SerializedName("status")
	@Expose
	private String status;

	public void setTehsilId(String tehsilId){
		this.tehsilId = tehsilId;
	}

	public String getTehsilId(){
		return tehsilId;
	}

	public void setCitiesId(String citiesId){
		this.citiesId = citiesId;
	}

	public String getCitiesId(){
		return citiesId;
	}

	public void setTehsilName(String tehsilName){
		this.tehsilName = tehsilName;
	}

	public String getTehsilName(){
		return tehsilName;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}
}