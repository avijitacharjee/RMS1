package com.avijit.rms1.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.avijit.rms1.data.remote.model.Good;
import com.avijit.rms1.data.remote.responses.NetworkResponse;
import com.avijit.rms1.repository.GoodRepository;

import java.util.List;

/**
 * Created by Avijit Acharjee on 7/23/2020 at 12:15 AM.
 * Email: avijitach@gmail.com.
 */
public class GoodsViewModel extends ViewModel {
    GoodRepository goodRepository;
    MutableLiveData<NetworkResponse<List<Good>>> goodList;
    public GoodsViewModel(){
        goodRepository=GoodRepository.getInstance();
    }

    public MutableLiveData<NetworkResponse<List<Good>>> getGoodList() {
        if(goodList==null){
            goodList = goodRepository.getAllGoods();
        }
        return goodList;
    }
    public MutableLiveData<NetworkResponse<Good>> delete(String id){
        return goodRepository.delete(id);
    }
    public MutableLiveData<NetworkResponse<Good>> update(String id,Good good){
        return goodRepository.update(id,good);
    }
    public MutableLiveData<NetworkResponse<Good>> create(Good good){
        return goodRepository.create(good);
    }
}
