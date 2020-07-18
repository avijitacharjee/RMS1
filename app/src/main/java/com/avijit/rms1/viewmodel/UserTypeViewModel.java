package com.avijit.rms1.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.avijit.rms1.data.remote.model.UserType;
import com.avijit.rms1.data.remote.responses.NetworkResponse;
import com.avijit.rms1.repository.UserTypeRepository;

import java.util.List;

/**
 * Created by Avijit Acharjee on 7/18/2020 at 12:53 PM.
 * Email: avijitach@gmail.com.
 */
public class UserTypeViewModel extends ViewModel {
    UserTypeRepository userTypeRepository;
    public UserTypeViewModel(){
        userTypeRepository=UserTypeRepository.getInstance();
    }
    public MutableLiveData<NetworkResponse<List<UserType>>> getAll(){
        return userTypeRepository.getUserTypes();
    }
    public MutableLiveData<NetworkResponse<UserType>> save(UserType userType){
        return userTypeRepository.saveUserType(userType);
    }
    public MutableLiveData<NetworkResponse<UserType>> update(String id, UserType userType){
        return userTypeRepository.updateUserType(id,userType);
    }
    public MutableLiveData<NetworkResponse<UserType>> delete(String id){
        return userTypeRepository.deleteUser(id);
    }
}
