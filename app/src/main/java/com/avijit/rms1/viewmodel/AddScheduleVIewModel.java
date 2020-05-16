package com.avijit.rms1.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.avijit.rms1.data.remote.model.ReliefSchedule;
import com.avijit.rms1.data.remote.responses.ReliefScheduleStoreResponse;
import com.avijit.rms1.repository.ReliefScheduleRepository;

public class AddScheduleVIewModel extends ViewModel {
    private ReliefScheduleRepository reliefScheduleRepository;
    public void init(){
        reliefScheduleRepository = ReliefScheduleRepository.getInstance();
    }
    public MutableLiveData<ReliefScheduleStoreResponse> addSchedule(ReliefSchedule reliefSchedule){
        return reliefScheduleRepository.addSchedule(reliefSchedule);
    }
}
