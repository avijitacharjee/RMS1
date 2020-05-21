package com.avijit.rms1.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.avijit.rms1.data.remote.RetrofitService;
import com.avijit.rms1.data.remote.api.ReliefApi;
import com.avijit.rms1.data.remote.api.ReliefScheduleApi;
import com.avijit.rms1.data.remote.model.ReliefSchedule;
import com.avijit.rms1.data.remote.responses.ReliefScheduleResponse;
import com.avijit.rms1.data.remote.responses.ReliefScheduleStoreResponse;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReliefScheduleRepository {
    final String TAG = "Repository";
    private static ReliefScheduleRepository reliefScheduleRepository;
    private ReliefScheduleApi reliefScheduleApi;
    public static ReliefScheduleRepository getInstance(){
        if(reliefScheduleRepository==null){
            reliefScheduleRepository = new ReliefScheduleRepository();
        }
        return reliefScheduleRepository;
    }
    public ReliefScheduleRepository (){
        reliefScheduleApi = RetrofitService.createService(ReliefScheduleApi.class);
    }
    public MutableLiveData<ReliefScheduleStoreResponse> addSchedule(final ReliefSchedule reliefSchedule){
        final MutableLiveData<ReliefScheduleStoreResponse> reliefScheduleStoreResponseMutableLiveData = new MutableLiveData<>();
        reliefScheduleApi.storeSchedule(reliefSchedule).enqueue(new Callback<ReliefScheduleStoreResponse>() {
            @Override
            public void onResponse(Call<ReliefScheduleStoreResponse> call, Response<ReliefScheduleStoreResponse> response) {
                Log.d(TAG, "onResponse: "+new Gson().toJson(response));
                if(response.isSuccessful()){
                    reliefScheduleStoreResponseMutableLiveData.setValue(response.body());
                }
                else {
                    reliefScheduleStoreResponseMutableLiveData.setValue(new ReliefScheduleStoreResponse.Builder<ReliefScheduleStoreResponse>(ReliefScheduleStoreResponse.class).setNetworkIsSuccessful(false));
                }
                 }

            @Override
            public void onFailure(Call<ReliefScheduleStoreResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t);

                reliefScheduleStoreResponseMutableLiveData.setValue(new ReliefScheduleStoreResponse.Builder<ReliefScheduleStoreResponse>(ReliefScheduleStoreResponse.class).setNetworkIsSuccessful(false));
            }
        });
        return reliefScheduleStoreResponseMutableLiveData;
    }
    public MutableLiveData<ReliefScheduleResponse> pendingSchedules(String id){
        final MutableLiveData<ReliefScheduleResponse> reliefScheduleResponseMutableLiveData = new MutableLiveData<>();
        reliefScheduleApi.pendingSchedules(id).enqueue(new Callback<ReliefScheduleResponse>() {
            @Override
            public void onResponse(Call<ReliefScheduleResponse> call, Response<ReliefScheduleResponse> response) {
                Log.d(TAG, "onResponse: "+response.toString());
                if(response.isSuccessful()){
                    reliefScheduleResponseMutableLiveData.setValue(response.body());
                }
                else{
                    reliefScheduleResponseMutableLiveData.setValue(new ReliefScheduleResponse.Builder<ReliefScheduleResponse>(ReliefScheduleResponse.class).setNetworkIsSuccessful(false));
                }
            }

            @Override
            public void onFailure(Call<ReliefScheduleResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t);
                reliefScheduleResponseMutableLiveData.setValue(new ReliefScheduleResponse.Builder<ReliefScheduleResponse>(ReliefScheduleResponse.class).setNetworkIsSuccessful(false));
            }
        });
        return reliefScheduleResponseMutableLiveData;
    }

    public MutableLiveData<ReliefScheduleResponse> completedSchedules(String id){
        final MutableLiveData<ReliefScheduleResponse> reliefScheduleResponseMutableLiveData = new MutableLiveData<>();
        reliefScheduleApi.completedSchedules(id).enqueue(new Callback<ReliefScheduleResponse>() {
            @Override
            public void onResponse(Call<ReliefScheduleResponse> call, Response<ReliefScheduleResponse> response) {
                Log.d(TAG, "onResponse: "+response.toString());
                if(response.isSuccessful()){
                    reliefScheduleResponseMutableLiveData.setValue(response.body());
                }
                else {
                    reliefScheduleResponseMutableLiveData.setValue(new ReliefScheduleResponse.Builder<ReliefScheduleResponse>(ReliefScheduleResponse.class
                    ).setNetworkIsSuccessful(false));
                }
            }

            @Override
            public void onFailure(Call<ReliefScheduleResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t);
                reliefScheduleResponseMutableLiveData.setValue(
                        new ReliefScheduleResponse.Builder<ReliefScheduleResponse>(ReliefScheduleResponse.class
                ).setNetworkIsSuccessful(false));
            }
        });
        return reliefScheduleResponseMutableLiveData;
    }
}
