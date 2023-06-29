package com.example.ap2_ex3.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.ap2_ex3.converters.DisplayedUserConverter;
import com.example.ap2_ex3.converters.MessagesConverter;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity(tableName = "ChatsDB", indices = @Index(value = {"id"},unique = true))
@TypeConverters({DisplayedUserConverter.class, MessagesConverter.class})
public class Chat {

    @PrimaryKey
    private int id;
    private DisplayedUser user;
    private List<Message> messages;

    public Chat(int id, DisplayedUser user, List<Message> messages) {
        this.id = id;
        this.user = user;
        Collections.reverse(messages);
        this.messages = messages;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }

    public DisplayedUser getUser() {
        return user;
    }

    public void setUser(DisplayedUser user) {
        this.user = user;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public Message getLastMessage() {
        if (messages.isEmpty()) {
            return null;
        }
        return messages.get(messages.size() - 1);
    }

    @NonNull
    @Override
    public String toString() {
        return "{" + id + ", " + user +
                ", " + messages.toString() + "}";
    }
}
