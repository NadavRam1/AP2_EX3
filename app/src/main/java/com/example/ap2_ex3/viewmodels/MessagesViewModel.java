package com.example.ap2_ex3.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.ap2_ex3.entities.Message;
import com.example.ap2_ex3.repositories.MessagesRepository;

import java.util.List;

public class MessagesViewModel extends AndroidViewModel {
    private MessagesRepository messagesRepository;
    private final LiveData<List<Message>> messages;

    public MessagesViewModel(@NonNull Application application) {
        super(application);
        messagesRepository = new MessagesRepository(application);
        messages = messagesRepository.getAllMessages();
    }

    public LiveData<List<Message>> getAllMessages() {
        return messages;
    }

    public void insert(Message message) {
        messagesRepository.insert(message);
    }

    public void insertList(List<Message> chatList) {
        messagesRepository.insertList(chatList);
    }

    public void deleteAll() {
        messagesRepository.deleteAll();
    }
}
