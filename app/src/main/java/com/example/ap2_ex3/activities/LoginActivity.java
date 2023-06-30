package com.example.ap2_ex3.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.example.ap2_ex3.R;
import com.example.ap2_ex3.WebServiceAPI;
import com.example.ap2_ex3.entities.UserCredentials;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    EditText username, password;
    private String defaultURL;
    private SharedPreferences sharedPreferences;
    private String baseURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        defaultURL = getResources().getString(R.string.BaseUrl);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        baseURL = sharedPreferences.getString("base_url", defaultURL);

        TextView textView = findViewById(R.id.unregisteredText);
        String unregisteredText = getResources().getString(R.string.unregisteredText);
        SpannableString spannableString = new SpannableString(unregisteredText);

// Create a ClickableSpan for the word "here"
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                // Handle the click event here, start the registering activity
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                // Customize the appearance of the ClickableSpan (blue color and underline)
                ds.setColor(getResources().getColor(R.color.linkColor));
                ds.setUnderlineText(true);
            }
        };

// Find the start and end indices of the word "here"
        int start = unregisteredText.indexOf("here");
        int end = start + "here".length();

// Set the ClickableSpan to the specified portion of the text
        spannableString.setSpan(clickableSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

// Set the SpannableString to the TextView
        textView.setText(spannableString);
        textView.setMovementMethod(LinkMovementMethod.getInstance());

        Button loginButton = findViewById(R.id.loginButton);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);

        loginButton.setOnClickListener(view -> {
            if(checkAllFields()) {
                networkRequest();
            }
        });
    }

    private void networkRequest() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        WebServiceAPI api = retrofit.create(WebServiceAPI.class);
        UserCredentials user = new UserCredentials(
                username.getText().toString(),
                password.getText().toString()
        );
        Call<String> call = api.createToken(user);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.code() == 200) {
                    String token = response.body();
                    SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("token", "Bearer " + token);
                    editor.putString("me", username.getText().toString());
                    editor.apply();
                    Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                    startActivity(intent);
                } else {
                    username.setError(response.body());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {}
        });
    }

    private boolean checkAllFields() {
        if(username.length() == 0) {
            username.setError("This field is required");
            return false;
        }
        if(password.length() == 0) {
            username.setError("This field is required");
            return false;
        } else if (password.length() < 8) {
            password.setError("Password must be minimum 8 characters");
            return false; //add more logic like in js
        }
        return true;
    }
}