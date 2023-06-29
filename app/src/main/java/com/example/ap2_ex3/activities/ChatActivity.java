package com.example.ap2_ex3.activities;

import android.content.Intent;
import android.content.SharedPreferences;
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
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ap2_ex3.R;
import com.example.ap2_ex3.WebServiceAPI;
import com.example.ap2_ex3.adapters.MessagesAdapter;
import com.example.ap2_ex3.entities.Chat;
import com.example.ap2_ex3.entities.Message;
import com.example.ap2_ex3.entities.MessageContent;
import com.example.ap2_ex3.entities.UserName;
import com.example.ap2_ex3.repositories.ChatsRepository;
import com.example.ap2_ex3.viewmodels.ChatsViewModel;
import com.example.ap2_ex3.viewmodels.MessagesViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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
    private List<Message> messageList;
    private RecyclerView msgFeed;
    private MessagesAdapter messagesAdapter;
    ChatsViewModel chatsViewModel;
    MessagesViewModel messagesViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

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


//        displayName.setText(currentChat[0].getUser().getDisplayName());


        FloatingActionButton btn = findViewById(R.id.recordOrSendBtn);

//        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
//        String username = sharedPreferences.getString("me", "");
//        String content, String time, User sender
//        String username, String password, String displayName, String profilePic
        messageList = new ArrayList<>();
        messageList.add(new Message(1, "hello my friend", "16:33", new UserName("nadav")));
        messageList.add(new Message(2, "hello to you too", "16:34", new UserName("ori")));
        messageList.add(new Message(3, "fuck Hemi", "16:33", new UserName("nadav")));
        messageList.add(new Message(4, "I agree", "16:33", new UserName("ori")));
        messageList.add(new Message(5, "Mother RUSSIA", "16:33", new UserName("nadav")));
        messageList.add(new Message(6, "YEET!", "16:33", new UserName("ori")));

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String me = sharedPref.getString("me", "");
        messagesAdapter = new MessagesAdapter(me);
        msgFeed = findViewById(R.id.msgFeed);
        msgFeed.setAdapter(messagesAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        msgFeed.addItemDecoration(dividerItemDecoration);

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
                networkRequest();
                messageBar.setText("");
            } else {
                View toastView = getLayoutInflater().inflate(R.layout.custom_toast, null);

                TextView textView = toastView.findViewById(R.id.text_message);
                textView.setText("fuck you");

                Toast toast = new Toast(getApplicationContext());
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setView(toastView);
                toast.show();
            }
        });
    }

    private void networkRequest() {
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
                    Call<List<Message>> messagesCall = api.getMessages(token, currentChat.getId());
                    messagesCall.enqueue(new Callback<List<Message>>() {
                        @Override
                        public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                            if (response.code() == 200) {
                                Collections.reverse(response.body());
                                messagesAdapter.setMessageList(response.body());
                            } else {
                                Log.i("code", String.valueOf(response.code()));
                            }
                        }

                        @Override
                        public void onFailure(Call<List<Message>> call, Throwable t) {
                            Log.i("failure", t.getMessage());
                            Toast.makeText(getBaseContext(), "something wrong fuck you", Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    Log.i("code", String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.i("failure", t.getMessage());
                Toast.makeText(getBaseContext(), "something wrong fuck you", Toast.LENGTH_LONG).show();
            }
        });
    }
}