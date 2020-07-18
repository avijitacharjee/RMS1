package com.avijit.rms1.data.remote.api;

import androidx.room.Delete;

import com.avijit.rms1.data.remote.model.User;
import com.avijit.rms1.data.remote.model.UserType;
import com.avijit.rms1.data.remote.responses.NetworkResponse;
import com.avijit.rms1.data.remote.responses.UserTypeResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserTypeApi {

    @GET("user-type")
    Call<NetworkResponse<List<UserType>>> allTypes();

    @GET("user-type/{id}")
    Call<NetworkResponse<UserType>> getTypeById(@Path("id") String id);

    @POST("user-type")
    Call<NetworkResponse<UserType>> saveUserType(@Body UserType userType);

    @POST("user-type/{id}")
    Call<NetworkResponse<UserType>> updateUserType(@Path("id") String id,@Body UserType userType);

    @DELETE("user-type/{id}")
    Call<NetworkResponse<UserType>> deleteType(@Path("id") String id);


}
