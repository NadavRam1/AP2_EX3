package com.example.ap2_ex3.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.ap2_ex3.converters.MessagesConverter;
import com.example.ap2_ex3.converters.UserConverter;

import java.util.List;

@Entity(tableName = "ChatsDB", indices = @Index(value = {"id"},unique = true))
@TypeConverters({UserConverter.class, MessagesConverter.class})
public class Chat {

    @PrimaryKey
    private int id;
    private User user;
    private List<Message> messages;

    public Chat(int id, User user, List<Message> messages) {
        this.id = id;
        this.user = user;
        this.messages = messages;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public Message getLastMessage() {
        return messages.get(messages.size() - 1);
    }

    @NonNull
    @Override
    public String toString() {
        return "{" + id + ", " + user +
                ", " + messages.toString() + "}";
    }
}
