package com.example.ap2_ex3.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ap2_ex3.R;
import com.example.ap2_ex3.entities.Chat;
import com.example.ap2_ex3.entities.Message;

import java.util.ArrayList;
import java.util.List;

public class MessagesAdapter extends RecyclerView.Adapter {
//
    private List<Message> messageList = new ArrayList<>();
    private final String myUsername;

    public MessagesAdapter(String myUsername) {
        this.myUsername = myUsername;
    }

    @Override
    public int getItemViewType(int position) {
        if (messageList.get(position).getSender().getUsername().equals(myUsername)) {
            return 0;
        }
        return 1;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view;

        if(viewType == 0) {
            view = layoutInflater.inflate(R.layout.feed_my_message_layout, parent, false);
            return new MyMessageViewHolder(view);
        }

        view = layoutInflater.inflate(R.layout.feed_other_message_layout, parent, false);
        return new OtherMessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (messageList.get(position).getSender().getUsername().equals(myUsername)) {
            MyMessageViewHolder myMessageViewHolder = (MyMessageViewHolder) holder;
            myMessageViewHolder.content.setText(messageList.get(position).getContent());
            myMessageViewHolder.time.setText(messageList.get(position).getTime());
        } else {
            OtherMessageViewHolder otherMessageViewHolder = (OtherMessageViewHolder) holder;
            otherMessageViewHolder.content.setText(messageList.get(position).getContent());
            otherMessageViewHolder.time.setText(messageList.get(position).getTime());
        }

    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public void setMessageList(List<Message> messageList) {
        this.messageList = messageList;
        notifyDataSetChanged();
    }

    class MyMessageViewHolder extends RecyclerView.ViewHolder {
        TextView content, time;

        public MyMessageViewHolder(@NonNull View itemView) {
            super(itemView);

            content = itemView.findViewById(R.id.myMessage);
            time = itemView.findViewById(R.id.timeSent);
        }


    }

    class OtherMessageViewHolder extends RecyclerView.ViewHolder {
        TextView content, time;
        public OtherMessageViewHolder(@NonNull View itemView) {
            super(itemView);

            content = itemView.findViewById(R.id.otherMessage);
            time = itemView.findViewById(R.id.timeReceived);
        }
    }
}
