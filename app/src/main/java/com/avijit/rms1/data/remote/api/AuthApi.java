package com.avijit.rms1.data.remote.api;

import com.avijit.rms1.data.remote.model.AuthBody;
import com.avijit.rms1.data.remote.responses.AuthResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface AuthApi {
    @POST
    Call<AuthResponse> login (@Url String url, @Body AuthBody authBody);
}

