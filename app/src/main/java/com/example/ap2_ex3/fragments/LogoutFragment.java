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

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ap2_ex3.R;
import com.example.ap2_ex3.WebServiceAPI;
import com.example.ap2_ex3.activities.AddContactActivity;
import com.example.ap2_ex3.activities.ChatActivity;
import com.example.ap2_ex3.activities.LoginActivity;
import com.example.ap2_ex3.adapters.ChatAdapter4;
import com.example.ap2_ex3.entities.Chat;
import com.example.ap2_ex3.viewmodels.ChatsViewModel;
import com.example.ap2_ex3.viewmodels.MessagesViewModel;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

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

//        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) confirm.getLayoutParams();
//        confirm.setOnClickListener(v -> {
//            if (counter++ % 2 == 0) {
//                layoutParams.bottomMargin = 100;
//                confirm.setLayoutParams(layoutParams);
//            }
//            else {
//                layoutParams.bottomMargin = 0;
//                confirm.setLayoutParams(layoutParams);
//            }
//        });
        return view;
    }

}
