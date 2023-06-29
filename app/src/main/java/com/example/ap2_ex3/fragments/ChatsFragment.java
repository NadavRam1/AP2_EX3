package com.example.ap2_ex3.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ap2_ex3.AppDB;
import com.example.ap2_ex3.WebServiceAPI;
import com.example.ap2_ex3.activities.AddContactActivity;
import com.example.ap2_ex3.activities.LoginActivity;
import com.example.ap2_ex3.adapters.ChatAdapter;
import com.example.ap2_ex3.adapters.ChatAdapter2;
import com.example.ap2_ex3.adapters.ChatAdapter3;
import com.example.ap2_ex3.adapters.ChatAdapter4;
import com.example.ap2_ex3.daos.ChatDao;
import com.example.ap2_ex3.entities.Message;
import com.example.ap2_ex3.entities.User;
import com.example.ap2_ex3.entities.UserName;
import com.example.ap2_ex3.repositories.ChatsRepository;
import com.example.ap2_ex3.viewmodels.ChatsViewModel;
import com.example.ap2_ex3.R;
import com.example.ap2_ex3.entities.Chat;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
//
public class ChatsFragment extends Fragment {
//
//    private ChatsViewModel chatsViewModel;
//    private static final String URL_DATA = "http://10.0.2.2:5000/";
    private RecyclerView lstFeed;
    private ChatAdapter4 chatAdapter;
    private List<Chat> chatList;
    private List<Message> messageList;

    private ChatsViewModel chatsViewModel;
//
////    private ListView lstFeed;
//    private static final int REQUEST_CODE = 1;
//    private int tempPosition;
//
//    private AppDB db;
//    private ChatDao chatDao;
////    private ChatAdapter chatAdapter;
//    private ChatsRepository chatsRepository;
//
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chats, container, false);

        lstFeed = view.findViewById(R.id.lstFeed);
        lstFeed.setHasFixedSize(true);

        chatAdapter = new ChatAdapter4();
        lstFeed.setAdapter(chatAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        lstFeed.addItemDecoration(dividerItemDecoration);

        chatsViewModel = ViewModelProviders.of(this).get(ChatsViewModel.class);
        chatsViewModel.getAllChats().observe(getActivity(), chatList -> {
            Toast.makeText(getActivity(), "ahhhhhhhhhh", Toast.LENGTH_LONG).show();
            chatAdapter.setChatList(chatList);
            Log.d("code", chatList.toString());
        });

//        messageList = new ArrayList<>();
//        messageList.add(new Message(1, "hello my friend", "16:33", new UserName("nadav")));
//        messageList.add(new Message(2, "hello to you too", "16:34", new UserName("ori")));
//        messageList.add(new Message(3, "fuck Hemi", "16:33", new UserName("nadav")));
//        messageList.add(new Message(4, "I agree", "16:33", new UserName("ori")));
//        messageList.add(new Message(5, "Mother RUSSIA", "16:33", new UserName("nadav")));
//        messageList.add(new Message(6, "YEET!", "16:33", new UserName("ori")));
//
//        chatList = new ArrayList<>();
//        chatList.add(new Chat(1, new User("nadav", "pass", "nadavv", ""), messageList));
//        chatList.add(new Chat(2, new User("nadav", "pass", "nadavv", ""), messageList));
//        chatList.add(new Chat(3, new User("nadav", "pass", "nadavv", ""), messageList));
//        chatList.add(new Chat(4, new User("nadav", "pass", "nadavv", ""), messageList));
//        chatList.add(new Chat(5, new User("nadav", "pass", "nadavv", ""), messageList));
//        chatList.add(new Chat(6, new User("nadav", "pass", "nadavv", ""), messageList));
//        chatList.add(new Chat(7, new User("nadav", "pass", "nadavv", ""), messageList));




        FloatingActionButton addButton = view.findViewById(R.id.addButton);
        addButton.setOnClickListener(v -> {
            Intent i = new Intent(getActivity(), AddContactActivity.class);
            startActivity(i);
        });
//
//        lstFeed = view.findViewById(R.id.lstFeed);
//        lstFeed.setHasFixedSize(true);
//        lstFeed.setLayoutManager(new LinearLayoutManager(getContext()));
//        lstFeed.setItemAnimator(new DefaultItemAnimator());
//
//        chatsRepository = new ChatsRepository(getActivity().getApplication());
//        chats = new ArrayList<>();
//
//        //chats.add(new Chat("Nadav1", getResources().getIdentifier("ic_launcher", "drawable", getActivity().getPackageName()), "", ""));
//        chatAdapter = new ChatAdapter2(getContext(), chats);
//        lstFeed.setAdapter(chatAdapter);
//        chatsViewModel = new ViewModelProvider(this).get(ChatsViewModel.class);
//
//        FloatingActionButton addButton = view.findViewById(R.id.addButton);
//        addButton.setOnClickListener(v -> {
//            Intent i = new Intent(getActivity(), AddContactActivity.class);
//            startActivity(i);
//        });
////        chatsViewModel.getAllChats().observe(getActivity(), new Observer<List<Chat>>() {
////            @Override
////            public void onChanged(List<Chat> chatList) {
////
////               // chats.clear();
////                chats = chatDao.getAllChats().getValue();
////                if (chats == null) {
////                    Log.i("FUCK", "CHATSISNULL");
////                }
////                for (Chat chat : chats) {
////                    chats.add(chat.getId() + "," + chat.getContent());
////                }
////                chatAdapter.notifyDataSetChanged();
////
////
////
////
////                Log.d("check", "on change");
//////                chats.clear();
//////                chats = chatsRepository.getAllChats();
////                lstFeed.setAdapter(chatAdapter);
////                chatAdapter.setAllChats(chats);
////
////                chatAdapter.notifyDataSetChanged();
////            }
////        });
        return view;
    }
//
//
////    @Override
////    public void onActivityResult(int requestCode, int resultCode, Intent data) {
////        super.onActivityResult(requestCode, resultCode, data);
////        if (requestCode == 1) {
////            String displayName = data.getStringExtra("displayName");
////            Toast.makeText(getContext(), "contact added", Toast.LENGTH_LONG).show();
////            chatsViewModel.insert(new Chat(displayName));
////        }
////    }
//
//    private void networkRequest() {
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(URL_DATA)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        WebServiceAPI api = retrofit.create(WebServiceAPI.class);
//        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext());
//        String token = sharedPref.getString("token", "");
//        Call<List<Chat>> call = api.getAllChats(token);
//        call.enqueue(new Callback<List<Chat>>() {
//            @Override
//            public void onResponse(Call<List<Chat>> call, Response<List<Chat>> response) {
//                if (response.code() == 200) {
//                    // update chats repository
//                    Log.d("chats", response.body().toString());
//                } else {
//                    Log.i("code", String.valueOf(response.code()));
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<List<Chat>> call, Throwable t) {
//                Log.i("failure", t.getMessage());
//                Toast.makeText(getContext(), "something wrong fuck you", Toast.LENGTH_LONG).show();
//            }
//        });
//    }
//
//
//
//
//
//
//
//
//
////        chatAdapter = new ChatAdapter();
////
////        try {
////            chatsViewModel.getAllChats().observe(getViewLifecycleOwner(), new Observer<List<Chat>>() {
////                @Override
////                public void onChanged(List<Chat> chats) {
////                    if (chats != null) {
////                        chatAdapter.setChats(chats);
////                    }
////                }
////            });
////        } catch (ExecutionException | InterruptedException e) {
////            throw new RuntimeException(e);
////        }
////
////        db = Room.databaseBuilder(requireActivity(), AppDB.class, "ChatsDB").build();
////        chatDao = db.chatDao();
////
////        ListView lstFeed = view.findViewById(R.id.lstFeed);
////
//////        Button addButton = view.findViewById(R.id.addButton);
//////        addButton.setOnClickListener(temp -> {
//////            Intent i = new Intent(getActivity(), AddContactActivity.class);
//////            startActivity(i);
//////        });
////
////        lstFeed.setAdapter(chatAdapter);
////
////        lstFeed.setOnItemClickListener((parent, views, position, id) -> {
////            tempPosition = position;
////            Chat c = chatDao.get(position);
////
////            Intent intent = new Intent(getActivity(), ChatActivity.class);
////            intent.putExtra("displayName", c.getDisplayName());
////            startActivityForResult(intent, REQUEST_CODE);
////
////            chatAdapter.notifyDataSetChanged();
////        });
////
////        return view;
////    }
////
//    @Override
//    public void onResume() {
//        super.onResume();
//        chats.clear();
//        chatAdapter.notifyDataSetChanged();
//        networkRequest();
//    }
////
////    @Override
////    public void onActivityResult(int requestCode, int resultCode, Intent data) {
////        super.onActivityResult(requestCode, resultCode, data);
////
////        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
////            String lastMessage = data.getStringExtra("lastMessage");
////            String lastMessageTime = data.getStringExtra("lastMessageTime");
////            chatDao.get(tempPosition).setLastMessage(lastMessage);
////            chatDao.get(tempPosition).setLastMessageTime(lastMessageTime);
////        }
////    }
}
