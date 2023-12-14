package com.david.justworks.ui.candidatures;

import static com.david.justworks.serverCommunication.Messages.CL_ADD_MESSAGE;
import static com.david.justworks.serverCommunication.Messages.CL_MESSAGES_OF_THIS_CANDIDATURE;
import static com.david.justworks.serverCommunication.Messages.CL_MY_CANDIDATURES;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.david.justworks.R;
import com.david.justworks.adapters.CandidatureAdapter;
import com.david.justworks.databinding.RecyclerViewCandidaturesBinding;
import com.david.justworks.entities.Candidature;
import com.david.justworks.serverCommunication.CommunicationMethods;
import com.david.justworks.ui.knowledges.KnowledgeViewer;
import com.david.justworks.ui.offers.AllOffersFragment;

import java.util.ArrayList;
import java.util.List;

public class MyCandidaturesFragment extends Fragment implements CandidatureAdapter.ClickListener {

    private RecyclerViewCandidaturesBinding binding;
    CandidatureAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = RecyclerViewCandidaturesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        RecyclerView recyclerView = binding.messageRecyclerView;
        adapter = new CandidatureAdapter(this.getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));



        adapter.setOnItemClickListener(this);

        binding.acceptedCandidaturesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                manageList("Accepted");

            }
        });

        binding.pendingCandidaturesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                manageList("Pending");

            }
        });

        binding.deniedCandidaturesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                manageList("Denied");

            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onItemClick(View v, int position) {
        Candidature candidature = adapter.getCandidatureAtPosition(position);
        CommunicationMethods.getInstance().sendMessage(CL_MESSAGES_OF_THIS_CANDIDATURE+":"+candidature.getId());
        Bundle bundle = new Bundle();
        bundle.putInt("candidatureId", candidature.getId());
        NavHostFragment.findNavController(MyCandidaturesFragment.this)
                .navigate(R.id.action_nav_my_candidatures_to_nav_candidature_messages,bundle);
    }

    private void manageList(String state) {
        adapter.cleanCandidatures();

        CommunicationMethods.getInstance().sendMessage(CL_MY_CANDIDATURES+":"+state);
        String[] processedInput = CommunicationMethods.getInstance().recieveMessage().split(":");
        List <Candidature> candidatures = new ArrayList<>();
        for(int i= 1;i<processedInput.length;i=i+7){
            Candidature candidature = new Candidature(Integer.parseInt(processedInput[i]),Integer.parseInt(processedInput[i+1]),Integer.parseInt(processedInput[i+2]),processedInput[i+3],processedInput[i+4], processedInput[i+5]);
            String[] labels = processedInput[i+6].split(",");
            List labelsList = new ArrayList(0);
            if(labels.length >= 2){
                for(int j = 1;j<labels.length;j++){
                    labelsList.add(labels[j]);
                }
                candidature.setLabels(labelsList);
            }
            candidatures.add(candidature);

            adapter.setCandidatures(candidatures);

        }
    }
}