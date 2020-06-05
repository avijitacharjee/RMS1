package com.avijit.rms1.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.avijit.rms1.data.remote.RetrofitService;
import com.avijit.rms1.data.remote.api.ReliefApi;
import com.avijit.rms1.data.remote.model.Relief;
import com.avijit.rms1.data.remote.responses.NetworkResponse;
import com.avijit.rms1.data.remote.responses.ReliefSearchResponse;
import com.avijit.rms1.data.remote.responses.ReliefStoreResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReliefRepository {
    private static final String TAG = "ReliefRepository";
    private static ReliefRepository reliefRepository;
    private ReliefApi reliefApi;
    public static ReliefRepository getInstance(){
        if(reliefRepository==null){
            reliefRepository= new ReliefRepository();
        }
        return reliefRepository;
    }
    public ReliefRepository(){
        reliefApi = RetrofitService.createService(ReliefApi.class);
    }
    public MutableLiveData<ReliefStoreResponse> storeGivenRelief(Relief relief){
        final MutableLiveData<ReliefStoreResponse> result = new MutableLiveData<>();
        reliefApi.storeRelief(relief).enqueue(new Callback<ReliefStoreResponse>() {
            @Override
            public void onResponse(Call<ReliefStoreResponse> call, Response<ReliefStoreResponse> response) {
                if(response.isSuccessful()){
                    result.setValue(response.body());
                }
                else {
                    result.setValue((ReliefStoreResponse) new ReliefStoreResponse().setUnSuccess(false));
                }
            }

            @Override
            public void onFailure(Call<ReliefStoreResponse> call, Throwable t) {
                result.setValue(new ReliefStoreResponse.Builder<ReliefStoreResponse>(ReliefStoreResponse.class).setNetworkIsSuccessful(false));
            }
        });
        return result;
    }
    public MutableLiveData<NetworkResponse<List<Relief>>> searchRelief(String data){
        final MutableLiveData<NetworkResponse<List<Relief>>> result = new MutableLiveData<>();
        reliefApi.searchRelief(data).enqueue(new Callback<NetworkResponse<List<Relief>>>() {
            @Override
            public void onResponse(Call<NetworkResponse<List<Relief>>> call, Response<NetworkResponse<List<Relief>>> response) {
                Log.d(TAG, "onResponse: "+response.body().toString());
                result.setValue(response.body());
            }

            @Override
            public void onFailure(Call<NetworkResponse<List<Relief>>> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t);
                NetworkResponse<List<Relief>> r = new NetworkResponse<>();
                r.setNetworkIsSuccessful(false);
                result.setValue(r);
            }
        });
        return result;
    }

}
