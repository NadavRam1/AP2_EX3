package com.example.ap2_ex3.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ap2_ex3.R;
import com.example.ap2_ex3.WebServiceAPI;
import com.example.ap2_ex3.activities.AddContactActivity;
import com.example.ap2_ex3.activities.ChatActivity;
import com.example.ap2_ex3.adapters.ChatAdapter;
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
    private ChatAdapter chatAdapter;
    private ChatsViewModel chatsViewModel;
    BroadcastReceiver messageReceiver;
    LocalBroadcastManager localBroadcastManager;
    private String defaultURL;
    private SharedPreferences sharedPreferences;
    private String baseURL;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chats, container, false);

        defaultURL = getResources().getString(R.string.BaseUrl);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext());
        baseURL = sharedPreferences.getString("base_url", defaultURL);

        lstFeed = view.findViewById(R.id.lstFeed);
        lstFeed.setHasFixedSize(true);

        localBroadcastManager = LocalBroadcastManager.getInstance(this.getActivity());
        messageReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                networkRequest();
            }
        };
        localBroadcastManager.registerReceiver(messageReceiver, new IntentFilter("messageReceived"));


        chatAdapter = new ChatAdapter();
        lstFeed.setAdapter(chatAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        lstFeed.addItemDecoration(dividerItemDecoration);

        chatsViewModel = ViewModelProviders.of(this).get(ChatsViewModel.class);
        chatsViewModel.getAllChats().observe(getActivity(), chatList -> {
        chatAdapter.setChatList(chatList);
        });

        FloatingActionButton addButton = view.findViewById(R.id.addButton);
        addButton.setOnClickListener(v -> {
            Intent i = new Intent(getActivity(), AddContactActivity.class);
            startActivity(i);
        });

        chatAdapter.setOnItemClickListener(chat -> {
            Intent intent = new Intent(getActivity(), ChatActivity.class);
            intent.putExtra("chatID", chat.getId());
            startActivity(intent);
        });
        networkRequest();
        return view;
    }
    private void networkRequest() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
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
                }
            }

            @Override
            public void onFailure(Call<List<Chat>> call, Throwable t) {}
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        networkRequest();
    }
}
