package com.example.ap2_ex3;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;


import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.ap2_ex3.Chat;


public class ChatsFragment extends Fragment {

    private static final int REQUEST_CODE = 1;
    private int tempPosition;
    private List<Chat> chats;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chats, container, false);

        ListView lstFeed = view.findViewById(R.id.lstFeed);

        chats = generateChats();
        final ChatAdapter chatAdapter = new ChatAdapter(chats);
        lstFeed.setAdapter(chatAdapter);
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

    private List<Chat> generateChats() {
        List<Chat> chats = new ArrayList<>();
        chats.add(new Chat("Nadav1", getResources().getIdentifier("ic_launcher", "drawable", getActivity().getPackageName()), "", ""));
        chats.add(new Chat("Nadav2", getResources().getIdentifier("ic_launcher", "drawable", getActivity().getPackageName()), "", ""));
        chats.add(new Chat("Nadav3", getResources().getIdentifier("ic_launcher", "drawable", getActivity().getPackageName()), "", ""));
        chats.add(new Chat("Nadav1", getResources().getIdentifier("ic_launcher", "drawable", getActivity().getPackageName()), "", ""));
        chats.add(new Chat("Nadav2", getResources().getIdentifier("ic_launcher", "drawable", getActivity().getPackageName()), "", ""));
        chats.add(new Chat("Nadav3", getResources().getIdentifier("ic_launcher", "drawable", getActivity().getPackageName()), "", ""));
        chats.add(new Chat("Nadav1", getResources().getIdentifier("ic_launcher", "drawable", getActivity().getPackageName()), "", ""));
        chats.add(new Chat("Nadav2", getResources().getIdentifier("ic_launcher", "drawable", getActivity().getPackageName()), "", ""));
        chats.add(new Chat("Nadav3", getResources().getIdentifier("ic_launcher", "drawable", getActivity().getPackageName()), "", ""));
        chats.add(new Chat("Nadav1", getResources().getIdentifier("ic_launcher", "drawable", getActivity().getPackageName()), "", ""));
        chats.add(new Chat("Nadav2", getResources().getIdentifier("ic_launcher", "drawable", getActivity().getPackageName()), "", ""));
        chats.add(new Chat("Nadav3", getResources().getIdentifier("ic_launcher", "drawable", getActivity().getPackageName()), "", ""));
        chats.add(new Chat("Nadav1", getResources().getIdentifier("ic_launcher", "drawable", getActivity().getPackageName()), "", ""));
        chats.add(new Chat("Nadav2", getResources().getIdentifier("ic_launcher", "drawable", getActivity().getPackageName()), "", ""));
        chats.add(new Chat("Nadav3", getResources().getIdentifier("ic_launcher", "drawable", getActivity().getPackageName()), "", ""));
        chats.add(new Chat("Nadav1", getResources().getIdentifier("ic_launcher", "drawable", getActivity().getPackageName()), "", ""));
        chats.add(new Chat("Nadav2", getResources().getIdentifier("ic_launcher", "drawable", getActivity().getPackageName()), "", ""));
        chats.add(new Chat("Nadav3", getResources().getIdentifier("ic_launcher", "drawable", getActivity().getPackageName()), "", ""));

        // Add more chats as needed
        return chats;
    }
}
