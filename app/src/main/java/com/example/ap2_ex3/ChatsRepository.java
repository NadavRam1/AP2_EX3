package com.example.ap2_ex3;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.LinkedList;
import java.util.List;

public class ChatsRepository {
    private ChatDao dao;
    private ChatListData chatListData;
    private ChatAPI api;

    public ChatsRepository() {
        //AppDB db = AppDB.getInstance();
        //dao = db.chatDao();
        chatListData = new ChatListData();
        api = new ChatAPI(chatListData, dao);

    }

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
//                chatListData.postValue(dao.get());
//            }).start();


        }
    }

    public LiveData<List<Chat>> getAll() {
        return chatListData;

    }


    public void add(final Chat chat) {
        //api.add(chat);

    }


    public void delete(final Chat chat) {
      //  api.delete(chat);

    }


    public void reload() {
        //api.get(this);

    }

}
