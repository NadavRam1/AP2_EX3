package com.example.ap2_ex3.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.ap2_ex3.entities.Message;

import java.util.List;

import retrofit2.http.GET;

@Dao
public interface MessageDao {

    @Query("SELECT * FROM MessagesDB")
    LiveData<List<Message>> getAllMessages();

    @Insert
    void insert(Message... messages);

    @Insert
    void insertList(List<Message> messageList);

    @GET
    Message get(int id);

}
