package com.example.ap2_ex3.repositories;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.ap2_ex3.AppDB;
//import com.example.ap2_ex3.ChatAPI;
import com.example.ap2_ex3.daos.ChatDao;
import com.example.ap2_ex3.R;
import com.example.ap2_ex3.entities.Chat;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ChatsRepository {
    private AppDB appDB;

    private LiveData<List<Chat>> getAllChats;
    private ChatDao chatDao;
//    private ChatListData chatListData;
//    private ChatAPI api;

    private Executor executor = Executors.newSingleThreadExecutor();

    public ChatsRepository(Application application) { // maybe Context context instead?
        appDB = AppDB.getInstance(application);
        chatDao = appDB.chatDao();
        getAllChats = chatDao.getAllChats();
//        chatListData = new ChatListData();
//        api = new ChatAPI(chatListData, chatDao);

    }

//    public void insert(List<Chat> chatList) {
//        new InsertAsyncTask((appDB).execute(chatList));
//    }
//
    public LiveData<List<Chat>> getAllChats() {
        return getAllChats;
    }
//
//    class InsertAsyncTask extends AsyncTask<List<Chat>, Void, Void> {
//        private ChatDao chatDao;
//        InsertAsyncTask(AppDB appDB) {
//            chatDao = appDB.chatDao();
//        }
//        @Override
//        protected Void doInBackground(List<Chat>... lists) {
//            chatDao.insertList(lists[0]);
//            return null;
//        }
//    }


    class ChatListData extends MutableLiveData<List<Chat>> {
        public ChatListData() {
            super();


        }

        @Override
        protected void onActive() {
            super.onActive();

            new Thread(() -> {
                List<Chat> chats = new LinkedList<>();
                for(int i = 0; i < 20; i++) {
                    chats.add(new Chat("Nadav " + i, R.drawable.ic_launcher_background, "", ""));
                }
                setValue(chats);
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//                ChatAPI chatAPI = new ChatAPI();
//                chatAPI.get(this);
            }).start();



//            new Thread(() -> {
//                chatListData.postValue(chatDao.get());
//            }).start();


        }
    }

//    public LiveData<List<Chat>> getAllChats() throws ExecutionException, InterruptedException {
////        return chatListData;
//
//        return chatDao.getAllChats();
//    }

    public Chat get(int id) {
        return chatDao.get(id);
    }

    public void insert(final Chat chat) {
        //api.add(chat);
        executor.execute(() -> chatDao.insert(chat));
    }

    public void insertList(final List<Chat> chatList) {
        executor.execute(() -> chatDao.insertList(chatList));
    }


    public void delete(final Chat chat) {
      //  api.delete(chat);
        executor.execute(() -> chatDao.delete(chat));
    }

    public void update(final Chat chat) {
        //  api.delete(chat);
        executor.execute(() -> chatDao.update(chat));
    }


    public void reload() {
        //api.get(this);

    }

}
