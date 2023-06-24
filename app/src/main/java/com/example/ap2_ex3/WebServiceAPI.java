package com.example.ap2_ex3;


import com.example.ap2_ex3.entities.Chat;
import com.example.ap2_ex3.entities.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface WebServiceAPI {

    @GET("api/Chats")
    Call<List<Chat>> getAllChats();

    @POST("api/Chats")
    Call<Void> createChat(@Body Chat chat);

    @GET("api/Chats/{id}")
    Call<Chat> getChat(@Path("id") int id);

    @DELETE("chats/{id}")
    Call<Void> deleteChat(@Path("id") int id);

    @POST("api/Chats/{id}/Messages")
    Call<Void> createMessage(@Path("id") int id);

    @GET("api/Chats/{id}/Messages")
    Call<Void> getMessage(@Path("id") int id);

    @POST("api/Tokens")
    Call<Void> createToken(@Body String s);

    @GET("api/Users/{username}")
    Call<Void> getUser(@Path("username") String username);

    @POST("/api/Users")
    Call<User> createUser(@Body User user); //need user object?




}
