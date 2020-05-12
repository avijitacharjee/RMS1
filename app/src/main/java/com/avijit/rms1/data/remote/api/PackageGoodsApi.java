package com.avijit.rms1.data.remote.api;

import com.avijit.rms1.data.remote.model.PackageGoods;
import com.avijit.rms1.data.remote.model.PackageGoodsUpdate;
import com.avijit.rms1.data.remote.model.PackageUpdate;
import com.avijit.rms1.data.remote.responses.PackageGoodsResponse;
import com.avijit.rms1.data.remote.responses.PackageResponse;
import com.avijit.rms1.data.remote.responses.PackageStoreResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PackageGoodsApi {
    @GET("package-goods")
    Call<PackageGoodsResponse> getAllPackageGoods();

    @GET("package-goods/{id}")
    Call<PackageGoodsResponse> getPackageGoodsById(@Path("id") String id);

    @POST("package-goods")
    Call<PackageGoodsResponse> storePackageGoods(@Body PackageGoods pkggoods);

    @DELETE("package-goods/{id}")
    Call<PackageGoodsResponse> delete(@Path("id") String id);

    @POST("package-goods/{id}")
    Call<PackageGoodsResponse> update(@Path("id") String id, @Body PackageGoodsUpdate body);
}
