package com.example.ap2_ex3.repositories;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.ap2_ex3.AppDB;
import com.example.ap2_ex3.daos.ChatDao;
import com.example.ap2_ex3.daos.MessageDao;
import com.example.ap2_ex3.entities.Message;

import java.util.Arrays;
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

    public void insert(final Message message) {
        new AddMessageAsyncTask(messageDao).execute(message);
    }

    public void insertList(final List<Message> messageList) {
        new AddMessagesAsyncTask(messageDao).execute(messageList);
    }

    public void deleteAll() {
        new DeleteAllMessagesAsyncTask(messageDao).execute();
    }

    private static class AddMessagesAsyncTask extends AsyncTask<List<Message>, Void, Void> {
        private MessageDao messageDao;

        private AddMessagesAsyncTask(MessageDao messageDao) {
            this.messageDao = messageDao;
        }

        @Override
        protected Void doInBackground(List<Message>... messages) {
            Log.d("messagesInRepository", messages[0].toString());
            messageDao.insertList(messages[0]);
            return null;
        }
    }

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

    private static class DeleteAllMessagesAsyncTask extends AsyncTask<Void, Void, Void> {
        private MessageDao messageDao;

        private DeleteAllMessagesAsyncTask(MessageDao messageDao) {
            this.messageDao = messageDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            messageDao.deleteTable();
            return null;
        }
    }
}
