package com.example.ap2_ex3.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.ap2_ex3.entities.Chat;
import com.example.ap2_ex3.repositories.ChatsRepository;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class ChatsViewModel extends AndroidViewModel {

    private ChatsRepository chatsRepository;

    private LiveData<List<Chat>> chats;

    public ChatsViewModel(@NonNull Application application) {
        super(application);
        chatsRepository = new ChatsRepository(application);
        chats = chatsRepository.getAllChats();
    }

    public LiveData<List<Chat>> getAllChats() {
        return chats;
    }

    public void insert(Chat chat) {
        chatsRepository.insert(chat);
    }

    public void insertList(List<Chat> chatList) {
        chatsRepository.insertList(chatList);
    }

    public void delete(Chat chat) {
        chatsRepository.delete(chat);
    }

    public void update(Chat chat) {
        chatsRepository.update(chat);
    }

    public void reload() {
        chatsRepository.reload();
    }
}
