package com.david.justworks.ui.notifications;

import static com.david.justworks.serverCommunication.Messages.CL_MY_CANDIDATURE_STATE_CHANGED_NOTIFICATIONS;
import static com.david.justworks.serverCommunication.Messages.CL_MY_NEW_CANDIDATURE_NOTIFICATIONS;
import static com.david.justworks.serverCommunication.Messages.CL_MY_NEW_OFFER_NOTIFICATIONS;
import static com.david.justworks.serverCommunication.Messages.CL_MY_PROFILES;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.david.justworks.R;
import com.david.justworks.adapters.NotificationAdapter;
import com.david.justworks.adapters.ProfileAdapter;
import com.david.justworks.databinding.FragmentMyNotificationsBinding;
import com.david.justworks.entities.CandidatureStateChangedNotification;
import com.david.justworks.entities.NewCandidatureNotification;
import com.david.justworks.entities.NewMessageNotification;
import com.david.justworks.entities.NewOfferNotification;
import com.david.justworks.entities.Notification;
import com.david.justworks.serverCommunication.CommunicationMethods;
import com.david.justworks.ui.offers.AllOffersFragment;

import java.util.ArrayList;
import java.util.List;

public class MyNotificationsFragment extends Fragment implements NotificationAdapter.ClickListener {

    private FragmentMyNotificationsBinding binding;
    NotificationAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMyNotificationsBinding.inflate(inflater, container, false);
        String activityName = getActivity().getClass().getSimpleName();

        if(activityName.equals("MainWorkerActivity")){

            binding.newCandidatureButton.setVisibility(View.GONE);

        }else if(activityName.equals("MainBusinessmanActivity")){
            binding.candidatureStateChangedButton.setVisibility(View.GONE);
            binding.newOfferButton.setVisibility(View.GONE);
        }
        RecyclerView recyclerView = binding.recyclerView;
        adapter = new NotificationAdapter(this.getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        adapter.setOnItemClickListener(this);

        binding.newCandidatureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.cleanNotification();
                CommunicationMethods.getInstance().sendMessage(CL_MY_NEW_CANDIDATURE_NOTIFICATIONS);
                String[] processedInput = CommunicationMethods.getInstance().recieveMessage().split(":");
                List <Notification> notifications = new ArrayList<>();
                for(int i= 1;i<processedInput.length;i=i+4){
                    notifications.add(new NewCandidatureNotification(Integer.parseInt(processedInput[i]),Integer.parseInt(processedInput[i+1]),Integer.parseInt(processedInput[i+2]),processedInput[i+3]));
                }
                adapter.setNotifications(notifications);
            }
        });

        binding.newOfferButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.cleanNotification();
                CommunicationMethods.getInstance().sendMessage(CL_MY_NEW_OFFER_NOTIFICATIONS);
                String[] processedInput = CommunicationMethods.getInstance().recieveMessage().split(":");
                List <Notification> notifications = new ArrayList<>();
                for(int i= 1;i<processedInput.length;i=i+4){
                    notifications.add(new NewOfferNotification(Integer.parseInt(processedInput[i]),Integer.parseInt(processedInput[i+1]),Integer.parseInt(processedInput[i+2]),processedInput[i+3]));
                }
                adapter.setNotifications(notifications);
            }
        });

        binding.candidatureStateChangedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.cleanNotification();
                CommunicationMethods.getInstance().sendMessage(CL_MY_CANDIDATURE_STATE_CHANGED_NOTIFICATIONS);
                String[] processedInput = CommunicationMethods.getInstance().recieveMessage().split(":");
                List <Notification> notifications = new ArrayList<>();
                for(int i= 1;i<processedInput.length;i=i+5){
                    notifications.add(new CandidatureStateChangedNotification(Integer.parseInt(processedInput[i]),Integer.parseInt(processedInput[i+1]),Integer.parseInt(processedInput[i+2]),processedInput[i+3],processedInput[i+4]));
                }
                adapter.setNotifications(notifications);
            }
        });

        binding.newMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.cleanNotification();
            }
        });


        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onItemClick(View v, int position) {
        Notification notification = adapter.getNotificationAtPosition(position);
        if(notification instanceof CandidatureStateChangedNotification){
            CandidatureStateChangedNotification candidatureStateChangedNotification = (CandidatureStateChangedNotification) notification;
            NavHostFragment.findNavController(MyNotificationsFragment.this).popBackStack(R.id.nav_my_notifications, false);
            NavHostFragment.findNavController(MyNotificationsFragment.this)
                    .navigate(R.id.action_nav_my_notifications_to_nav_my_candidatures);
        }else if(notification instanceof NewCandidatureNotification){
            NewCandidatureNotification newCandidatureNotification = (NewCandidatureNotification) notification;
        }else if(notification instanceof NewOfferNotification){
            NewOfferNotification newOfferNotification = (NewOfferNotification) notification;
            Bundle bundle = new Bundle();
            bundle.putInt("offerId", newOfferNotification.getOfferId());
            NavHostFragment.findNavController(MyNotificationsFragment.this).popBackStack(R.id.nav_my_notifications, false);
            NavHostFragment.findNavController(MyNotificationsFragment.this)
                    .navigate(R.id.action_nav_my_notifications_to_nav_offer_viewer,bundle);
        }else if(notification instanceof NewMessageNotification){
            NewMessageNotification newMessageNotification = (NewMessageNotification) notification;
        }
    }
}