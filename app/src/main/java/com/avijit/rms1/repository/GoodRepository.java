package com.avijit.rms1.repository;

import android.widget.MultiAutoCompleteTextView;

import androidx.lifecycle.MutableLiveData;

import com.avijit.rms1.data.remote.RetrofitService;
import com.avijit.rms1.data.remote.api.GoodsApi;
import com.avijit.rms1.data.remote.model.Good;
import com.avijit.rms1.data.remote.responses.NetworkResponse;

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


}
