package com.example.ap2_ex3.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ap2_ex3.R;
import com.example.ap2_ex3.WebServiceAPI;
import com.example.ap2_ex3.adapters.MessagesAdapter;
import com.example.ap2_ex3.entities.Chat;
import com.example.ap2_ex3.entities.Message;
import com.example.ap2_ex3.entities.MessageContent;
import com.example.ap2_ex3.viewmodels.ChatsViewModel;
import com.example.ap2_ex3.viewmodels.MessagesViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChatActivity extends AppCompatActivity {
    private boolean isEmpty = true;
    Chat currentChat;
    private RecyclerView msgFeed;
    private MessagesAdapter messagesAdapter;
    ChatsViewModel chatsViewModel;
    MessagesViewModel messagesViewModel;

    BroadcastReceiver messageReceiver;
    LocalBroadcastManager localBroadcastManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        messageReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                updateMessagesFromDB();
            }
        };
        localBroadcastManager.registerReceiver(messageReceiver, new IntentFilter("messageReceived"));

        chatsViewModel = ViewModelProviders.of(this).get(ChatsViewModel.class);
        messagesViewModel = ViewModelProviders.of(this).get(MessagesViewModel.class);
        messagesViewModel.getAllMessages().observe(this, messageList -> {
            Toast.makeText(this, "ahhhhhhhhhh", Toast.LENGTH_LONG).show();
            messagesAdapter.setMessageList(messageList);
        });

        TextView displayName = findViewById(R.id.displayName);
        Intent intent = getIntent();
        int id = intent.getExtras().getInt("chatID");
        try {
            currentChat = chatsViewModel.getChat(id);
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        displayName.setText(currentChat.getUser().getDisplayName());

        View back = findViewById(R.id.backBtn);
        back.setOnClickListener(view -> {
            finish();
        });
        FloatingActionButton btn = findViewById(R.id.recordOrSendBtn);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String me = sharedPref.getString("me", "");
        messagesAdapter = new MessagesAdapter(me);
        msgFeed = findViewById(R.id.msgFeed);
        msgFeed.setAdapter(messagesAdapter);

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        msgFeed.setLayoutManager(linearLayoutManager);
//        msgFeed.scrollToPosition(messagesAdapter.getItemCount() - 1);

//
        EditText messageBar = findViewById(R.id.messageBar);
        messageBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Not needed in this case, but must be implemented.
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Called when the text in the EditText is changed.
                if (s.length() > 0) {
                    btn.setImageResource(R.drawable.ic_btn_send);
                    isEmpty = false;
                } else {
                    // Set the default record icon when the message bar is empty.
                    btn.setImageResource(R.drawable.ic_btn_record);
                    isEmpty = true;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Not needed in this case, but must be implemented.
            }
        });



        btn.setOnClickListener(view -> {
            if (!isEmpty) {
                addMessageToDB();
                messageBar.setText("");
            }
        });
        updateMessagesFromDB();
    }

    private void addMessageToDB() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getResources().getString(R.string.BaseUrl))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        WebServiceAPI api = retrofit.create(WebServiceAPI.class);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String token = sharedPref.getString("token", "");
        EditText messageBar = findViewById(R.id.messageBar);
        Call<Void> call = api.createMessage(token, currentChat.getId(), new MessageContent(messageBar.getText().toString()));
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() == 200) {
                    updateMessagesFromDB();
                    updateChatsFromDB();
                    notifyChat();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
            }
        });
    }

    private void updateMessagesFromDB() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getResources().getString(R.string.BaseUrl))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        WebServiceAPI api = retrofit.create(WebServiceAPI.class);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String token = sharedPref.getString("token", "");
        Call<List<Message>> messagesCall = api.getMessages(token, currentChat.getId());
        messagesCall.enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                if (response.code() == 200) {
                    messagesViewModel.deleteAll();
                    Collections.reverse(response.body());
                    messagesViewModel.insertList(response.body());
                } else {
                    Log.i("code", String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {
            }
        });
    }

    private void updateChatsFromDB() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getResources().getString(R.string.BaseUrl))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        WebServiceAPI api = retrofit.create(WebServiceAPI.class);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
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
            public void onFailure(Call<List<Chat>> call, Throwable t) {
            }
        });
    }

    public void notifyChat() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getResources().getString(R.string.BaseUrl))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        WebServiceAPI api = retrofit.create(WebServiceAPI.class);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String token = sharedPref.getString("token", "");

        Call<Chat> chat = api.getChat(token, currentChat.getId());
        chat.enqueue(new Callback<Chat>() {
            @Override
            public void onResponse(Call<Chat> call, Response<Chat> response) {
                if(response.code() == 200) {
                    Call<Void> notiCall = api.notifyUser(token, response.body());
                    notiCall.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {}
                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {}
                    });
                }
            }

            @Override
            public void onFailure(Call<Chat> call, Throwable t) {

            }
        });

    }
}