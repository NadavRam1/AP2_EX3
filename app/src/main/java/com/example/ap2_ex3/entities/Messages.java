package com.example.ap2_ex3.entities;

import java.util.List;

public class Messages {
    private List<Message> messages;

    public Messages(List<Message> messages) {
        this.messages = messages;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public void addMessage(Message message) {
        this.messages.add(message);
    }

    public Message getLastMessage() {
        return messages.get(messages.size() - 1);
    }
}
