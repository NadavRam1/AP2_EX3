package com.example.ap2_ex3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    EditText username, password;
    boolean isAllFieldsChecked = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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
                ds.setColor(getResources().getColor(R.color.blue));
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
            isAllFieldsChecked = CheckAllFields();

            if(isAllFieldsChecked) {
                Intent tempIntent = new Intent(LoginActivity.this, MenuActivity.class);
                startActivity(tempIntent);
            }
        });



    }
    private boolean CheckAllFields() {
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