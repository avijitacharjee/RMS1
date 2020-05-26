package com.avijit.rms1.repository;

import com.avijit.rms1.data.remote.RetrofitService;
import com.avijit.rms1.data.remote.api.NewsApi;

/**
 * Created by Avijit Acharjee on 5/26/2020.
 */
public class NewsRepository {
    private static NewsRepository newsRepository;
    private NewsApi newsApi;
    public NewsRepository getInstance(){
        if(newsRepository==null){
            newsRepository= new NewsRepository();
        }
        return newsRepository;
    }
    public NewsRepository(){
        newsApi = RetrofitService.createService(NewsApi.class);
    }
}
