package com.avijit.rms1.data.remote.api;

import com.avijit.rms1.data.remote.responses.LocationResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface LocationApi {
    @GET("locations")
    Call<LocationResponse> getAllLocations();
}
