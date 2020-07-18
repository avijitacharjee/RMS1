package com.avijit.rms1.viewmodel;

import androidx.lifecycle.ViewModel;

import com.avijit.rms1.repository.UserTypeRepository;

/**
 * Created by Avijit Acharjee on 7/18/2020 at 12:53 PM.
 * Email: avijitach@gmail.com.
 */
public class UserTypeViewModel extends ViewModel {
    UserTypeRepository userTypeRepository;
    public UserTypeViewModel(){
        userTypeRepository=UserTypeRepository.getInstance();
    }

}
