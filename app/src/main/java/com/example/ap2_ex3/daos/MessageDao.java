package com.example.ap2_ex3.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.ap2_ex3.entities.Message;

import java.util.List;

@Dao
public interface MessageDao {

    @Query("SELECT * FROM MessagesDB")
    LiveData<List<Message>> getAllMessages();

    @Insert
    void insert(Message... messages);

    @Insert
    void insertList(List<Message> messageList);

    @Query("SELECT * FROM MessagesDB WHERE id = :id")
    Message get(int id);

}
