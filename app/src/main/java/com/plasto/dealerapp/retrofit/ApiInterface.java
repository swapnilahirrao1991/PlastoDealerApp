package com.plasto.dealerapp.retrofit;

import com.plasto.dealerapp.retrofit.addDealer.request.AddDealerRequest;
import com.plasto.dealerapp.retrofit.addDealer.response.AddDealerResponse;
import com.plasto.dealerapp.retrofit.categoryList.request.CategoryRequest;
import com.plasto.dealerapp.retrofit.categoryList.request.response.CategoryResponse;
import com.plasto.dealerapp.retrofit.cityList.response.CityListResponse;
import com.plasto.dealerapp.retrofit.cityList.response.request.CityListRequest;
import com.plasto.dealerapp.retrofit.dayendreport.request.DayEndReportRequest;
import com.plasto.dealerapp.retrofit.editdealer.request.EditDealerRequest;
import com.plasto.dealerapp.retrofit.editdealer.response.EditDealerResponse;
import com.plasto.dealerapp.retrofit.forgotPassword.request.ForgotPwRequest;
import com.plasto.dealerapp.retrofit.forgotPassword.response.ForgotPwResponse;
import com.plasto.dealerapp.retrofit.getLoginData.response.GetLoginDataResponse;
import com.plasto.dealerapp.retrofit.login.request.LoginRequest;
import com.plasto.dealerapp.retrofit.login.response.LoginResponse;
import com.plasto.dealerapp.retrofit.pdfresponse.PdfResponse;
import com.plasto.dealerapp.retrofit.register.request.RegisterRequest;
import com.plasto.dealerapp.retrofit.register.response.RegisterResponse;
import com.plasto.dealerapp.retrofit.searchDealer.request.SearchRequest;
import com.plasto.dealerapp.retrofit.searchDealer.response.SearchDealerResponse;
import com.plasto.dealerapp.retrofit.stateList.request.StateListRequest;
import com.plasto.dealerapp.retrofit.stateList.response.StateListResponse;
import com.plasto.dealerapp.retrofit.subCategory.request.SubCategoryRequest;
import com.plasto.dealerapp.retrofit.subCategory.request.response.SubCategoryResponse;
import com.plasto.dealerapp.retrofit.submitVisit.request.SubmitVisitRequest;
import com.plasto.dealerapp.retrofit.submitVisit.request.SubmitVisitResponse;
import com.plasto.dealerapp.retrofit.tashilList.request.TashilListRequest;
import com.plasto.dealerapp.retrofit.tashilList.request.response.TashilListResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Pat-Win 10 on 03-01-2017.
 */

public interface ApiInterface {

    @POST("login")
    Call<LoginResponse> userLogin(@Body LoginRequest loginRequest);

    @POST("addDealerSubmit")
    Call<AddDealerResponse> addDealer(@Body AddDealerRequest dealerRequest);

    @Headers("Cache-Control: public, max-age=640000, s-maxage=640000 , max-stale=2419200")
    @POST("getStateList")
    Call<StateListResponse> getStateList(@Body StateListRequest stateListRequest);

    @Headers("Cache-Control: public, max-age=640000, s-maxage=640000 , max-stale=2419200")
    @POST("getcity")
    Call<CityListResponse> getCityList(@Body CityListRequest stateListRequest);

    @Headers("Cache-Control: public, max-age=640000, s-maxage=640000 , max-stale=2419200")
    @POST("getCategoryList")
    Call<CategoryResponse> getCategoryList(@Body CategoryRequest categoryRequest);

    @Headers("Cache-Control: public, max-age=640000, s-maxage=640000 , max-stale=2419200")
    @POST("getSubCategory")
    Call<SubCategoryResponse> getSubCategoryList(@Body SubCategoryRequest subCategoryRequest);

    @Headers("Cache-Control: public, max-age=640000, s-maxage=640000 , max-stale=2419200")
    @POST("gettashil")
    Call<TashilListResponse> getTashilList(@Body TashilListRequest tashilListRequest);

    @POST("getDealerSearch")
    Call<SearchDealerResponse> searchDealersList(@Body SearchRequest searchDealerRequest);

    @POST("getsatesstuff")
    Call<GetLoginDataResponse> getUserCityList(@Body CategoryRequest categoryRequest);


    @POST("saveDealerVisit")
    Call<SubmitVisitResponse> submitVisit(@Body SubmitVisitRequest request);

    @POST("forgetPassword")
    Call<ForgotPwResponse> submitForgotPwEmail(@Body ForgotPwRequest forgotPwRequest);

    @POST("requestToLogin")
    Call<RegisterResponse> submitRegistration(@Body RegisterRequest registerRequest);

    @POST("dayendreport")
    Call<PdfResponse> dayEndReport(@Body DayEndReportRequest reportRequest);

    @POST("editDealerData")
    Call<EditDealerResponse> editDealer(@Body EditDealerRequest dealerRequest);
}
