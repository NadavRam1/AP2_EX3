package com.example.ap2_ex3;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.ap2_ex3.daos.ChatDao;
import com.example.ap2_ex3.daos.MessageDao;
import com.example.ap2_ex3.daos.UserDao;
import com.example.ap2_ex3.entities.Chat;
import com.example.ap2_ex3.entities.Message;
import com.example.ap2_ex3.entities.User;

@Database(entities = {Chat.class, User.class, Message.class}, version = 16)
public abstract class AppDB extends RoomDatabase {
    public abstract ChatDao chatDao();
    public abstract UserDao userDao();
    public abstract MessageDao messageDao();

    private static AppDB instance;

    public static synchronized AppDB getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDB.class, "AppDB")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
