package com.avijit.rms1.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.avijit.rms1.data.remote.api.UserApi;
import com.avijit.rms1.data.remote.model.User;
import com.avijit.rms1.data.remote.responses.UserStoreResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {
    private static final String TAG = "UserRepository";
    private static UserRepository userRepository;
    private UserApi userApi;
    public static UserRepository getInstance(){
        if(userRepository==null){
            userRepository= new UserRepository();
        }
        return userRepository;
    }
    public MutableLiveData<UserStoreResponse> addUser(User user){
        final MutableLiveData<UserStoreResponse> userResponse = new MutableLiveData<>();
        userApi.storeUser(user).enqueue(new Callback<UserStoreResponse>() {
            @Override
            public void onResponse(Call<UserStoreResponse> call, Response<UserStoreResponse> response) {
                Log.d(TAG, "onResponse: "+response.toString());
                if(response.isSuccessful()){
                    userResponse.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<UserStoreResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t);
            }
        });
        return userResponse;
    }
}
