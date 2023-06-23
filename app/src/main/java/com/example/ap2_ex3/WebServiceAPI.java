package com.example.ap2_ex3;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface WebServiceAPI {

    @GET("chats")
    Call<List<Chat>> getChats();

    @POST("chats")
    Call<Void> createChat(@Body Chat chat);

    @DELETE("chats/{id}")
    Call<Void> deleteChat(@Path("id") int id);


}
