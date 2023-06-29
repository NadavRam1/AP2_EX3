package com.example.ap2_ex3.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.ap2_ex3.entities.Chat;

import java.util.List;

@Dao
public interface ChatDao {

    @Query("SELECT * FROM ChatsDB")
    LiveData<List<Chat>> getAllChats();

    @Query("SELECT * FROM ChatsDB WHERE id = :id")
    Chat get(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Chat chats);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertList(List<Chat> chats);

    @Update
    void update(Chat... chats);

    @Delete
    void delete(Chat... chats);


}
