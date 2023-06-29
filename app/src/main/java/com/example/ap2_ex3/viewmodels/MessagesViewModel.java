package com.example.ap2_ex3.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.ap2_ex3.entities.Chat;
import com.example.ap2_ex3.entities.Message;
import com.example.ap2_ex3.repositories.ChatsRepository;
import com.example.ap2_ex3.repositories.MessagesRepository;

import java.util.List;
import java.util.concurrent.ExecutionException;

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

//    public Chat getChat(int id) throws ExecutionException, InterruptedException {
//        return messagesRepository.get(id);

//    }

//    public void insertList(List<Chat> chatList) {
//        messagesRepository.insertList(chatList);
//    }

//    public void insertList(Chat... chats) {
//        messagesRepository.insertList(chats);
//    }

//    public void delete(Chat chat) {
//        messagesRepository.delete(chat);
//    }
//
//    public void update(Message... messages) {
//        messagesRepository.update(messages);
//    }
}
