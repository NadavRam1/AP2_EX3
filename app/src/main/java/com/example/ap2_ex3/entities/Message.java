package com.example.ap2_ex3.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.ap2_ex3.converters.UserNameConverter;

@Entity(tableName = "MessagesDB", indices = @Index(value = {"id"},unique = true))
@TypeConverters({UserNameConverter.class})
public class Message {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String content;
    private String time;

    private UserName sender;

    public Message(int id, String content, String time, UserName sender) {
        this.id = id;
        this.content = content;
        this.time = time;
        this.sender = sender;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public UserName getSender() {
        return sender;
    }

    public void setSender(UserName sender) {
        this.sender = sender;
    }

    @NonNull
    @Override
    public String toString() {
        return "{" + id + ", " + time +
                ", " + content + ", " + sender.getUsername() + "}";
    }
}
