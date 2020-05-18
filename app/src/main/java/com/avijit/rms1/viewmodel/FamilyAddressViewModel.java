package com.avijit.rms1.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.avijit.rms1.data.local.entities.Area;
import com.avijit.rms1.data.local.entities.District;
import com.avijit.rms1.data.local.entities.Division;
import com.avijit.rms1.data.remote.model.ReliefSchedule;
import com.avijit.rms1.data.remote.responses.ReliefScheduleStoreResponse;
import com.avijit.rms1.repository.LocationRepository;

import java.util.List;

public class FamilyAddressViewModel extends AndroidViewModel {
    private LocationRepository locationRepository;
    private LiveData<List<Division>> divisions;
    public FamilyAddressViewModel(@NonNull Application application) {
        super(application);
        locationRepository = LocationRepository.getInstance(application);
        divisions = locationRepository.getAllDivisions();
    }

    public LiveData<List<Division>> getDivisions(){
        return divisions;
    }
    public LiveData<List<District>> getDistrictByDivisionId(String id){
        return locationRepository.getDistrictByDivisionId(id);
    }
    public LiveData<List<Area>> getAreasByDistrictId(String id){
        return locationRepository.getAreaByDistrictId(id);
    }
}
