package com.example.ap2_ex3;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.ap2_ex3.daos.ChatDao;
import com.example.ap2_ex3.entities.Chat;

@Database(entities = {Chat.class}, version = 2)
public abstract class AppDB extends RoomDatabase {
    public abstract ChatDao chatDao();

    private static AppDB instance;

    public static synchronized AppDB getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDB.class, "ChatsDB")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

}
