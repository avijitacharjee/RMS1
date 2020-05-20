package com.avijit.rms1.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.avijit.rms1.data.remote.model.User;
import com.avijit.rms1.data.remote.responses.UserStoreResponse;
import com.avijit.rms1.data.remote.responses.UserTypeResponse;
import com.avijit.rms1.repository.UserRepository;
import com.avijit.rms1.repository.UserTypeRepository;

public class SignUpViewModel extends AndroidViewModel {
    private UserTypeRepository userTypeRepository;
    private UserRepository userRepository;

    public SignUpViewModel(@NonNull Application application) {
        super(application);
        userRepository= UserRepository.getInstance();
        userTypeRepository = UserTypeRepository.getInstance();
    }
    public MutableLiveData<UserTypeResponse> getUserTypes(){
        return userTypeRepository.getUserTypes();
    }
    public MutableLiveData<UserStoreResponse> saveUser(User user){
        return userRepository.addUser(user);
    }
    public MutableLiveData<User> getUserByToken(String token){
        return userRepository.getUserByToken(token);
    }
}
