package com.avijit.rms1.data.remote.api;

import com.avijit.rms1.data.remote.model.CompanyUser;
import com.avijit.rms1.data.remote.responses.CompanyUserStoreResponse;
import com.avijit.rms1.data.remote.responses.PackageResponse;
import com.avijit.rms1.data.remote.responses.PackageStoreResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PackageApi {
    @GET("package")
    Call<PackageResponse> getAllPackages();

    @GET("package/{id}")
    Call<Package> getPackageById(@Path("id") String id);

    @POST("package")
    Call<PackageResponse> storePackage(@Body Package pkg);

    @DELETE("package/{id}")
    Call<PackageStoreResponse> delete(@Path("id") String id);

}
