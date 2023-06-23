package com.example.ap2_ex3;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class ChatsViewModel extends ViewModel {

    private ChatsRepository mRepository;

    private LiveData<List<Chat>> chats;

    public ChatsViewModel() {
        mRepository = new ChatsRepository();
        chats = mRepository.getAll();



    }

    public LiveData<List<Chat>> get() {
        return chats;
    }

    public void add(Chat chat) {
        mRepository.add(chat);
    }

    public void delete(Chat chat) {
        mRepository.delete(chat);
    }

    public void reload() {
        mRepository.reload();
    }
}
