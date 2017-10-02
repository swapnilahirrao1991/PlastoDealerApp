package com.plasto.dealerapp.retrofit.tashilList.request.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TashilListResponse{

	@SerializedName("data")
	@Expose
	private Data data;

	@SerializedName("response")
	@Expose
	private boolean response;

	@SerializedName("message")
	@Expose
	private String message;

	public void setData(Data data){
		this.data = data;
	}

	public Data getData(){
		return data;
	}

	public void setResponse(boolean response){
		this.response = response;
	}

	public boolean isResponse(){
		return response;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}
}