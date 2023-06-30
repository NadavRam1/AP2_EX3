package com.example.ap2_ex3.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ap2_ex3.R;
import com.example.ap2_ex3.WebServiceAPI;
import com.example.ap2_ex3.activities.AddContactActivity;
import com.example.ap2_ex3.activities.ChatActivity;
import com.example.ap2_ex3.adapters.ChatAdapter4;
import com.example.ap2_ex3.entities.Chat;
import com.example.ap2_ex3.viewmodels.ChatsViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//
public class ChatsFragment extends Fragment {
    private RecyclerView lstFeed;
    private ChatAdapter4 chatAdapter;
    private ChatsViewModel chatsViewModel;

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
//            Toast.makeText(getActivity(), "ahhhhhhhhhh", Toast.LENGTH_LONG).show();
            chatAdapter.setChatList(chatList);
        });

        FloatingActionButton addButton = view.findViewById(R.id.addButton);
        addButton.setOnClickListener(v -> {
            Intent i = new Intent(getActivity(), AddContactActivity.class);
            startActivity(i);
        });

        chatAdapter.setOnItemClickListener(new ChatAdapter4.OnItemClickListener() {
            @Override
            public void onItemClick(Chat chat) {
                Intent intent = new Intent(getActivity(), ChatActivity.class);
                intent.putExtra("chatID", chat.getId());
                startActivity(intent);
            }
        });
        networkRequest();
        return view;
    }
//
//@Override
//public void onActivityResult(int requestCode, int resultCode, Intent data) {
//    super.onActivityResult(requestCode, resultCode, data);
//
//    if (requestCode == REQUEST_CODE) {
//        if (resultCode == Activity.RESULT_OK) {
//            String lastMessage = data.getStringExtra("lastMessage");
//            String lastMessageTime = data.getStringExtra("lastMessageTime");
//            chats.get(tempPosition).setLastMessage(lastMessage);
//            chats.get(tempPosition).setLastMessageTime(lastMessageTime);
//        }
//    }
//}
//
    private void networkRequest() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getResources().getString(R.string.BaseUrl))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        WebServiceAPI api = retrofit.create(WebServiceAPI.class);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext());
        String token = sharedPref.getString("token", "");
        Call<List<Chat>> call = api.getAllChats(token);
        call.enqueue(new Callback<List<Chat>>() {
            @Override
            public void onResponse(Call<List<Chat>> call, Response<List<Chat>> response) {
                if (response.code() == 200) {
                    chatsViewModel.deleteAll();
                    chatsViewModel.insertList(response.body());
                } else {
                    Log.i("code", String.valueOf(response.code()));
                }

            }

            @Override
            public void onFailure(Call<List<Chat>> call, Throwable t) {
                Log.i("failure", t.getMessage());
                Toast.makeText(getContext(), "something wrong fuck you", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        networkRequest();
    }
}
