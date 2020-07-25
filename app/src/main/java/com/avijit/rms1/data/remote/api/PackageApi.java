package com.avijit.rms1.data.remote.api;

import com.avijit.rms1.data.remote.model.Package;
import com.avijit.rms1.data.remote.model.PackageGood;
import com.avijit.rms1.data.remote.model.PackageUpdate;
import com.avijit.rms1.data.remote.responses.NetworkResponse;
import com.avijit.rms1.data.remote.responses.PackageStoreResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PackageApi {
    @GET("package")
    Call<NetworkResponse<List<Package>>> getAllPackages();

    @GET("package/{id}")
    Call<NetworkResponse<Package>> getPackageById(@Path("id") String id);

    @POST("package")
    Call<NetworkResponse<Package>> storePackage(@Body Package pkg);

    @DELETE("package/{id}")
    Call<NetworkResponse<Package>> delete(@Path("id") String id);

    @POST("package/{id}")
    Call<NetworkResponse<Package>> update(@Path("id") String id, @Body Package body);
}
