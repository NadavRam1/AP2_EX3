package com.example.ap2_ex3.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.example.ap2_ex3.AppDB;
import com.example.ap2_ex3.daos.ChatDao;
import com.example.ap2_ex3.R;
import com.example.ap2_ex3.entities.Chat;
import com.example.ap2_ex3.repositories.ChatsRepository;
import com.example.ap2_ex3.viewmodels.ChatsViewModel;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class AddContactActivity extends AppCompatActivity {

    private AppDB appDB;
    private ChatDao chatDao;
    private ChatsRepository chatsRepository;

    private ChatsViewModel chatsViewModel;
    private Executor executor = Executors.newSingleThreadExecutor();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        appDB = AppDB.getInstance(this);

        chatDao = appDB.chatDao();

        chatsRepository = new ChatsRepository(getApplication());
        chatsViewModel = new ViewModelProvider(this).get(ChatsViewModel.class);

        Button btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(view -> {
//            Intent intent = new Intent();

//            intent.putExtra("displayName", etContent.getText().toString());
//            setResult(RESULT_OK, intent);
            EditText etContent = findViewById(R.id.etContent);
            Chat chat = new Chat(etContent.getText().toString());
            executor.execute(() -> chatDao.insert(chat));
            //executor.execute(() -> chatsRepository.insert(chat));
            //executor.execute(() -> chatsViewModel.insert(chat));
            //LiveData<List<Chat>> temp = chatsRepository.getAllChats();



            finish();
        });
    }
}