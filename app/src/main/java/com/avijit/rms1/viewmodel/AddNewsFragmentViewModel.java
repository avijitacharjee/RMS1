package com.avijit.rms1.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.avijit.rms1.data.remote.model.News;
import com.avijit.rms1.data.remote.model.NewsSubtype;
import com.avijit.rms1.data.remote.model.NewsType;
import com.avijit.rms1.data.remote.responses.NetworkResponse;
import com.avijit.rms1.repository.NewsRepository;
import com.avijit.rms1.ui.news_fragments.AddNewsSubTypeFragment;

import java.util.List;

/**
 * Created by Avijit Acharjee on 6/10/2020 at 2:19 PM.
 * Email: avijitach@gmail.com.
 */
public class AddNewsFragmentViewModel extends ViewModel {
    NewsRepository newsRepository;
    public AddNewsFragmentViewModel(){
        newsRepository= NewsRepository.getInstance();
    }
    public MutableLiveData<NetworkResponse<News>> storeNews(News news){
        return newsRepository.storeNews(news);
    }
    public MutableLiveData<NetworkResponse<List<NewsType>>> getNewsTypes(){
        return newsRepository.getNewsTypes();
    }
    public MutableLiveData<NetworkResponse<List<NewsSubtype>>> getNewsSubTypes(){
        return newsRepository.getNewsSubTypes();
    }
}
