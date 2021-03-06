package com.avijit.rms1.data.remote.api;

import com.avijit.rms1.data.remote.model.Good;
import com.avijit.rms1.data.remote.model.UpdateGood;
import com.avijit.rms1.data.remote.responses.GoodResponse;
import com.avijit.rms1.data.remote.responses.NetworkResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface GoodsApi {
    @GET("goods")
    Call<NetworkResponse<List<Good>>> getAllGoods();

    @GET("goods/{id}")
    Call<GoodResponse> getGoodById(@Path("id") String id);

    @DELETE("goods/{id}")
    Call<NetworkResponse<Good>> delete(@Path("id") String id);

    @POST("goods")
    Call<NetworkResponse<Good>> storeGood(@Body Good good);

    @POST("goods/{id}")
    Call<NetworkResponse<Good>> updateGood(@Path("id") String id,@Body Good good);

}
