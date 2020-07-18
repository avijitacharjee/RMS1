package com.avijit.rms1.data.remote.api;

import com.avijit.rms1.data.remote.model.User;
import com.avijit.rms1.data.remote.responses.NetworkResponse;
import com.avijit.rms1.data.remote.responses.UserStoreResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserApi {
    @GET("user")
    Call<User> getUserByHeader(@Header("Authorization")String header);
    @DELETE("deleteuser/{id}")
    Call<NetworkResponse<User>> delete(@Path("id") String id);

    @GET("getuser/{id}")
    Call<User> getUser(@Path("id") String id);

    @GET("allusers")
    Call<NetworkResponse<List<User>>> allUser();

    @POST("saveuser")
    Call<NetworkResponse<User>> storeUser(@Body User user);

    @POST("updateuser/{id}")
    Call<NetworkResponse<User>> update(@Path("id") String id,@Body User user);

}
