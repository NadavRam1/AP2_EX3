package com.example.ap2_ex3.daos.converters;

import android.util.Log;

import androidx.room.TypeConverter;

import com.example.ap2_ex3.entities.DisplayedUser;

import java.util.Arrays;
import java.util.List;

public class DisplayedUserConverter {
    @TypeConverter
    public DisplayedUser StringToDisplayedUser(String value) {
        List<String> userList = Arrays.asList(value.split(","));

        return new DisplayedUser(userList.get(0), userList.get(1), userList.get(2));
    }

    @TypeConverter
    public String displayedUserToString(DisplayedUser user) {
        return user.getUsername() + ","
                + user.getDisplayName() + ","
                + user.getProfilePic();
    }
}
