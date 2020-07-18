package com.avijit.rms1.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.avijit.rms1.data.remote.RetrofitService;
import com.avijit.rms1.data.remote.api.UserApi;
import com.avijit.rms1.data.remote.model.User;
import com.avijit.rms1.data.remote.responses.BaseModel;
import com.avijit.rms1.data.remote.responses.NetworkResponse;
import com.avijit.rms1.data.remote.responses.UserStoreResponse;

import java.util.List;

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
    public UserRepository(){
        userApi = RetrofitService.createService(UserApi.class);
    }
    public MutableLiveData<NetworkResponse<User>> addUser(final User user){
        final MutableLiveData<NetworkResponse<User>> userResponse = new MutableLiveData<>();
        userApi.storeUser(user).enqueue(new Callback<NetworkResponse<User>>() {
            @Override
            public void onResponse(Call<NetworkResponse<User>> call, Response<NetworkResponse<User>> response) {
                Log.d(TAG, "onResponse: "+response.toString());
                if(response.isSuccessful()){
                    userResponse.setValue(response.body());
                }
                else {
                    userResponse.setValue(new BaseModel.Builder<NetworkResponse<User>>(NetworkResponse.class).setNetworkIsSuccessful(false));
                }
            }

            @Override
            public void onFailure(Call<NetworkResponse<User>> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t);
                userResponse.setValue(new BaseModel.Builder<NetworkResponse<User>>(NetworkResponse.class).setNetworkIsSuccessful(false));
            }
        });
        return userResponse;
    }
    public MutableLiveData<User> getUserByToken(String token){
        final MutableLiveData<User> user= new MutableLiveData<>();
        userApi.getUserByHeader(token).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    user.setValue(response.body());
                }
                else {
                    user.setValue(new User.Builder<User>(User.class).setNetworkIsSuccessful(false));
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

                user.setValue(new User.Builder<User>(User.class).setNetworkIsSuccessful(false));
            }
        });
        return user;
    }
    public MutableLiveData<NetworkResponse<List<User>>> getAllUser(){
        MutableLiveData<NetworkResponse<List<User>>> result= new MutableLiveData<>();
        userApi.allUser().enqueue(new Callback<NetworkResponse<List<User>>>() {
            @Override
            public void onResponse(Call<NetworkResponse<List<User>>> call, Response<NetworkResponse<List<User>>> response) {
                if(response.isSuccessful()){
                    result.setValue(response.body());
                }
                else {
                    NetworkResponse<List<User>> fail= new NetworkResponse<>();
                    fail.setNetworkIsSuccessful(false);
                    result.setValue(fail);
                }
            }

            @Override
            public void onFailure(Call<NetworkResponse<List<User>>> call, Throwable t) {
                NetworkResponse<List<User>> fail= new NetworkResponse<>();
                fail.setNetworkIsSuccessful(false);
                result.setValue(fail);
            }
        });
        return result;
    }
    public MutableLiveData<NetworkResponse<User>> updateUser(String id, User user){
        MutableLiveData<NetworkResponse<User>> rslt = new MutableLiveData<>();
        userApi.update(id,user).enqueue(new Callback<NetworkResponse<User>>() {
            @Override
            public void onResponse(Call<NetworkResponse<User>> call, Response<NetworkResponse<User>> response) {
                if(response.isSuccessful()){
                    rslt.setValue(response.body());
                }
                else {
                    NetworkResponse<User> fail = new NetworkResponse<>();
                    fail.setNetworkIsSuccessful(false);
                    rslt.setValue(fail);
                }
            }

            @Override
            public void onFailure(Call<NetworkResponse<User>> call, Throwable t) {
                NetworkResponse<User> fail = new NetworkResponse<>();
                fail.setNetworkIsSuccessful(false);
                rslt.setValue(fail);
            }
        });
        return rslt;
    }
    public MutableLiveData<NetworkResponse<User>> deleteUser(String id){
        MutableLiveData<NetworkResponse<User>> rslt = new MutableLiveData<>();
        userApi.delete(id).enqueue(new Callback<NetworkResponse<User>>() {
            @Override
            public void onResponse(Call<NetworkResponse<User>> call, Response<NetworkResponse<User>> response) {
                if(response.isSuccessful()){
                    rslt.setValue(response.body());
                }
                else {
                    NetworkResponse<User> fail = new NetworkResponse<>();
                    fail.setNetworkIsSuccessful(false);
                    rslt.setValue(fail);
                }
            }

            @Override
            public void onFailure(Call<NetworkResponse<User>> call, Throwable t) {
                NetworkResponse<User> fail = new NetworkResponse<>();
                fail.setNetworkIsSuccessful(false);
                rslt.setValue(fail);
            }
        });
        return rslt;
    }

}
