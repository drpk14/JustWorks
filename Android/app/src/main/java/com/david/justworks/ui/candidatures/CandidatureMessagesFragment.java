package com.david.justworks.ui.candidatures;

import static com.david.justworks.serverCommunication.Messages.CL_ADD_MESSAGE;
import static com.david.justworks.serverCommunication.Messages.CL_MESSAGES_OF_THIS_CANDIDATURE;
import static com.david.justworks.serverCommunication.Messages.CL_OFFER_DETAILS;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.david.justworks.adapters.MessageAdapter;
import com.david.justworks.databinding.FragmentCandidatureMessagesBinding;
import com.david.justworks.serverCommunication.CommunicationMethods;

import java.util.ArrayList;
import java.util.List;

import Entities.Message;

public class CandidatureMessagesFragment extends Fragment{

    private FragmentCandidatureMessagesBinding binding;
    private int candidatureId;
    MessageAdapter adapter;
    RecyclerView recyclerView;

    private static CandidatureMessagesFragment candidatureMessagesFragment;

    public static CandidatureMessagesFragment getCandidatureMessagesFragment() {
        return candidatureMessagesFragment;
    }

    public static void setCandidatureMessagesFragment(CandidatureMessagesFragment candidatureMessagesFragment) {
        CandidatureMessagesFragment.candidatureMessagesFragment = candidatureMessagesFragment;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCandidatureMessagesBinding.inflate(inflater, container, false);

        Bundle args = getArguments();
        candidatureId = args.getInt("candidatureId", 0);

        recyclerView = binding.messageRecyclerView;
        adapter = new MessageAdapter(this.getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        String[] processedInput = CommunicationMethods.getInstance().recieveMessage().split(":");
        List<Message> messages = new ArrayList<>();
        for(int i= 3;i<processedInput.length;i=i+4){
            String[] sendedTime = processedInput[i+2].split("_");
            Message message = new Message(Integer.parseInt(processedInput[i]),processedInput[i+1],Integer.parseInt(sendedTime[0]),Integer.parseInt(sendedTime[1]),Boolean.parseBoolean(processedInput[i+3]));
            messages.add(message);
        }
        adapter.setMessages(messages);
        int lastIndex = adapter.getItemCount() - 1;
        recyclerView.scrollToPosition(lastIndex);

        binding.messageEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    if(binding.messageEditText.getText().length() != 0){
                        CommunicationMethods.getInstance().sendMessage(CL_ADD_MESSAGE+":"+candidatureId+":"+binding.messageEditText.getText());
                        String[] processedInput = CommunicationMethods.getInstance().recieveMessage().split(":");
                        for(int i= 1;i<processedInput.length;i=i+4){
                            String[] sendedTime = processedInput[i+2].split("_");
                            Entities.Message message = new Entities.Message(Integer.parseInt(processedInput[i]),processedInput[i+1],Integer.parseInt(sendedTime[0]),Integer.parseInt(sendedTime[1]),Boolean.parseBoolean(processedInput[i+3]));
                            adapter.addMessage(message);
                            int lastIndex = adapter.getItemCount() - 1;
                            binding.messageEditText.setText("");
                            recyclerView.scrollToPosition(lastIndex);
                        }

                    }
                    return true;
                }
                return false;
            }
        });
        CandidatureMessagesFragment.setCandidatureMessagesFragment(this);
        return binding.getRoot();
    }

    public void refreshMessages(){
        adapter.cleanMessages();

        CommunicationMethods.getInstance().sendMessage(CL_MESSAGES_OF_THIS_CANDIDATURE+":"+candidatureId);
        String[] processedInput = CommunicationMethods.getInstance().recieveMessage().split(":");
        List<Message> messages = new ArrayList<>();
        for(int i= 3;i<processedInput.length;i=i+4){
            String[] sendedTime = processedInput[i+2].split("_");
            Message message = new Message(Integer.parseInt(processedInput[i]),processedInput[i+1],Integer.parseInt(sendedTime[0]),Integer.parseInt(sendedTime[1]),Boolean.parseBoolean(processedInput[i+3]));
            messages.add(message);
        }
        adapter.setMessages(messages);
        int lastIndex = adapter.getItemCount() - 1;
        recyclerView.scrollToPosition(lastIndex);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        CandidatureMessagesFragment.setCandidatureMessagesFragment(null);
    }
}