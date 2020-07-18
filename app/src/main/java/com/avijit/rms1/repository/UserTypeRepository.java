package com.avijit.rms1.repository;

import androidx.lifecycle.MutableLiveData;

import com.avijit.rms1.data.remote.RetrofitService;
import com.avijit.rms1.data.remote.api.UserTypeApi;
import com.avijit.rms1.data.remote.model.UserType;
import com.avijit.rms1.data.remote.responses.NetworkResponse;
import com.avijit.rms1.data.remote.responses.UserTypeResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserTypeRepository {
    private static UserTypeRepository userTypeRepository;
    private UserTypeApi userTypeApi;
    public static UserTypeRepository getInstance(){
        if(userTypeRepository==null){
            userTypeRepository= new UserTypeRepository();
        }
        return userTypeRepository;
    }
    public UserTypeRepository(){
        userTypeApi = RetrofitService.createService(UserTypeApi.class);
    }
    public MutableLiveData<NetworkResponse<List<UserType>>> getUserTypes(){
        final MutableLiveData<NetworkResponse<List<UserType>>> result = new MutableLiveData<>();
        userTypeApi.allTypes().enqueue(new Callback<NetworkResponse<List<UserType>>>() {
            @Override
            public void onResponse(Call<NetworkResponse<List<UserType>>> call, Response<NetworkResponse<List<UserType>>> response) {
                if(response.isSuccessful()){
                    result.setValue(response.body());
                }
                else {
                    result.setValue(new UserTypeResponse.Builder<NetworkResponse<List<UserType>>>(NetworkResponse.class).setNetworkIsSuccessful(false));
                }
            }
            @Override
            public void onFailure(Call<NetworkResponse<List<UserType>>> call, Throwable t) {
                    result.setValue(new UserTypeResponse.Builder<NetworkResponse<List<UserType>>>(NetworkResponse.class).setNetworkIsSuccessful(false));
            }
        });
        return result;
    }
    public MutableLiveData<NetworkResponse<UserType>> getUserTypeById(String id){
        MutableLiveData<NetworkResponse<UserType>> result =new  MutableLiveData<>();
        userTypeApi.getTypeById(id).enqueue(new Callback<NetworkResponse<UserType>>() {
            @Override
            public void onResponse(Call<NetworkResponse<UserType>> call, Response<NetworkResponse<UserType>> response) {
                if(response.isSuccessful()){
                    result.setValue(response.body());
                }
                else {
                    NetworkResponse<UserType> t= new NetworkResponse<>();
                    t.setNetworkIsSuccessful(false);
                    result.setValue(t);
                }
            }

            @Override
            public void onFailure(Call<NetworkResponse<UserType>> call, Throwable t) {
                NetworkResponse<UserType> networkResponse= new NetworkResponse<>();
                networkResponse.setNetworkIsSuccessful(false);
                result.setValue(networkResponse);
            }
        });
        return result;
    }
    public MutableLiveData<NetworkResponse<UserType>> deleteUser(String id){
        MutableLiveData<NetworkResponse<UserType>> result =new  MutableLiveData<>();
        userTypeApi.deleteType(id).enqueue(new Callback<NetworkResponse<UserType>>() {
            @Override
            public void onResponse(Call<NetworkResponse<UserType>> call, Response<NetworkResponse<UserType>> response) {
                if(response.isSuccessful()){
                    result.setValue(response.body());
                }
                else {
                    NetworkResponse<UserType> t= new NetworkResponse<>();
                    t.setNetworkIsSuccessful(false);
                    result.setValue(t);
                }
            }

            @Override
            public void onFailure(Call<NetworkResponse<UserType>> call, Throwable t) {
                NetworkResponse<UserType> networkResponse= new NetworkResponse<>();
                networkResponse.setNetworkIsSuccessful(false);
                result.setValue(networkResponse);
            }
        });
        return result;
    }
    public MutableLiveData<NetworkResponse<UserType>> saveUserType(UserType userType){
        MutableLiveData<NetworkResponse<UserType>> result =new  MutableLiveData<>();
        userTypeApi.saveUserType(userType).enqueue(new Callback<NetworkResponse<UserType>>() {
            @Override
            public void onResponse(Call<NetworkResponse<UserType>> call, Response<NetworkResponse<UserType>> response) {
                if(response.isSuccessful()){
                    result.setValue(response.body());
                }
                else {
                    NetworkResponse<UserType> t= new NetworkResponse<>();
                    t.setNetworkIsSuccessful(false);
                    result.setValue(t);
                }
            }

            @Override
            public void onFailure(Call<NetworkResponse<UserType>> call, Throwable t) {
                NetworkResponse<UserType> networkResponse= new NetworkResponse<>();
                networkResponse.setNetworkIsSuccessful(false);
                result.setValue(networkResponse);
            }
        });
        return result;
    }
    public MutableLiveData<NetworkResponse<UserType>> updateUserType(String id,UserType userType){
        MutableLiveData<NetworkResponse<UserType>> result =new  MutableLiveData<>();
        userTypeApi.updateUserType(id,userType).enqueue(new Callback<NetworkResponse<UserType>>() {
            @Override
            public void onResponse(Call<NetworkResponse<UserType>> call, Response<NetworkResponse<UserType>> response) {
                if(response.isSuccessful()){
                    result.setValue(response.body());
                }
                else {
                    NetworkResponse<UserType> t= new NetworkResponse<>();
                    t.setNetworkIsSuccessful(false);
                    result.setValue(t);
                }
            }

            @Override
            public void onFailure(Call<NetworkResponse<UserType>> call, Throwable t) {
                NetworkResponse<UserType> networkResponse= new NetworkResponse<>();
                networkResponse.setNetworkIsSuccessful(false);
                result.setValue(networkResponse);
            }
        });
        return result;
    }
}
