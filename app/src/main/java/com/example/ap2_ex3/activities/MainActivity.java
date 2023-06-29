package com.example.ap2_ex3.activities;

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
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ap2_ex3.R;
import com.example.ap2_ex3.WebServiceAPI;
import com.example.ap2_ex3.entities.User;
import com.example.ap2_ex3.repositories.UsersRepository;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_IMAGE_PICK = 1;
    private static final String URL_DATA = "http://10.0.2.2:5000/";
    EditText username, password, verifyPassword, displayName;
    private ImageView profilePic;
    CheckBox robotCheck;
    UsersRepository usersRepository;

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
        profilePic = findViewById(R.id.profilePic);
        robotCheck = findViewById(R.id.robotCheck);

        signUpButton.setOnClickListener(view -> {
            if(checkAllFields()) {
                networkRequest();
            }
        });
    }


   private void networkRequest() {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(getResources().getString(R.string.BaseUrl))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        WebServiceAPI api = retrofit.create(WebServiceAPI.class);
        User user = new User(username.getText().toString(),
                password.getText().toString(),
                displayName.getText().toString(),
                profilePic.toString());
        Call<Void> call = api.createUser(user);
        usersRepository = new UsersRepository(getApplication());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() == 200) {
                    usersRepository.insert(user);
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
//                else {
//                    // Handle the case when the response is not successful or the body is null
////                    username.setError(response.body());
//                }
            }


            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }

        });
    }




    private boolean checkAllFields() {
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
        if (profilePic == null) {
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
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            profilePic = findViewById(R.id.profilePic);
            profilePic.setImageURI(selectedImageUri);
        }
    }
}