package com.example.ap2_ex3.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ap2_ex3.R;
import com.example.ap2_ex3.entities.Chat;

import java.util.List;

public class ChatAdapter extends BaseAdapter {

    List<Chat> chats;

    private class ViewHolder {
        TextView displayName;
        TextView lastMessageTime;
        TextView lastMessage;
        ImageView profilePic;
    }

    public ChatAdapter() {
        this.chats = chats;
    }

    @Override
    public int getCount() {
        return chats.size();
    }

    @Override
    public Object getItem(int position) {
        return chats.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public void setChats(List<Chat> c) {
        chats = c;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.feed_chat_layout, parent, false);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.displayName = convertView.findViewById(R.id.feed_chat_display_name);
            viewHolder.lastMessage = convertView.findViewById(R.id.feed_chat_last_message);
            viewHolder.lastMessageTime = convertView.findViewById(R.id.feed_chat_last_message_time);
            viewHolder.profilePic = convertView.findViewById(R.id.feed_chat_profile_pic);
            convertView.setTag(viewHolder);
        }
        Chat c = chats.get(position);
        ViewHolder viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.displayName.setText(c.getDisplayName());
        viewHolder.profilePic.setImageResource(c.getProfilePic());
        viewHolder.lastMessage.setText(c.getLastMessage());
        viewHolder.lastMessageTime.setText(c.getLastMessageTime());

        return convertView;


    }
}
