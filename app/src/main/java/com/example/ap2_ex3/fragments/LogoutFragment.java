package com.example.ap2_ex3.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.example.ap2_ex3.R;
import com.example.ap2_ex3.activities.LoginActivity;
import com.example.ap2_ex3.viewmodels.ChatsViewModel;
import com.example.ap2_ex3.viewmodels.MessagesViewModel;

//
public class LogoutFragment extends Fragment {

    private int counter = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_logout, container, false);
        Button confirm = view.findViewById(R.id.confirmLogout);
        Button abort = view.findViewById(R.id.discardLogout);

        confirm.setOnClickListener(v -> {
            ChatsViewModel chatsViewModel = ViewModelProviders.of(this).get(ChatsViewModel.class);
            MessagesViewModel messagesViewModel = ViewModelProviders.of(this).get(MessagesViewModel.class);
            chatsViewModel.deleteAll();
            messagesViewModel.deleteAll();
            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext());
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.remove("token");
            editor.remove("me");
            editor.apply();
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        });

        abort.setOnClickListener(v -> {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayout,new HomeFragment());
            fragmentTransaction.commit();
        });

        return view;
    }

}
