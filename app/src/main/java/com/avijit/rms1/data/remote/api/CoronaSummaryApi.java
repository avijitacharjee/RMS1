package com.avijit.rms1.data.remote.api;

import com.avijit.rms1.data.remote.responses.CoronaSummaryResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CoronaSummaryApi {
    @GET("corona-summary")
    Call<CoronaSummaryResponse> getCoronaSummary();
}
