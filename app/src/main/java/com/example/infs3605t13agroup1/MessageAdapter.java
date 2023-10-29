package com.example.infs3605t13agroup1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter {

    Context context;
    ArrayList<Message> messageArrayList;

    int MESSAGE_SEND = 1;
    int MESSAGE_RECEIVE = 2;

    public MessageAdapter (Context context, ArrayList<Message> messageArrayList){
        this.context = context;
        this.messageArrayList = messageArrayList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType==MESSAGE_SEND){
            View view = LayoutInflater.from(context).inflate(R.layout.senderchatlayout,parent,false);
            return new SenderViewHolder(view);
        }
        else{
            View view = LayoutInflater.from(context).inflate(R.layout.receiverchatlayout,parent,false);
            return new ReceiverViewHolder(view);

        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Message message = messageArrayList.get(position);
        if(holder.getClass()==SenderViewHolder.class){
            SenderViewHolder viewHolder = (SenderViewHolder)holder;
            viewHolder.textViewMessage.setText(message.getMessage());
            viewHolder.timeOfMessage.setText(message.getCurrentTime());
        }
        else {
            ReceiverViewHolder viewHolder=(ReceiverViewHolder)holder;
            viewHolder.textViewMessage.setText(message.getMessage());
            viewHolder.timeOfMessage.setText(message.getCurrentTime());

        }

    }

    @Override
    public int getItemViewType(int position){
        Message message = messageArrayList.get(position);
        if (message.isReceived){
            return MESSAGE_SEND;
        }
        else{
            return MESSAGE_RECEIVE;
        }

    }

    @Override
    public int getItemCount() {
        return messageArrayList.size();
    }

    class SenderViewHolder extends RecyclerView.ViewHolder{

        TextView textViewMessage;
        TextView timeOfMessage;

        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewMessage = itemView.findViewById(R.id.sendermessage);
            timeOfMessage = itemView.findViewById(R.id.timeofmessage);
        }
    }
    class ReceiverViewHolder extends RecyclerView.ViewHolder{

        TextView textViewMessage;
        TextView timeOfMessage;

        public ReceiverViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewMessage = itemView.findViewById(R.id.sendermessage);
            timeOfMessage = itemView.findViewById(R.id.timeofmessage);
        }
    }
}

