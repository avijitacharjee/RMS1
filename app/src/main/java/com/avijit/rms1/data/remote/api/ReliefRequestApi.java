package com.avijit.rms1.data.remote.api;

import com.avijit.rms1.data.remote.model.ReliefRequest;
import com.avijit.rms1.data.remote.responses.NetworkResponse;
import com.avijit.rms1.data.remote.responses.SaveReliefRequestResponse;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ReliefRequestApi {
    @POST("request")
    Call<JSONObject> storeReliefRequest(@Body ReliefRequest reliefRequest);

    @POST("reject/{id}")
    Call<NetworkResponse<ReliefRequest>> rejectReliefRequest(@Path("id")String id);
    @POST("saverequest")
    Call<NetworkResponse<ReliefRequest>> saveRequest(@Body ReliefRequest reliefRequest);

    @GET("pending-requests")
    Call<NetworkResponse<List<ReliefRequest>>> all();
}
