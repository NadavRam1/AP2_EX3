package com.example.ap2_ex3.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ap2_ex3.R;
import com.example.ap2_ex3.entities.Chat;

import java.util.List;

public class ChatAdapter2 extends RecyclerView.Adapter<ChatAdapter2.ChatViewHolder> {

    private Context context;
    private List<Chat> chatList;

    public ChatAdapter2(Context context, List<Chat> chatList) {
        this.context = context;
        this.chatList = chatList;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ChatViewHolder((LayoutInflater.from(parent.getContext()))
                .inflate(R.layout.feed_chat_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        Chat chat = chatList.get(position);
        holder.displayName.setText(chat.getUser().getDisplayName());
        holder.lastMessage.setText(chat.getLastMessage().getContent());
        holder.lastMessageTime.setText(chat.getLastMessage().getCreated());
//        holder.profilePic.setImageResource(chat.getUser().getProfilePic());
    }

    public void setAllChats(List<Chat> chatList) {
        if(chatList != null) {
            this.chatList = chatList;
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    public static class ChatViewHolder extends RecyclerView.ViewHolder {
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
