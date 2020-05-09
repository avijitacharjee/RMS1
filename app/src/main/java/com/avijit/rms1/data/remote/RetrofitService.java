package com.avijit.rms1.data.remote;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {
    private static Retrofit retrofit = new Retrofit.Builder().
            baseUrl("https://aniksen.me/covidbd/api/").
            addConverterFactory(GsonConverterFactory.create()).
            build();
    public static <S> S createService(Class<S> serviceClass)
    {
        return retrofit.create(serviceClass);
    }
}
