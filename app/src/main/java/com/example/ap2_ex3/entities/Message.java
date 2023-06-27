package com.example.ap2_ex3.entities;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "MessagesDB", indices = @Index(value = {"id"},unique = true))
public class Message {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String content;
    private String time;

    private User sender;

    public Message(int id, String content, String time, User sender) {
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

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }
}
