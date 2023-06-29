package com.example.ap2_ex3.repositories;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.ap2_ex3.AppDB;
import com.example.ap2_ex3.daos.ChatDao;
import com.example.ap2_ex3.entities.Chat;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ChatsRepository {
    private LiveData<List<Chat>> allChats;
    private ChatDao chatDao;
    private Executor executor = Executors.newSingleThreadExecutor();

    public ChatsRepository(Application application) {
        AppDB appDB = AppDB.getInstance(application);
        chatDao = appDB.chatDao();
        allChats = chatDao.getAllChats();
    }


    public LiveData<List<Chat>> getAllChats() {
        return allChats;
    }

    public Chat get(int id) {
        return chatDao.get(id);
    }

    public void insert(Chat chat) {
        new InsertChatAsyncTask(chatDao).execute(chat);
    }

//    public void insertList(final Chat... chats) {
//        executor.execute(() -> chatDao.insert(chats));
//    }


//    public void delete(final Chat chat) {
//        //  api.delete(chat);
//        executor.execute(() -> chatDao.delete(chat));
//    }

    public void update(final Chat... chat) {
        new UpdateChatAsyncTask(chatDao).execute(chat);
    }


    private static class InsertChatAsyncTask extends AsyncTask<Chat, Void, Void> {

        private ChatDao chatDao;
        private InsertChatAsyncTask(ChatDao chatDao) {
            this.chatDao = chatDao;
        }
        @Override
        protected Void doInBackground(Chat... chats) {
            Log.d("code", chats.toString());
            chatDao.insert(chats[0]);
            return null;
        }
    }

//    private static class GetAllChatsAsyncTask extends AsyncTask<Void, Void, Void> {
//
//        private ChatDao chatDao;
//        private GetAllChatsAsyncTask(ChatDao chatDao) {
//            this.chatDao = chatDao;
//        }
//        @Override
//        protected Void doInBackground(Void... voids) {
//            chatDao.getAllChats();
//            return null;
//        }
//    }

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
}
