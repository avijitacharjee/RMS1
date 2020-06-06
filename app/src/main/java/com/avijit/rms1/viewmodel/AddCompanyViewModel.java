package com.avijit.rms1.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.avijit.rms1.data.local.entities.Division;
import com.avijit.rms1.data.remote.model.Company;
import com.avijit.rms1.data.remote.model.CompanyUser;
import com.avijit.rms1.data.remote.responses.CompanyStoreResponse;
import com.avijit.rms1.data.remote.responses.CompanyUserStoreResponse;
import com.avijit.rms1.data.remote.responses.NetworkResponse;
import com.avijit.rms1.repository.CompanyRepository;
import com.avijit.rms1.repository.LocationRepository;

import java.util.List;

public class AddCompanyViewModel extends AndroidViewModel {
    private MutableLiveData<CompanyStoreResponse> mutableLiveData;
    private MutableLiveData<Boolean> cacheLocations;
    private CompanyRepository companyRepository;
    private LocationRepository locationRepository;
    private LiveData<List<Division>> division ;
    Application application;
    public void init(){
        if(companyRepository!=null){
            return;
        }
        mutableLiveData = new MutableLiveData<>();
        companyRepository = CompanyRepository.getInstance();
        locationRepository = LocationRepository.getInstance(application);
        cacheLocations = locationRepository.cacheLocation();
        division= locationRepository.getAllDivisions();
    }
    public AddCompanyViewModel(Application application){
        super(application);
        this.application = application;
    }
    public MutableLiveData<NetworkResponse<Company>> addCompany(String companyName, String email){
        return companyRepository.addCompany(companyName,email);
    }
    public MutableLiveData<NetworkResponse<CompanyUser>> addUserToCompany(String email,String company_id,String role){
        return companyRepository.addUserToCompany(email, company_id, role);
    }
    public LiveData<List<Division>> getDivisions(){
        return division;
    }
}
