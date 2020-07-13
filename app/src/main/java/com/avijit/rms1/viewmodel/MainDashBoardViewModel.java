package com.avijit.rms1.viewmodel;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.avijit.rms1.MainActivity;
import com.avijit.rms1.data.remote.responses.CoronaSummaryResponse;
import com.avijit.rms1.repository.CoronaSummaryRepository;
import com.avijit.rms1.repository.LocationRepository;
import com.avijit.rms1.ui.MainDashboard;
import com.avijit.rms1.ui.Nav;

public class MainDashBoardViewModel extends AndroidViewModel {
    LocationRepository locationRepository;
    AlertDialog dialog;
    Context context;

    private MutableLiveData<CoronaSummaryResponse> mutableLiveData;
    private CoronaSummaryRepository coronaSummaryRepository;

    public MainDashBoardViewModel(@NonNull Application application) {
        super(application);
        context=application;
        locationRepository=LocationRepository.getInstance(application);
    }

    public void init()
    {
        if(mutableLiveData!=null)
        {
            return;
        }
        coronaSummaryRepository = CoronaSummaryRepository.getInstance();

        mutableLiveData = coronaSummaryRepository.getStudents();
    }
    public LiveData<CoronaSummaryResponse> getStudentRepository()
    {
        return mutableLiveData;
    }

    public MutableLiveData<Boolean> cacheLocations(){
        return locationRepository.cacheLocation();
    }

}
