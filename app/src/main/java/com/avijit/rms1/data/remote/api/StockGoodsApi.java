package com.avijit.rms1.data.remote.api;

import com.avijit.rms1.data.remote.model.Good;
import com.avijit.rms1.data.remote.model.UpdateGood;
import com.avijit.rms1.data.remote.responses.GoodResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface StockGoodsApi {
    @GET("stock-goods")
    Call<GoodResponse> getAllGoods();
    @GET("stock-goods/{id}")
    Call<GoodResponse> getGoodById(@Path("id") String id);

    @DELETE("stock-goods/{id}")
    Call<GoodResponse> delete(@Path("id") String id);

    @POST("goods")
    Call<GoodResponse> storeGood(@Body Good good);

    @POST("goods/1")
    Call<GoodResponse> updateGood(@Body UpdateGood good);
}
