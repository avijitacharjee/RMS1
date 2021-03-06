package com.avijit.rms1.data.remote.api;

import com.avijit.rms1.data.remote.model.News;
import com.avijit.rms1.data.remote.model.NewsSubtype;
import com.avijit.rms1.data.remote.model.NewsType;
import com.avijit.rms1.data.remote.responses.NetworkResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface NewsApi {
    //News Type
    @GET("types")
    Call<NetworkResponse<List<NewsType>>> getNewsTypes();
    @POST("storenewstypes")
    Call<NetworkResponse<NewsType>> storeNewsType(@Body NewsType newsType);
    @POST("newstypesupdate/{id}")
    Call<NetworkResponse<NewsType>> updateNewsType(@Path("id") String id,@Body NewsType newsType);
    @GET("newstypesdelete/{id}")
    Call<NetworkResponse<NewsType>> deleteNewsType(@Path("id") String id);

    //News Subtype
    @GET("subtypes")
    Call<NetworkResponse<List<NewsSubtype>>> getNewsSubTypes();
    @POST("storenewssubtypes")
    Call<NetworkResponse<NewsSubtype>> storeNewsSubTypes(@Body NewsSubtype newsSubtype);
    @POST("newssubtypesupdate/{id}")
    Call<NetworkResponse<NewsSubtype>> updateNewsSubType(@Path("id") String id,@Body NewsSubtype newsSubtype);
    @GET("newssubtypesdelete/{id}")
    Call<NetworkResponse<NewsSubtype>> deleteNewsSubType(@Path("id") String id);

    //News
    @POST("newsstore")
    Call<NetworkResponse<News>> storeNews(@Body News news);
    @GET("newsshow")
    Call<NetworkResponse<List<News>>> getAllNews();
}
