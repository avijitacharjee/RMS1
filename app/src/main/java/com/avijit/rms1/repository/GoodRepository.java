package com.avijit.rms1.repository;

import android.util.Log;
import android.widget.MultiAutoCompleteTextView;

import androidx.lifecycle.MutableLiveData;

import com.avijit.rms1.data.remote.RetrofitService;
import com.avijit.rms1.data.remote.api.GoodsApi;
import com.avijit.rms1.data.remote.model.Good;
import com.avijit.rms1.data.remote.responses.NetworkResponse;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Avijit Acharjee on 7/23/2020 at 6:55 PM.
 * Email: avijitach@gmail.com.
 */
public class GoodRepository {
    private static final String TAG = "GoodRepository";
    private static GoodRepository goodRepository;
    GoodsApi goodsApi;
    public static GoodRepository getInstance(){
        if(goodRepository==null) goodRepository = new GoodRepository();
        return goodRepository;
    }
    private GoodRepository(){
        goodsApi= RetrofitService.createService(GoodsApi.class);
    }
    public MutableLiveData<NetworkResponse<List<Good>>> getAllGoods(){
        MutableLiveData<NetworkResponse<List<Good>>> result = new MutableLiveData<>();
        NetworkResponse<List<Good>> err= new NetworkResponse<>();
        err.setNetworkIsSuccessful(false);
        goodsApi.getAllGoods().enqueue(new Callback<NetworkResponse<List<Good>>>() {
            @Override
            public void onResponse(Call<NetworkResponse<List<Good>>> call, Response<NetworkResponse<List<Good>>> response) {
                if(response.isSuccessful()){
                    result.setValue(response.body());
                }
                else {
                    result.setValue(err);
                }
            }

            @Override
            public void onFailure(Call<NetworkResponse<List<Good>>> call, Throwable t) {
                result.setValue(err);
            }
        });
        return result;
    }
    public MutableLiveData<NetworkResponse<Good>> delete(String id){
        MutableLiveData<NetworkResponse<Good>> result = new MutableLiveData<>();
        NetworkResponse<Good> failed = new NetworkResponse<>();
        failed.setNetworkIsSuccessful(false);
        goodsApi.delete(id).enqueue(new Callback<NetworkResponse<Good>>() {
            @Override
            public void onResponse(Call<NetworkResponse<Good>> call, Response<NetworkResponse<Good>> response) {
                if(response.isSuccessful()) result.setValue(response.body());
                else result.setValue(failed);
            }

            @Override
            public void onFailure(Call<NetworkResponse<Good>> call, Throwable t) {
                result.setValue(failed);
            }
        });
        return result;
    }
    public MutableLiveData<NetworkResponse<Good>> update(String id,Good good){
        MutableLiveData<NetworkResponse<Good>> result = new MutableLiveData<>();
        NetworkResponse<Good> ff= new NetworkResponse<>();
        ff.setNetworkIsSuccessful(false);
        goodsApi.updateGood(id,good).enqueue(new Callback<NetworkResponse<Good>>() {
            @Override
            public void onResponse(Call<NetworkResponse<Good>> call, Response<NetworkResponse<Good>> response) {
                if(response.isSuccessful()){
                    result.setValue(response.body());
                }
                else {
                    result.setValue(ff);
                }
            }

            @Override
            public void onFailure(Call<NetworkResponse<Good>> call, Throwable t) {
                result.setValue(ff);
            }
        });
        return result;
    }
    public MutableLiveData<NetworkResponse<Good>> create(Good good){
        MutableLiveData<NetworkResponse<Good>> result = new MutableLiveData<>();
        NetworkResponse<Good> ff= new NetworkResponse<>();
        ff.setNetworkIsSuccessful(false);
        goodsApi.storeGood(good).enqueue(new Callback<NetworkResponse<Good>>() {
            @Override
            public void onResponse(Call<NetworkResponse<Good>> call, Response<NetworkResponse<Good>> response) {
                Log.d(TAG, new Gson().toJson(good)+"onResponse: "+new Gson().toJson(response));
                if(response.isSuccessful()){
                    result.setValue(response.body());
                }
                else {
                    result.setValue(ff);
                }
            }
            @Override
            public void onFailure(Call<NetworkResponse<Good>> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.toString());
                result.setValue(ff);
            }
        });

        return result;
    }


}
