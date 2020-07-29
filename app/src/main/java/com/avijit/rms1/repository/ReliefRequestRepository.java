package com.avijit.rms1.repository;

import android.util.Log;
import android.util.MutableBoolean;

import androidx.lifecycle.MutableLiveData;

import com.avijit.rms1.data.remote.RetrofitService;
import com.avijit.rms1.data.remote.api.ReliefRequestApi;
import com.avijit.rms1.data.remote.model.ReliefRequest;
import com.avijit.rms1.data.remote.responses.NetworkResponse;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReliefRequestRepository {
    private static final String TAG = "ReliefRequestRepository";
    private static ReliefRequestRepository reliefRequestRepository;
    public static ReliefRequestRepository getInstance(){
        if(reliefRequestRepository==null){
            reliefRequestRepository=new ReliefRequestRepository();
        }
        return reliefRequestRepository;
    }
    private ReliefRequestApi reliefRequestApi;
    public ReliefRequestRepository(){
        reliefRequestApi = RetrofitService.createService(ReliefRequestApi.class);
    }
    public MutableLiveData<NetworkResponse<ReliefRequest>> saveReliefRequest(ReliefRequest data){
        final MutableLiveData<NetworkResponse<ReliefRequest>> result = new MutableLiveData<>();
        reliefRequestApi.saveRequest(data).enqueue(new Callback<NetworkResponse<ReliefRequest>>() {
            @Override
            public void onResponse(Call<NetworkResponse<ReliefRequest>> call, Response<NetworkResponse<ReliefRequest>> response) {
                Log.d(TAG, "onResponse: "+response.toString());
                if(response.isSuccessful()){
                    result.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<NetworkResponse<ReliefRequest>> call, Throwable t) {
                NetworkResponse<ReliefRequest> r = new NetworkResponse<>();
                r.setNetworkIsSuccessful(true);
                result.setValue(r);
            }
        });
        return result;
    }
    public MutableLiveData<NetworkResponse<List<ReliefRequest>>> all(){
        final MutableLiveData<NetworkResponse<List<ReliefRequest>>> result = new MutableLiveData<>();
        NetworkResponse<List<ReliefRequest>> fail = new NetworkResponse<>();
        fail.setNetworkIsSuccessful(false);
        reliefRequestApi.all().enqueue(new Callback<NetworkResponse<List<ReliefRequest>>>() {
            @Override
            public void onResponse(Call<NetworkResponse<List<ReliefRequest>>> call, Response<NetworkResponse<List<ReliefRequest>>> response) {
                if(response.isSuccessful()){
                    result.setValue(response.body());
                }
                else {
                    result.setValue(fail);
                }
            }

            @Override
            public void onFailure(Call<NetworkResponse<List<ReliefRequest>>> call, Throwable t) {
                result.setValue(fail);
            }
        });
        return result;
    }

}
