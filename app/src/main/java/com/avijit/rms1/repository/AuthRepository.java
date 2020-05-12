package com.avijit.rms1.repository;

import androidx.lifecycle.MutableLiveData;

import com.avijit.rms1.data.remote.RetrofitService;
import com.avijit.rms1.data.remote.api.AuthApi;
import com.avijit.rms1.data.remote.model.AuthBody;
import com.avijit.rms1.data.remote.responses.AuthResponse;
import com.google.gson.Gson;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthRepository {
    private static AuthRepository authRepository;
    public static AuthRepository getInstance(){
        if(authRepository==null){
            authRepository = new AuthRepository();
        }
        return authRepository;
    }
    private AuthApi authApi;
    public AuthRepository() {
        authApi = RetrofitService.createService(AuthApi.class);
    }
    public MutableLiveData<AuthResponse> getAuth (AuthBody authBody){
        final MutableLiveData<AuthResponse> authResponse = new MutableLiveData<>();
        authApi.login("https://aniksen.me/covidbd/oauth/token",authBody).enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                if(response.code()==200){
                    authResponse.setValue(response.body());
                }
                else {
                    AuthResponse authResponse1 = new AuthResponse();
                    authResponse1.setAccessToken("");
                    authResponse.setValue(authResponse1);
                }
            }
            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {

            }
        });
        return authResponse;
    }
}
