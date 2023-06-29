package com.example.ap2_ex3.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ap2_ex3.R;
import com.example.ap2_ex3.activities.AddContactActivity;
import com.example.ap2_ex3.activities.ChatActivity;
import com.example.ap2_ex3.adapters.ChatAdapter4;
import com.example.ap2_ex3.entities.Chat;
import com.example.ap2_ex3.viewmodels.ChatsViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
//
public class ChatsFragment extends Fragment {
    private static final int REQUEST_CODE = 1;
    int tempPosition;
    private RecyclerView lstFeed;
    private ChatAdapter4 chatAdapter;
    private ChatsViewModel chatsViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chats, container, false);

        lstFeed = view.findViewById(R.id.lstFeed);
        lstFeed.setHasFixedSize(true);

        chatAdapter = new ChatAdapter4();
        lstFeed.setAdapter(chatAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        lstFeed.addItemDecoration(dividerItemDecoration);

        chatsViewModel = ViewModelProviders.of(this).get(ChatsViewModel.class);
        chatsViewModel.getAllChats().observe(getActivity(), chatList -> {
            Toast.makeText(getActivity(), "ahhhhhhhhhh", Toast.LENGTH_LONG).show();
            chatAdapter.setChatList(chatList);
            Log.d("code", chatList.toString());
        });

        FloatingActionButton addButton = view.findViewById(R.id.addButton);
        addButton.setOnClickListener(v -> {
            Intent i = new Intent(getActivity(), AddContactActivity.class);
            startActivityForResult(i, 1);
        });


        chatAdapter.setOnItemClickListener(new ChatAdapter4.OnItemClickListener() {
            @Override
            public void onItemClick(Chat chat) {

                Intent intent = new Intent(getActivity(), ChatActivity.class);
                intent.putExtra("chatID", chat.getId());

                startActivity(intent);

            }
        });



        return view;
    }
//
//@Override
//public void onActivityResult(int requestCode, int resultCode, Intent data) {
//    super.onActivityResult(requestCode, resultCode, data);
//
//    if (requestCode == REQUEST_CODE) {
//        if (resultCode == Activity.RESULT_OK) {
//            String lastMessage = data.getStringExtra("lastMessage");
//            String lastMessageTime = data.getStringExtra("lastMessageTime");
//            chats.get(tempPosition).setLastMessage(lastMessage);
//            chats.get(tempPosition).setLastMessageTime(lastMessageTime);
//        }
//    }
//}
//
//    private void networkRequest() {
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(URL_DATA)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        WebServiceAPI api = retrofit.create(WebServiceAPI.class);
//        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext());
//        String token = sharedPref.getString("token", "");
//        Call<List<Chat>> call = api.getAllChats(token);
//        call.enqueue(new Callback<List<Chat>>() {
//            @Override
//            public void onResponse(Call<List<Chat>> call, Response<List<Chat>> response) {
//                if (response.code() == 200) {
//                    // update chats repository
//                    Log.d("chats", response.body().toString());
//                } else {
//                    Log.i("code", String.valueOf(response.code()));
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<List<Chat>> call, Throwable t) {
//                Log.i("failure", t.getMessage());
//                Toast.makeText(getContext(), "something wrong fuck you", Toast.LENGTH_LONG).show();
//            }
//        });
//    }
//
//
}
