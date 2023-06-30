package com.example.ap2_ex3.adapters;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ap2_ex3.R;
import com.example.ap2_ex3.entities.Chat;
import com.example.ap2_ex3.entities.Message;

import org.w3c.dom.Text;

import java.util.Base64;
import java.util.List;
import java.util.prefs.Preferences;

public class ChatAdapter3 extends RecyclerView.Adapter {
    //
    private List<Chat> chatsList;

    public ChatAdapter3(List<Chat> chatsList) {
        this.chatsList = chatsList;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view;

        view = layoutInflater.inflate(R.layout.feed_chat_layout, parent, false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ChatViewHolder chatViewHolder = (ChatViewHolder) holder;
        chatViewHolder.displayName.setText(chatsList.get(position).getUser().getDisplayName());
        chatViewHolder.lastMessage.setText(chatsList.get(position).getLastMessage().getContent());
        chatViewHolder.lastMessageTime.setText(chatsList.get(position).getLastMessage().getCreated());
        chatViewHolder.profilePic.setImageResource(R.drawable.ic_launcher_foreground);

    }

    public void setChatsList(List<Chat> chatsList) {
        this.chatsList = chatsList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(chatsList == null) {
            return 0;
        }
        return chatsList.size();
    }

    class ChatViewHolder extends RecyclerView.ViewHolder {
        TextView displayName, lastMessage, lastMessageTime;
        ImageView profilePic;

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);

            displayName = itemView.findViewById(R.id.feed_chat_display_name);
            lastMessage = itemView.findViewById(R.id.feed_chat_last_message);
            lastMessageTime = itemView.findViewById(R.id.feed_chat_last_message_time);
            profilePic = itemView.findViewById(R.id.feed_chat_profile_pic);
        }


    }

}
