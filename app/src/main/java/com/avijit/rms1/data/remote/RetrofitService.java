package com.avijit.rms1.data.remote;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {
    static {
        initialize();
    }
    static OkHttpClient okHttpClient;
    private static void initialize(){
        okHttpClient =new OkHttpClient.Builder()
                .readTimeout(20, TimeUnit.SECONDS)
                .connectTimeout(20, TimeUnit.SECONDS)
                .build();
    }
    private static Retrofit retrofit = new Retrofit.Builder().
            baseUrl("https://aniksen.me/covidbd/api/").
            addConverterFactory(GsonConverterFactory.create()).
            client(okHttpClient).
            build();
    public static <S> S createService(Class<S> serviceClass)
    {
        return retrofit.create(serviceClass);
    }
}
