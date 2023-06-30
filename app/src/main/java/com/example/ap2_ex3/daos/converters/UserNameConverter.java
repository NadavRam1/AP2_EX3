package com.example.ap2_ex3.daos.converters;

import androidx.room.TypeConverter;

import com.example.ap2_ex3.entities.UserName;

public class UserNameConverter {

    @TypeConverter
    public UserName StringToUserName(String value) {
        return new UserName(value);
    }

    @TypeConverter
    public String userToString(UserName username) {
        return username.getUsername();
    }
}
