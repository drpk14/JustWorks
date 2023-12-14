package com.david.justworks.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.david.justworks.R;

import java.util.ArrayList;
import java.util.List;


public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder>{
    private List<Entities.Message> messages;
    private final LayoutInflater mInflater;

    public MessageAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        messages = new ArrayList();
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.message_row, parent, false);


        return new MessageViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        if (messages != null) {
            Entities.Message current = messages.get(position);

            LinearLayout linearLayout = holder.itemView.findViewById(R.id.linearLayout);

            if(current.isMine()) {
                String colorHex = "#abf5a4";
                linearLayout.setBackgroundColor(Color.parseColor(colorHex));
            } else {
                String colorHex = "#FFFFFF";
                linearLayout.setBackgroundColor(Color.parseColor(colorHex));
            }
            holder.messageTextView.setText(current.getContent());
            holder.hourTextView.setText(current.getSendedHour()+":"+current.getSendedMinute());
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public void setMessages(List<Entities.Message> messages){
        this.messages = messages;
        notifyDataSetChanged();
    }

    public void addMessages(Entities.Message message){
        messages.add(message);
        notifyDataSetChanged();
    }

    public void cleanMessages(){
        this.messages.clear();
        notifyDataSetChanged();
    }

    public void addMessage(Entities.Message message){
        this.messages.add(message);
        notifyDataSetChanged();
    }

    public Entities.Message getMessageAtPosition (int position) {
        return messages.get(position);
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder{

        private TextView messageTextView;
        private TextView hourTextView;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            messageTextView = itemView.findViewById(R.id.messageTextView);
            hourTextView = itemView.findViewById(R.id.hourTextView);
        }
    }
}
