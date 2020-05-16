package com.avijit.rms1.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.avijit.rms1.data.remote.responses.ReliefScheduleResponse;
import com.avijit.rms1.repository.ReliefScheduleRepository;

public class PendingSchedulesViewModel extends ViewModel {
    public ReliefScheduleRepository reliefScheduleRepository;
    public void init(){
        reliefScheduleRepository = ReliefScheduleRepository.getInstance();
    }
    public MutableLiveData<ReliefScheduleResponse> getPendingSchedules(String id){
        return reliefScheduleRepository.pendingSchedules(id);
    }
}
