package com.example.ap2_ex3.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.ap2_ex3.AppDB;
import com.example.ap2_ex3.R;
import com.example.ap2_ex3.WebServiceAPI;
import com.example.ap2_ex3.daos.ChatDao;
import com.example.ap2_ex3.entities.Chat;
import com.example.ap2_ex3.entities.UserName;
import com.example.ap2_ex3.repositories.ChatsRepository;
import com.example.ap2_ex3.viewmodels.ChatsViewModel;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class AddContactActivity extends AppCompatActivity {
    private ChatsViewModel chatsViewModel;
    private Executor executor = Executors.newSingleThreadExecutor();
    private String defaultURL;
    private SharedPreferences sharedPreferences;
    private String baseURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        defaultURL = getResources().getString(R.string.BaseUrl);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        baseURL = sharedPreferences.getString("base_url", defaultURL);

        chatsViewModel = new ViewModelProvider(this).get(ChatsViewModel.class);

        Button btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(view -> {
            networkRequest();
            finish();
        });
    }

    private void networkRequest() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
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
                    chatsViewModel.insert(response.body());
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Chat> call, Throwable t) {}
        });
    }
}

