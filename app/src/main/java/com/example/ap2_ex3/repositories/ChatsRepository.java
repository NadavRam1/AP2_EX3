package com.example.ap2_ex3.repositories;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.room.Insert;

import com.example.ap2_ex3.AppDB;
//import com.example.ap2_ex3.ChatAPI;
import com.example.ap2_ex3.daos.ChatDao;
import com.example.ap2_ex3.entities.Chat;

import java.util.List;
import java.util.Vector;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ChatsRepository {

    private LiveData<List<Chat>> allChats;
    private ChatDao chatDao;
//    private ChatListData chatListData;
//    private ChatAPI api;

    private Executor executor = Executors.newSingleThreadExecutor();

    public ChatsRepository(Application application) { // maybe Context context instead?
        AppDB appDB = AppDB.getInstance(application);
        chatDao = appDB.chatDao();
        allChats = chatDao.getAllChats();

//        chatListData = new ChatListData();
//        api = new ChatAPI(chatListData, chatDao);

    }


    public LiveData<List<Chat>> getAllChats() {
//        new GetAllChatsAsyncTask(chatDao).execute();
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
        //  api.delete(chat);
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

//    public void insert(List<Chat> chatList) {
//        new InsertAsyncTask((appDB).execute(chatList));
//    }
//

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


//    class ChatListData extends MutableLiveData<List<Chat>> {
//        public ChatListData() {
//            super();
//
//
//        }
//
//        @Override
//        protected void onActive() {
//            super.onActive();
//
//            new Thread(() -> {
//                List<Chat> chats = new LinkedList<>();
//                for(int i = 0; i < 20; i++) {
//                    chats.add(new Chat("Nadav " + i, R.drawable.ic_launcher_background, "", ""));
//                }
//                setValue(chats);
//                try {
//                    Thread.sleep(10000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
////                ChatAPI chatAPI = new ChatAPI();
////                chatAPI.get(this);
//            }).start();
//
//
//
////            new Thread(() -> {
////                chatListData.postValue(chatDao.get());
////            }).start();
//
//
//        }
//    }

//    public LiveData<List<Chat>> getAllChats() throws ExecutionException, InterruptedException {
////        return chatListData;
//
//        return chatDao.getAllChats();
//    }



//    private static class InsertTask extends AsyncTask<Chat, Void, Void> {
//
//        private ChatDao chatDao;
//
//        public InsertTask(ChatDao chatDao) {
//            this.chatDao = chatDao;
//        }
//        @Override
//        protected Void doInBackground(Chat... chats) {
//            chatDao.insert(chats[0]);
//            return null;
//        }
//    }

}
