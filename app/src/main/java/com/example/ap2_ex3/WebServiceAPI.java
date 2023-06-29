package com.example.ap2_ex3;


import com.example.ap2_ex3.entities.Chat;
import com.example.ap2_ex3.entities.User;
import com.example.ap2_ex3.entities.UserCredentials;
import com.example.ap2_ex3.entities.UserName;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface WebServiceAPI {

    @GET("api/Chats")
    Call<List<Chat>> getAllChats(@Header("Authorization") String token);

    @POST("api/Chats")
    Call<Chat> createChat(@Header("Authorization") String token, @Body UserName username);

    @GET("api/Chats/{id}")
    Call<Chat> getChat(@Header("Authorization") String token, @Path("id") int id);

    @DELETE("api/chats/{id}")
    Call<Void> deleteChat(@Header("Authorization") String token, @Path("id") int id);

    @POST("api/Chats/{id}/Messages")
    Call<Void> createMessage(@Header("Authorization") String token, @Path("id") int id);

    @GET("api/Chats/{id}/Messages")
    Call<Void> getMessage(@Header("Authorization") String token, @Path("id") int id);

    @POST("api/Tokens")
    Call<String> createToken(@Body UserCredentials user);

    @GET("api/Users/{username}")
    Call<Void> getUser(@Header("Authorization") String token, @Path("username") String username);

    @POST("/api/Users")
    Call<Void> createUser(@Body User user); //need user object?




}
