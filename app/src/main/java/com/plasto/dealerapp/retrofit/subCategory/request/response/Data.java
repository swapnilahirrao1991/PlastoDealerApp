package com.plasto.dealerapp.retrofit.subCategory.request.response;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data{

	@SerializedName("sub_categorys")
	@Expose
	private List<SubCategorysItem> subCategorys;

	public void setSubCategorys(List<SubCategorysItem> subCategorys){
		this.subCategorys = subCategorys;
	}

	public List<SubCategorysItem> getSubCategorys(){
		return subCategorys;
	}
}