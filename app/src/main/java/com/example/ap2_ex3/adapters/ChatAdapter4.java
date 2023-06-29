package com.example.ap2_ex3.adapters;

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

import java.util.ArrayList;
import java.util.List;

public class ChatAdapter4 extends RecyclerView.Adapter<ChatAdapter4.ChatViewHolder> {
    private List<Chat> chatList = new ArrayList<>();

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.feed_chat_layout, parent, false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        Chat currentChat = chatList.get(position);
        Message lastMessage = currentChat.getLastMessage();
        String content, time;
        if (lastMessage == null) {
            time = "";
            content = "";
        } else {
            time = lastMessage.getTime();
            content = lastMessage.getContent();
        }
        holder.displayName.setText(currentChat.getUser().getDisplayName());
        holder.lastMessage.setText(content);
        holder.lastMessageTime.setText(time);
        holder.profilePic.setImageResource(R.drawable.ic_launcher_foreground);
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    public void setChatList(List<Chat> chatList) {
        this.chatList = chatList;
        notifyDataSetChanged();
    }

    static class ChatViewHolder extends RecyclerView.ViewHolder {
        private TextView displayName, lastMessage, lastMessageTime;
        private ImageView profilePic;

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            displayName = itemView.findViewById(R.id.feed_chat_display_name);
            lastMessage = itemView.findViewById(R.id.feed_chat_last_message);
            lastMessageTime = itemView.findViewById(R.id.feed_chat_last_message_time);
            profilePic = itemView.findViewById(R.id.feed_chat_profile_pic);
        }
    }
}
