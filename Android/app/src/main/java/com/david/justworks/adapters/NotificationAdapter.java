package com.david.justworks.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.david.justworks.R;
import com.david.justworks.entities.CandidatureStateChangedNotification;
import com.david.justworks.entities.NewCandidatureNotification;
import com.david.justworks.entities.NewMessageNotification;
import com.david.justworks.entities.NewOfferNotification;
import com.david.justworks.entities.Notification;

import java.util.ArrayList;
import java.util.List;


public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>{
    private List<Notification> notifications;
    private final LayoutInflater mInflater;
    private static NotificationAdapter.ClickListener clickListener;

    public NotificationAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        notifications = new ArrayList();
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.candidature_row, parent, false);
        return new NotificationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        if (notifications != null) {
            Notification notification = notifications.get(position);
            String message = "";
            if(notification instanceof CandidatureStateChangedNotification){
                CandidatureStateChangedNotification candidatureStateChangedNotification = (CandidatureStateChangedNotification) notification;
                message = "The state of the candidature for the offer: "+candidatureStateChangedNotification.getOfferName()+" has change";
            }else if(notification instanceof NewCandidatureNotification){
                NewCandidatureNotification newCandidatureNotification = (NewCandidatureNotification) notification;
                message = "You have a new candidature for your offer: "+newCandidatureNotification.getOfferName();
            }else if(notification instanceof NewOfferNotification){
                NewOfferNotification newOfferNotification = (NewOfferNotification) notification;
                message = "You have a new offer for your alert: "+newOfferNotification.getProfileName();
            }else if(notification instanceof NewMessageNotification){
                NewMessageNotification newMessageNotification = (NewMessageNotification) notification;
                message = "You have a new message for the offer: "+newMessageNotification.getOfferName();
            }

            holder.nameTextView.setText(message);
        }
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    public void setNotifications(List<Notification> notifications){
        this.notifications = notifications;
        notifyDataSetChanged();
    }

    public void cleanNotification(){
        this.notifications.clear();
        notifyDataSetChanged();
    }

    public Notification getNotificationAtPosition (int position) {
        return notifications.get(position);
    }

    public class NotificationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView nameTextView;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.candidatureListViewText);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(v, getAdapterPosition());
        }
    }

    public void setOnItemClickListener(NotificationAdapter.ClickListener clickListener) {
        NotificationAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(View v, int position);
    }
}
