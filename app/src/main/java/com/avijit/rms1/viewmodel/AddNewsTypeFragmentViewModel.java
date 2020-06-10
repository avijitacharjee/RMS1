package com.avijit.rms1.viewmodel;

import android.widget.EditText;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.avijit.rms1.data.remote.model.NewsType;
import com.avijit.rms1.data.remote.responses.NetworkResponse;
import com.avijit.rms1.repository.NewsRepository;

/**
 * Created by Avijit Acharjee on 6/8/2020 at 12:49 PM.
 * Email: avijitach@gmail.com.
 */
public class AddNewsTypeFragmentViewModel extends ViewModel {
    NewsRepository newsRepository;
    public AddNewsTypeFragmentViewModel(){
        if(newsRepository==null){
            newsRepository= NewsRepository.getInstance();
        }
    }
    public MutableLiveData<NetworkResponse<NewsType>> storeNewsType(NewsType newsType){
        return newsRepository.storeNewsType(newsType);
    }
}
