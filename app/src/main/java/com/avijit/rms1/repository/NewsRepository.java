package com.avijit.rms1.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.avijit.rms1.data.remote.RetrofitService;
import com.avijit.rms1.data.remote.api.NewsApi;
import com.avijit.rms1.data.remote.model.News;
import com.avijit.rms1.data.remote.model.NewsSubtype;
import com.avijit.rms1.data.remote.model.NewsType;
import com.avijit.rms1.data.remote.responses.NetworkResponse;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Avijit Acharjee on 5/26/2020.
 */
public class NewsRepository {
    private static final String TAG = "NewsRepository";
    private static NewsRepository newsRepository;
    private NewsApi newsApi;

    public static NewsRepository getInstance() {
        if (newsRepository == null) {
            newsRepository = new NewsRepository();
        }
        return newsRepository;
    }

    private NewsRepository() {
        newsApi = RetrofitService.createService(NewsApi.class);
    }

    public MutableLiveData<NetworkResponse<NewsType>> storeNewsType(NewsType newsType) {
        final MutableLiveData<NetworkResponse<NewsType>> result = new MutableLiveData<>();
        newsApi.storeNewsType(newsType).enqueue(new Callback<NetworkResponse<NewsType>>() {
            @Override
            public void onResponse(Call<NetworkResponse<NewsType>> call, Response<NetworkResponse<NewsType>> response) {
                if (response.isSuccessful()) result.setValue(response.body());
                else result.setValue(new NetworkResponse<>(false));
            }

            @Override
            public void onFailure(Call<NetworkResponse<NewsType>> call, Throwable t) {
                result.setValue(new NetworkResponse<>(false));
            }
        });
        return result;
    }
    public MutableLiveData<NetworkResponse<News>> storeNews(News news){
        final MutableLiveData<NetworkResponse<News>> result = new MutableLiveData<>();
        newsApi.storeNews(news).enqueue(new Callback<NetworkResponse<News>>() {
            @Override
            public void onResponse(Call<NetworkResponse<News>> call, Response<NetworkResponse<News>> response) {
                Log.d(TAG, "onResponse: "+response.toString());
                if(response.isSuccessful()){
                    result.setValue(response.body());
                }
                else {
                    result.setValue(new NetworkResponse<>(false));
                }
            }

            @Override
            public void onFailure(Call<NetworkResponse<News>> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t);
                result.setValue(new NetworkResponse<>(false));
            }
        });
        newsApi.store(news).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.d(TAG, "onResponse: "+new Gson().toJson(response));
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d(TAG, "onFailure: "+new Gson().toJson(t));
            }
        });
        return result;
    }
    public MutableLiveData<NetworkResponse<NewsSubtype>> storeNewsSubType(NewsSubtype subtype){
        MutableLiveData<NetworkResponse<NewsSubtype>> result = new MutableLiveData<>();
        newsApi.storeNewsSubTypes(subtype).enqueue(new Callback<NetworkResponse<NewsSubtype>>() {
            @Override
            public void onResponse(Call<NetworkResponse<NewsSubtype>> call, Response<NetworkResponse<NewsSubtype>> response) {
                Log.d(TAG, "onResponse: "+response.toString());
                result.setValue(response.isSuccessful()?response.body():new NetworkResponse<>(false));
            }

            @Override
            public void onFailure(Call<NetworkResponse<NewsSubtype>> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t);
                result.setValue(new NetworkResponse<>(false));
            }
        });
        return result;
    }
    public MutableLiveData<NetworkResponse<List<NewsType>>> getNewsTypes(){
        MutableLiveData<NetworkResponse<List<NewsType>>> result = new MutableLiveData<>();
        newsApi.getNewsTypes().enqueue(new Callback<NetworkResponse<List<NewsType>>>() {
            @Override
            public void onResponse(Call<NetworkResponse<List<NewsType>>> call, Response<NetworkResponse<List<NewsType>>> response) {
                Log.d(TAG, "onResponse: "+response.toString());
                result.setValue(response.isSuccessful()?response.body():new NetworkResponse<>(false));
            }

            @Override
            public void onFailure(Call<NetworkResponse<List<NewsType>>> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t);
                result.setValue(new NetworkResponse<>(false));
            }
        });
        return result;
    }
    public MutableLiveData<NetworkResponse<List<NewsSubtype>>> getNewsSubTypes(){
        MutableLiveData<NetworkResponse<List<NewsSubtype>>> result = new MutableLiveData<>();
        newsApi.getNewsSubTypes().enqueue(new Callback<NetworkResponse<List<NewsSubtype>>>() {
            @Override
            public void onResponse(Call<NetworkResponse<List<NewsSubtype>>> call, Response<NetworkResponse<List<NewsSubtype>>> response) {
                result.setValue(response.isSuccessful()?response.body():new NetworkResponse<>(false));
            }

            @Override
            public void onFailure(Call<NetworkResponse<List<NewsSubtype>>> call, Throwable t) {
                result.setValue(new NetworkResponse<>(false));
            }
        });
        return result;
    }

}
