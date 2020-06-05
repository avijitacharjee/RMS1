package com.avijit.rms1.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.avijit.rms1.data.remote.model.Relief;
import com.avijit.rms1.data.remote.responses.NetworkResponse;
import com.avijit.rms1.data.remote.responses.ReliefSearchResponse;
import com.avijit.rms1.repository.ReliefRepository;
import com.avijit.rms1.ui.ReliefRequest;

import java.util.List;

public class SearchByNidViewModel extends ViewModel {
    private ReliefRepository reliefRepository;
    public SearchByNidViewModel(){
        reliefRepository = ReliefRepository.getInstance();
    }
    public MutableLiveData<NetworkResponse<List<Relief>>> searchRelief(String data){
        return reliefRepository.searchRelief(data);
    }
}
