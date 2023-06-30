package com.example.ap2_ex3.converters;

import androidx.room.TypeConverter;

import com.example.ap2_ex3.entities.User;

import java.util.Arrays;
import java.util.List;

public class UserConverter {

    @TypeConverter
    public User StringToUser(String value) {
        List<String> userList = Arrays.asList(value.split(","));
        return new User(userList.get(0), userList.get(1), userList.get(2), userList.get(3));
    }

    @TypeConverter
    public String userToString(User user) {
        return user.getUsername() + ","
                + user.getPassword() + ","
                + user.getDisplayName() + ","
                + user.getProfilePic();
    }
}
