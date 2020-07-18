package com.avijit.rms1.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.avijit.rms1.data.remote.model.User;
import com.avijit.rms1.data.remote.responses.NetworkResponse;
import com.avijit.rms1.repository.UserRepository;

import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Avijit Acharjee on 7/19/2020 at 12:38 AM.
 * Email: avijitach@gmail.com.
 */
public class UserCrudViewModel extends ViewModel {
    UserRepository userRepository;
    public UserCrudViewModel(){
        userRepository= UserRepository.getInstance();
    }
    public MutableLiveData<NetworkResponse<User>> create(User user){
        return userRepository.addUser(user);
    }
    public MutableLiveData<NetworkResponse<List<User>>> getAll(){
        return userRepository.getAllUser();
    }
    public MutableLiveData<NetworkResponse<User>> update(String id,User user){
        return userRepository.updateUser(id, user);
    }
    public MutableLiveData<NetworkResponse<User>> delete(String id){
        return userRepository.deleteUser(id);
    }
}
