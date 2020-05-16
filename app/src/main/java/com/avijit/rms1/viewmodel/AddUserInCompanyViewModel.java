package com.avijit.rms1.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.avijit.rms1.data.remote.model.CompanyUser;
import com.avijit.rms1.data.remote.model.User;
import com.avijit.rms1.data.remote.responses.CompanyUserStoreResponse;
import com.avijit.rms1.data.remote.responses.UserStoreResponse;
import com.avijit.rms1.repository.CompanyRepository;
import com.avijit.rms1.repository.UserRepository;

public class AddUserInCompanyViewModel extends ViewModel {
    private MutableLiveData<CompanyUserStoreResponse> companyUserStoreResponseMutableLiveData ;
    private CompanyRepository companyRepository;
    private UserRepository userRepository;
    public void init(){
        if(companyRepository!=null && userRepository!=null){
            return;
        }
        companyRepository = CompanyRepository.getInstance();
        userRepository = UserRepository.getInstance();
    }
    public MutableLiveData<CompanyUserStoreResponse> addUser(CompanyUser companyUser){
        return companyRepository.addUserToCompany(companyUser);
    }
    public MutableLiveData<UserStoreResponse> registerNewUser(User user){
        return userRepository.addUser(user);
    }
}
