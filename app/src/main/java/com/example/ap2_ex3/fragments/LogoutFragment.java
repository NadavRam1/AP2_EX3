package com.example.ap2_ex3.fragments;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ap2_ex3.R;
import com.example.ap2_ex3.WebServiceAPI;
import com.example.ap2_ex3.activities.AddContactActivity;
import com.example.ap2_ex3.activities.ChatActivity;
import com.example.ap2_ex3.adapters.ChatAdapter4;
import com.example.ap2_ex3.entities.Chat;
import com.example.ap2_ex3.viewmodels.ChatsViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//
public class LogoutFragment extends Fragment {

    private int counter = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_logout, container, false);
        Button button = view.findViewById(R.id.confirmLogout);

//        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) button.getLayoutParams();
//        button.setOnClickListener(v -> {
//            if (counter++ % 2 == 0) {
//                layoutParams.bottomMargin = 100;
//                button.setLayoutParams(layoutParams);
//            }
//            else {
//                layoutParams.bottomMargin = 0;
//                button.setLayoutParams(layoutParams);
//            }
//        });


        return view;
    }

}
