package com.example.ap2_ex3.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.ap2_ex3.entities.Chat;
import com.example.ap2_ex3.entities.User;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM UsersDB")
    List<User> getAllUsers();

    @Query("SELECT * FROM UsersDB WHERE username = :username")
    User get(String username);

    @Insert
    void insert(User... users);

    @Insert
    void insertList(List<User> userList);

    @Update
    void update(User... users);

    @Delete
    void delete(User... users);
}
