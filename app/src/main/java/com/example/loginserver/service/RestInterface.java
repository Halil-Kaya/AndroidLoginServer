package com.example.loginserver.service;

import com.example.loginserver.model.RefreshToken;
import com.example.loginserver.model.Token;
import com.example.loginserver.model.User;
import com.example.loginserver.model.UserLogin;
import com.example.loginserver.model.UserRegister;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface RestInterface {

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("user")
    Call<User> getUser(@Header("Authorization") String auth);

    @POST("user")
    Call<User> createUser(@Body UserRegister userRegister);

    @POST("login/AccessToken")
    Call<Token> getToken(@Body UserLogin userLogin);

    @POST("login/RefreshToken")
    Call<Token> refreshToken(@Body RefreshToken refreshToken);



}
