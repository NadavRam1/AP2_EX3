//package com.example.ap2_ex3;
//
//import androidx.lifecycle.MutableLiveData;
//
//import com.example.ap2_ex3.entities.Chat;
//
//import java.util.List;
//import java.util.concurrent.Executors;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//import retrofit2.Retrofit;
//import retrofit2.converter.gson.GsonConverterFactory;
//
//
//public class ChatAPI {
//    private MutableLiveData<List<Chat>> chatListData;
//    private ChatDao dao;
//    Retrofit retrofit;
//    WebServiceAPI webServiceAPI;
//
//    public ChatAPI(MutableLiveData<List<Chat>> postListData, ChatDao dao) {
//        this.chatListData = postListData;
//        this.dao = dao;
//
//        retrofit = new Retrofit.Builder()
//                .baseUrl(MyApplication.context.getString(R.string.BaseUrl))
//                .callbackExecutor(Executors.newSingleThreadExecutor())
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        webServiceAPI = retrofit.create(WebServiceAPI.class);
//    }
//
//    public void get(MutableLiveData<List<Chat>> chats) {
//        Call<List<Chat>> call = webServiceAPI.getChats();
//        call.enqueue(new Callback<List<Chat>>() {
//            @Override
//            public void onResponse(Call<List<Chat>> call, Response<List<Chat>> response) {
//
////                new Thread(() -> {
////                    dao.get().clear();
////                    dao.insertList(response.body());
////                    ChatAPI.this.chatListData.postValue(dao.get());
////                }).start();
//            }
//
//            @Override
//            public void onFailure(Call<List<Chat>> call, Throwable t) {
//            }
//        });
//    }
//}
