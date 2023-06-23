package com.example.ap2_ex3;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;


import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

import com.example.ap2_ex3.Chat;

import org.w3c.dom.ls.LSOutput;


public class ChatsFragment extends Fragment {

    private ChatsViewModel viewModel;
    private static final int REQUEST_CODE = 1;
    private int tempPosition;
    private List<Chat> chats;

    private AppDB db;
    private ChatDao chatDao;
    private  ChatAdapter chatAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chats, container, false);

        db = Room.databaseBuilder(getActivity(), AppDB.class, "ChatsDB")
                .allowMainThreadQueries()
                .build();

        chatDao = db.chatDao();

        Button addButton = view.findViewById(R.id.addButton);
        addButton.setOnClickListener(temp -> {
            Intent i = new Intent(getActivity(), AddContactActivity.class);
            startActivity(i);

        });

        viewModel = new ViewModelProvider(this).get(ChatsViewModel.class);

        ListView lstFeed = view.findViewById(R.id.lstFeed);
        chatAdapter = new ChatAdapter(chats);

        viewModel.get().observe(getViewLifecycleOwner(), chats -> {
            chatAdapter.setChats(chats);
        });


        lstFeed.setAdapter(chatAdapter);

        chats = chatDao.index();
        lstFeed.setOnItemClickListener((parent, views, position, id) -> {
            tempPosition = position;
            Chat c = chats.get(position);

            Intent intent = new Intent(getActivity(), ChatActivity.class);
            intent.putExtra("displayName", c.getDisplayName());
            startActivityForResult(intent, REQUEST_CODE);

            chatAdapter.notifyDataSetChanged();
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        chats.clear();
        chats.addAll(chatDao.index());
        chatAdapter.notifyDataSetChanged();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                String lastMessage = data.getStringExtra("lastMessage");
                String lastMessageTime = data.getStringExtra("lastMessageTime");
                chats.get(tempPosition).setLastMessage(lastMessage);
                chats.get(tempPosition).setLastMessageTime(lastMessageTime);
            }
        }
    }


//    private List<Chat> generateChats() {
//        List<Chat> chats = new ArrayList<>();
//        for(int i = 0; i < 20; i++) {
//            chats.add(new Chat("Nadav " + i, R.drawable.ic_launcher_background, "", ""));
//        }
////, getResources().getIdentifier("ic_launcher", "drawable", getActivity().getPackageName())   //was instead of  R.drawable.ic_launcher_background
//
//        // Add more chats as needed
//        return chats;
//    }
}
