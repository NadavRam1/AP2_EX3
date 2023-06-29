package com.example.ap2_ex3.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ap2_ex3.AppDB;
import com.example.ap2_ex3.WebServiceAPI;
import com.example.ap2_ex3.daos.ChatDao;
import com.example.ap2_ex3.R;
import com.example.ap2_ex3.entities.Chat;
import com.example.ap2_ex3.entities.UserName;
import com.example.ap2_ex3.repositories.ChatsRepository;
import com.example.ap2_ex3.viewmodels.ChatsViewModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


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

        chatsViewModel = new ViewModelProvider(this).get(ChatsViewModel.class);

        Button btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(view -> {
            networkRequest();
            finish();
        });
    }

    private void networkRequest() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(this.getResources().getString(R.string.BaseUrl))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        WebServiceAPI api = retrofit.create(WebServiceAPI.class);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String token = sharedPref.getString("token", "");
        EditText etContent = findViewById(R.id.etContent);
        Call<Chat> call = api.createChat(token, new UserName(etContent.getText().toString()));
        call.enqueue(new Callback<Chat>() {
            @Override
            public void onResponse(Call<Chat> call, Response<Chat> response) {
                if (response.code() == 200) {
                    Log.d("chat", response.body().toString());
                    chatsViewModel.insert(response.body());
                    finish();
                } else {
                    Log.i("code", String.valueOf(response.code()));
                }

            }

            @Override
            public void onFailure(Call<Chat> call, Throwable t) {
                Log.i("failure", t.getMessage());
                Toast.makeText(getBaseContext(), "something wrong fuck you", Toast.LENGTH_LONG).show();
            }
        });
    }
}

