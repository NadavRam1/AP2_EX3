package com.example.ap2_ex3.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ap2_ex3.R;
import com.example.ap2_ex3.adapters.MessagesAdapter;
import com.example.ap2_ex3.entities.User;
import com.example.ap2_ex3.entities.Message;
import com.example.ap2_ex3.entities.UserName;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
    private boolean isEmpty = true;
    private String lastMessage = "";

    private List<Message> messageList;
    private RecyclerView msgFeed;
    private MessagesAdapter messagesAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

//        TextView displayName = findViewById(R.id.displayName);
//        Intent intent = getIntent();
//        String name = intent.getExtras().getString("displayName");
//        displayName.setText(name);


        FloatingActionButton btn = findViewById(R.id.recordOrSendBtn);

        EditText messageBar = findViewById(R.id.messageBar);

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

        msgFeed = findViewById(R.id.msgFeed);
        messagesAdapter = new MessagesAdapter(messageList, "nadav");
        msgFeed.setAdapter(messagesAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        msgFeed.addItemDecoration(dividerItemDecoration);


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
                lastMessage = messageBar.getText().toString();
                messageBar.setText("");

            }
            else {
                View toastView = getLayoutInflater().inflate(R.layout.custom_toast, null);

                TextView textView = toastView.findViewById(R.id.text_message);
                textView.setText("fuck you");

                Toast toast = new Toast(getApplicationContext());
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setView(toastView);
                toast.show();


            }
        });

        View back = findViewById(R.id.backBtn);
        back.setOnClickListener(view -> {
            Intent resultIntent = new Intent();
            String currentDateTime = DateFormat.getDateTimeInstance().format(new Date());
            resultIntent.putExtra("lastMessage", lastMessage);
            resultIntent.putExtra("lastMessageTime", currentDateTime);
            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }
}