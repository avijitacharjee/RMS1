package com.avijit.rms1.data.remote.api;

import com.avijit.rms1.data.remote.model.Relief;
import com.avijit.rms1.data.remote.responses.ReliefSearchResponse;
import com.avijit.rms1.data.remote.responses.ReliefStoreResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ReliefApi {
    @POST("relief/store")
    Call<ReliefStoreResponse> storeRelief(@Body Relief relief);
    @GET("relief/search/{data}")
    Call<ReliefSearchResponse> searchRelief(@Path("data") String data);
}
