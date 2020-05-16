package com.avijit.rms1.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.avijit.rms1.data.remote.model.CompanyUser;
import com.avijit.rms1.data.remote.responses.CompanyStoreResponse;
import com.avijit.rms1.data.remote.responses.CompanyUserStoreResponse;
import com.avijit.rms1.repository.CompanyRepository;

public class AddCompanyViewModel extends ViewModel {
    private MutableLiveData<CompanyStoreResponse> mutableLiveData;
    private CompanyRepository companyRepository;
    public void init(){
        if(companyRepository!=null){
            return;
        }
        mutableLiveData = new MutableLiveData<>();
        companyRepository = CompanyRepository.getInstance();
    }
    public MutableLiveData<CompanyStoreResponse> addCompany(String companyName){
        return companyRepository.addCompany(companyName);
    }
    public MutableLiveData<CompanyUserStoreResponse> addUserToCompany(CompanyUser user){
        return companyRepository.addUserToCompany(user);
    }
}
