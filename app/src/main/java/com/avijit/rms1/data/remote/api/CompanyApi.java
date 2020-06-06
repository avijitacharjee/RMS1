package com.avijit.rms1.data.remote.api;

import com.avijit.rms1.data.remote.model.Company;
import com.avijit.rms1.data.remote.model.CompanyUser;
import com.avijit.rms1.data.remote.responses.CompanyResponse;
import com.avijit.rms1.data.remote.responses.CompanySearchResponse;
import com.avijit.rms1.data.remote.responses.CompanyStoreResponse;
import com.avijit.rms1.data.remote.responses.CompanyUserStoreResponse;
import com.avijit.rms1.data.remote.responses.CoronaSummaryResponse;
import com.avijit.rms1.data.remote.responses.NetworkResponse;

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
    Call<NetworkResponse<Company>> storeCompany(@Field("name") String name, @Field("email") String email);

    @GET("company/list")
    Call<CompanyResponse> getCompanyList();

    @GET("company/search/{data}")
    Call<CompanySearchResponse> searchCompany(@Path("data") String data);

    @FormUrlEncoded
    @POST("company/user/store")
    Call<NetworkResponse<CompanyUser>> storeUser(@Field("email") String email,@Field("company_id") String company_id,@Field("role") String role);

}
