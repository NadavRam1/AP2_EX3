package com.example.ap2_ex3.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.ap2_ex3.AppDB;
import com.example.ap2_ex3.daos.MessageDao;
import com.example.ap2_ex3.daos.UserDao;
import com.example.ap2_ex3.entities.Message;
import com.example.ap2_ex3.entities.User;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MessagesRepository {

    private AppDB appDB;

    private LiveData<List<Message>> getAllMessages;
    private MessageDao messageDao;

    private Executor executor = Executors.newSingleThreadExecutor();

    public MessagesRepository(Application application) {
        appDB = AppDB.getInstance(application);
        messageDao = appDB.messageDao();
        executor.execute(() -> getAllMessages = messageDao.getAllMessages());
    }

    public LiveData<List<Message>> getAllMessages() {
        return getAllMessages;
    }

    public Message get(int id) {
        return messageDao.get(id);
    }

    public void insert(final Message message) {
        executor.execute(() -> messageDao.insert(message));
    }

    public void insertList(final List<Message> messageList) {
        executor.execute(() -> messageDao.insertList(messageList));
    }



//    public void update(final User user) {
//        //  api.delete(chat);
//        executor.execute(() -> userDao.update(user));
//    }
}
