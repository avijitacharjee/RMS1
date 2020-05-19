package com.avijit.rms1.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.avijit.rms1.data.remote.model.Relief;
import com.avijit.rms1.data.remote.responses.ReliefStoreResponse;
import com.avijit.rms1.repository.ReliefRepository;

public class StoreReliefViewModel extends AndroidViewModel {
    private ReliefRepository reliefRepository;
    public StoreReliefViewModel(@NonNull Application application) {
        super(application);
        reliefRepository = ReliefRepository.getInstance();
    }
    public MutableLiveData<ReliefStoreResponse> storeRelief(Relief relief){
        return reliefRepository.storeGivenRelief(relief);
    }
}
