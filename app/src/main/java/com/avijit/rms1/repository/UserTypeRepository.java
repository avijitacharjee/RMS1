package com.avijit.rms1.repository;

import androidx.lifecycle.MutableLiveData;

import com.avijit.rms1.data.remote.RetrofitService;
import com.avijit.rms1.data.remote.api.UserTypeApi;
import com.avijit.rms1.data.remote.responses.UserTypeResponse;

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
    public MutableLiveData<UserTypeResponse> getUserTypes(){
        final MutableLiveData<UserTypeResponse> result = new MutableLiveData<>();
        userTypeApi.allTypes().enqueue(new Callback<UserTypeResponse>() {
            @Override
            public void onResponse(Call<UserTypeResponse> call, Response<UserTypeResponse> response) {
                if(response.isSuccessful()){
                    result.setValue(response.body());
                }
                else {
                    result.setValue(new UserTypeResponse.Builder<UserTypeResponse>(UserTypeResponse.class).setNetworkIsSuccessful(false));
                }
            }

            @Override
            public void onFailure(Call<UserTypeResponse> call, Throwable t) {
                    result.setValue(new UserTypeResponse.Builder<UserTypeResponse>(UserTypeResponse.class).setNetworkIsSuccessful(false));
            }
        });
        return result;
    }
}
