package com.avijit.rms1.data.remote.api;

import com.avijit.rms1.data.remote.model.CompanyUser;
import com.avijit.rms1.data.remote.responses.CompanyResponse;
import com.avijit.rms1.data.remote.responses.CompanySearchResponse;
import com.avijit.rms1.data.remote.responses.CompanyStoreResponse;
import com.avijit.rms1.data.remote.responses.CompanyUserStoreResponse;
import com.avijit.rms1.data.remote.responses.CoronaSummaryResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CompanyApi {
    @FormUrlEncoded
    @POST("company/store")
    Call<CompanyStoreResponse> storeCompany(@Field("name") String name);

    @GET("company/list")
    Call<CompanyResponse> getCompanyList();

    @GET("company/search/{data}")
    Call<CompanySearchResponse> searchCompany(@Path("data") String data);

    @POST("company/user/store")
    Call<CompanyUserStoreResponse> storeUser(@Body CompanyUser companyUser);

}
