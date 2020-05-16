package com.avijit.rms1.data.remote.api;

import com.avijit.rms1.data.remote.model.User;
import com.avijit.rms1.data.remote.responses.UserStoreResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserApi {
    @DELETE("deleteuser/{id}")
    Call<User> delete(@Path("id") String id);

    @GET("getuser/{id}")
    Call<User> getUser(@Path("id") String id);

    @GET("allusers")
    Call<User> allUser();

    @POST("saveuser")
    Call<UserStoreResponse> storeUser(@Body User user);

    @POST("updateuser/{id}")
    Call<User> update(@Path("id") String id,@Body User user);

}
