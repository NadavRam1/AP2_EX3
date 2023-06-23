package com.example.ap2_ex3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class AddContactActivity extends AppCompatActivity {

    private AppDB db;
    private ChatDao chatDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        db = Room.databaseBuilder(getApplicationContext(), AppDB.class, "ChatsDB")
                .allowMainThreadQueries()
                .build();

        chatDao = db.chatDao();

        Button btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(view -> {
            EditText etContent = findViewById(R.id.etContent);
            Chat chat = new Chat(etContent.getText().toString());
            chatDao.insert(chat);

            finish();
        });
    }
}