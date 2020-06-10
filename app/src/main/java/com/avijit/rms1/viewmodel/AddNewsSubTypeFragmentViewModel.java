package com.avijit.rms1.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.avijit.rms1.data.remote.model.NewsSubtype;
import com.avijit.rms1.data.remote.responses.NetworkResponse;
import com.avijit.rms1.repository.NewsRepository;

/**
 * Created by Avijit Acharjee on 6/10/2020 at 12:44 PM.
 * Email: avijitach@gmail.com.
 */
public class AddNewsSubTypeFragmentViewModel extends ViewModel {
    NewsRepository newsRepository ;
    public AddNewsSubTypeFragmentViewModel(){
        newsRepository= NewsRepository.getInstance();
    }
    public MutableLiveData<NetworkResponse<NewsSubtype>> storeNewsSubType(NewsSubtype newsSubtype){
        return newsRepository.storeNewsSubType(newsSubtype);
    }
}
