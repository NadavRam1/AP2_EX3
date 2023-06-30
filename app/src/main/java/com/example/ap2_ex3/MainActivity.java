package com.example.ap2_ex3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_IMAGE_PICK = 1;
    EditText username, password, verifyPassword, displayName;
    CheckBox robotCheck;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = findViewById(R.id.registeredText);
        String unregisteredText = getResources().getString(R.string.AlreadyRegisteredText);
        SpannableString spannableString = new SpannableString(unregisteredText);

// Create a ClickableSpan for the word "here"
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                // Handle the click event here, start the registering activity
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
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

        Button signUpButton = findViewById(R.id.signUpButton);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        verifyPassword = findViewById(R.id.verifyPassword);
        displayName = findViewById(R.id.displayName);
        robotCheck = findViewById(R.id.robotCheck);

        signUpButton.setOnClickListener(view -> {
            if(CheckAllFields()) {
                //creates another intent and transfer to login after saving the data
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    }

    private boolean CheckAllFields() {
        if (username.length() == 0) {
            username.setError("This field is required");
            return false;
        }

        if (password.length() == 0) {
            password.setError("This field is required");
            return false;
        }
        if (verifyPassword.length() == 0) {
            verifyPassword.setError("This field is required");
            return false;
        }
        if (displayName.length() == 0) {
            displayName.setError("This field is required");
            return false;
        }
        if (!robotCheck.isChecked()) {
            robotCheck.setError("This field is required");
            return false;
        }


        Pattern usernamePattern = Pattern.compile("^[a-zA-Z]+$");
        if (!usernamePattern.matcher(username.getText().toString()).matches()) {
            username.setError("Username must contain only letters");
            return false;
        }

        if (password.length() < 8) {
            password.setError("Password must be minimum 8 characters");
            return false;
        }

        Pattern passwordPattern = Pattern.compile("^[a-zA-Z1-9!*]+$");
        if (!passwordPattern.matcher(password.getText().toString()).matches()) {
            password.setError("Password must contain only letters digits or !/*");
            return false;
        }

        Pattern digitPattern = Pattern.compile("^.*[1-9].*$");
        if (!digitPattern.matcher(password.getText().toString()).matches()) {
            password.setError("Password must contain at least one digit");
            return false;
        }

        Pattern lowerPattern = Pattern.compile("^.*[a-z].*$");
        if (!lowerPattern.matcher(password.getText().toString()).matches()) {
            password.setError("Password must contain at least one lowercase letter");
            return false;
        }

        Pattern upperPattern = Pattern.compile("^.*[A-Z].*$");
        if (!upperPattern.matcher(password.getText().toString()).matches()) {
            password.setError("Password must contain at least one uppercase letter");
            return false;
        }

        if (!password.getText().toString().equals(verifyPassword.getText().toString())) {
            verifyPassword.setError("The password must match in both fields");
            return false;
        }

        if (!usernamePattern.matcher(displayName.getText()).matches()) {
            displayName.setError("display name must contain only letters");
            return false;
        }

        return true;
    }

    public void selectImage(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_IMAGE_PICK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            ImageView imageView = findViewById(R.id.imageView);
            imageView.setImageURI(selectedImageUri);
        }
    }
}