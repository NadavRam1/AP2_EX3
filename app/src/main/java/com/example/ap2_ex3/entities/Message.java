package com.example.ap2_ex3.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.ap2_ex3.daos.converters.UserNameConverter;

@Entity(tableName = "MessagesDB", indices = @Index(value = {"id"},unique = true))
@TypeConverters({UserNameConverter.class})
public class Message {
    @PrimaryKey
    private int id;
    private String content;
    private String created;

    private UserName sender;

    public Message(int id, String content, String created, UserName sender) {
        this.id = id;
        this.content = content;
        this.created = created;
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

    public String getCreated() {
        return created;
    }

    public void setCreated(String time) {
        this.created = time;
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
        return "{" + id + "," + created +
                "," + content + "," + sender.getUsername() + "}";
    }
}
