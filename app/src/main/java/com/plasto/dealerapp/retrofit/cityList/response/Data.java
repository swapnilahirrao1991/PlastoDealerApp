package com.plasto.dealerapp.retrofit.cityList.response;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data{

	@SerializedName("cities")
	@Expose
	private List<CitiesItem> cities;

	public void setCities(List<CitiesItem> cities){
		this.cities = cities;
	}

	public List<CitiesItem> getCities(){
		return cities;
	}
}