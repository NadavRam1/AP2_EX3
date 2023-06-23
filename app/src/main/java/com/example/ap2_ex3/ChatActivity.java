package com.example.ap2_ex3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.util.Date;

public class ChatActivity extends AppCompatActivity {
    private boolean isEmpty = true;
    private String lastMessage = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        TextView displayName = findViewById(R.id.displayName);
        Intent intent = getIntent();
        String name = intent.getExtras().getString("displayName");
        displayName.setText(name);


        FloatingActionButton btn = findViewById(R.id.recordOrSendBtn);

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