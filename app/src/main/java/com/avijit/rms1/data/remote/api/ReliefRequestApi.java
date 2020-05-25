package com.avijit.rms1.data.remote.api;

import com.avijit.rms1.data.remote.model.ReliefRequest;
import com.avijit.rms1.data.remote.responses.NetworkResponse;
import com.avijit.rms1.data.remote.responses.SaveReliefRequestResponse;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ReliefRequestApi {
    @POST("request")
    Call<JSONObject> storeReliefRequest(@Body ReliefRequest reliefRequest);

    @POST("reject/{id}")
    Call<JSONObject> rejectReliefRequest();
    @POST("saverequest")
    Call<NetworkResponse<ReliefRequest>> saveRequest(@Body ReliefRequest reliefRequest);
}
