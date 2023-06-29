package com.example.ap2_ex3.repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.ap2_ex3.AppDB;
import com.example.ap2_ex3.daos.MessageDao;
import com.example.ap2_ex3.entities.Message;

import java.util.List;

public class MessagesRepository {
    private final LiveData<List<Message>> allMessages;
    private final MessageDao messageDao;



    public MessagesRepository(Application application) {
        AppDB appDB = AppDB.getInstance(application);
        messageDao = appDB.messageDao();
        allMessages = messageDao.getAllMessages();
    }

    public LiveData<List<Message>> getAllMessages() {
        return allMessages;
    }

//    public Message get(int id) {
//        return messageDao.get(id);
//    }

    public void insert(final Message message) {
        new AddMessageAsyncTask(messageDao).execute(message);
    }

//    public void insertList(final List<Message> messageList) {
//        executor.execute(() -> messageDao.insertList(messageList));
//    }



//    public void update(final User user) {
//        //  api.delete(chat);
//        executor.execute(() -> userDao.update(user));
//    }

    private static class AddMessageAsyncTask extends AsyncTask<Message, Void, Void> {

        private MessageDao messageDao;
        private AddMessageAsyncTask(MessageDao messageDao) {
            this.messageDao = messageDao;
        }
        @Override
        protected Void doInBackground(Message... messages) {
            messageDao.insert(messages[0]);
            return null;
        }
    }
}
