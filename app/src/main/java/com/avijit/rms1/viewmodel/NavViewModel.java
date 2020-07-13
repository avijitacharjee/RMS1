package com.avijit.rms1.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.avijit.rms1.repository.LocationRepository;

/**
 * Created by Avijit Acharjee on 7/13/2020 at 12:43 PM.
 * Email: avijitach@gmail.com.
 */
public class NavViewModel extends AndroidViewModel {
    LocationRepository locationRepository;
    public NavViewModel(@NonNull Application application) {
        super(application);
    }
    public MutableLiveData<Boolean> cacheLocations(){
        return locationRepository.cacheLocation();
    }
}
