package com.avijit.rms1.repository;

import androidx.lifecycle.MutableLiveData;

import com.avijit.rms1.data.remote.RetrofitService;
import com.avijit.rms1.data.remote.api.ReliefApi;
import com.avijit.rms1.data.remote.model.Relief;
import com.avijit.rms1.data.remote.responses.ReliefStoreResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReliefRepository {
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
            }

            @Override
            public void onFailure(Call<ReliefStoreResponse> call, Throwable t) {

            }
        });
        return result;
    }

}
