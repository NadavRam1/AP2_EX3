package com.example.ap2_ex3.repositories;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.ap2_ex3.AppDB;
import com.example.ap2_ex3.daos.ChatDao;
import com.example.ap2_ex3.entities.Chat;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class ChatsRepository {
    private final LiveData<List<Chat>> allChats;
    private final ChatDao chatDao;

    public ChatsRepository(Application application) {
        AppDB appDB = AppDB.getInstance(application);
        chatDao = appDB.chatDao();
        allChats = chatDao.getAllChats();
    }

    public LiveData<List<Chat>> getAllChats() {
        return allChats;
    }

    public Chat get(int id) throws ExecutionException, InterruptedException {
        return new GetChatAsyncTask(chatDao).execute(id).get();
    }

    public void insert(Chat chat) {
        new InsertChatAsyncTask(chatDao).execute(chat);
    }

    public void insertList(List<Chat> chats) {
        new InsertChatsAsyncTask(chatDao).execute(chats);
    }

    public void update(final Chat... chat) {
        new UpdateChatAsyncTask(chatDao).execute(chat);
    }

    public void deleteAll() {
        new DeleteAllChatsAsyncTask(chatDao).execute();
    }

    private static class InsertChatAsyncTask extends AsyncTask<Chat, Void, Void> {
        private final ChatDao chatDao;

        private InsertChatAsyncTask(ChatDao chatDao) {
            this.chatDao = chatDao;
        }

        @Override
        protected Void doInBackground(Chat... chats) {
            chatDao.insert(chats[0]);
            return null;
        }
    }

    private static class InsertChatsAsyncTask extends AsyncTask<List<Chat>, Void, Void> {
        private final ChatDao chatDao;

        private InsertChatsAsyncTask(ChatDao chatDao) {
            this.chatDao = chatDao;
        }

        @Override
        protected Void doInBackground(List<Chat>... chats) {
            chatDao.insertList(chats[0]);
            return null;
        }
    }

    public static class GetChatAsyncTask extends AsyncTask<Integer, Void, Chat> {
        private ChatDao chatDao;

        private GetChatAsyncTask(ChatDao chatDao) {
            this.chatDao = chatDao;
        }

        @Override
        protected Chat doInBackground(Integer... integers) {
            Log.d("get", "background: in");
            return chatDao.get(integers[0]);
        }
    }

    private static class GetAllChatsAsyncTask extends AsyncTask<Void, Void, LiveData<List<Chat>>> {
        private ChatDao chatDao;

        private GetAllChatsAsyncTask(ChatDao chatDao) {
            this.chatDao = chatDao;
        }

        @Override
        protected LiveData<List<Chat>> doInBackground(Void... voids) {
            return chatDao.getAllChats();
        }
    }

    private static class UpdateChatAsyncTask extends AsyncTask<Chat, Void, Void> {
        private ChatDao chatDao;

        private UpdateChatAsyncTask(ChatDao chatDao) {
            this.chatDao = chatDao;
        }

        @Override
        protected Void doInBackground(Chat... chats) {
            chatDao.update(chats);
            return null;
        }
    }

    private static class DeleteAllChatsAsyncTask extends AsyncTask<Void, Void, Void> {
        private ChatDao chatDao;

        private DeleteAllChatsAsyncTask(ChatDao chatDao) {
            this.chatDao = chatDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            chatDao.deleteTable();
            return null;
        }
    }
}
