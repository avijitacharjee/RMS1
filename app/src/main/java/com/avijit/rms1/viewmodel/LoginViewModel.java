package com.avijit.rms1.viewmodel;

import android.telecom.StatusHints;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.avijit.rms1.data.remote.model.AuthBody;
import com.avijit.rms1.data.remote.model.User;
import com.avijit.rms1.data.remote.responses.AuthResponse;
import com.avijit.rms1.repository.AuthRepository;
import com.avijit.rms1.repository.UserRepository;
import com.google.gson.internal.$Gson$Types;

public class LoginViewModel extends ViewModel {
    private MutableLiveData<AuthResponse> user;
    private AuthRepository authRepository;
    private UserRepository userRepository;
    public void init(){
        if(user!=null){
            return;
        }
        authRepository = AuthRepository.getInstance();
        userRepository = UserRepository.getInstance();
        user = new MutableLiveData<>();
    }
    public MutableLiveData<AuthResponse> getAuth(AuthBody authBody){
        return authRepository.getAuth(authBody);
    }
    public MutableLiveData<Boolean> isUser(AuthBody user){
        MutableLiveData<Boolean> isUser = new MutableLiveData<>();
        this.user.setValue( authRepository.getAuth(user).getValue());
        isUser.setValue(!this.user.getValue().getAccessToken().equals(""));

        return isUser;
    }
    public MutableLiveData<AuthResponse> getUser(){
        return this.user;
    }
    public MutableLiveData<User> getUserByToken(String token){
        return userRepository.getUserByToken(token);
    }
}
