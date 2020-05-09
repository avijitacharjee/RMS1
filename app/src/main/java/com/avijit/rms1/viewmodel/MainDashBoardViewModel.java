package com.avijit.rms1.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.avijit.rms1.data.remote.responses.CoronaSummaryResponse;
import com.avijit.rms1.repository.CoronaSummaryRepository;

public class MainDashBoardViewModel extends ViewModel {
    private MutableLiveData<CoronaSummaryResponse> mutableLiveData;
    private CoronaSummaryRepository coronaSummaryRepository;
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
}
