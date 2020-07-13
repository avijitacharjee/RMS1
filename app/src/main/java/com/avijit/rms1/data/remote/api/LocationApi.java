package com.avijit.rms1.data.remote.api;

import com.avijit.rms1.data.remote.model.Division;
import com.avijit.rms1.data.remote.responses.NetworkResponse;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface LocationApi {
    @GET("locations")
    Call<NetworkResponse<List<Division>>> getAllLocations();
}
