package com.avijit.rms1.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.avijit.rms1.data.remote.model.News;
import com.avijit.rms1.data.remote.responses.NetworkResponse;
import com.avijit.rms1.repository.NewsRepository;

/**
 * Created by Avijit Acharjee on 6/8/2020 at 6:54 PM.
 * Email: avijitach@gmail.com.
 */
public class NewsHomeFragmentViewModel extends ViewModel {
    private NewsRepository newsRepository ;
    public NewsHomeFragmentViewModel(){
        newsRepository= NewsRepository.getInstance();
    }
    public MutableLiveData<NetworkResponse<News>> storeNews(News news){
        return newsRepository.storeNews(news);
    }
}
