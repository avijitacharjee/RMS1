package com.avijit.rms1.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.avijit.rms1.data.remote.responses.ReliefSearchResponse;
import com.avijit.rms1.repository.ReliefRepository;

public class SearchByNidViewModel extends ViewModel {
    private ReliefRepository reliefRepository;
    public SearchByNidViewModel(){
        reliefRepository = ReliefRepository.getInstance();
    }
    public MutableLiveData<ReliefSearchResponse> searchRelief(String data){
        return reliefRepository.searchRelief(data);
    }
}
