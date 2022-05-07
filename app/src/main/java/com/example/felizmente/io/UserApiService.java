package com.example.felizmente.io;

import com.example.felizmente.beans.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface UserApiService {

    @GET("users/registration/{email}")
    Call<User> search(@Path("email")String email);

    @GET("users/login/{token}")
    Call<User> searchCoincidence(@Path("token") String token);

    @POST("users/")
    Call<User> addUser(@Body User u);
}
