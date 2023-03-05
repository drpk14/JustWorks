package com.david.justworks.ui.candidatures;

import static com.david.justworks.serverCommunication.Messages.CL_CANDIDATURES_FOR_ONE_OFFER; 

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.david.justworks.adapters.CandidatureAdapter;
import com.david.justworks.databinding.RecyclerViewCandidaturesBinding;
import com.david.justworks.entities.Candidature;
import com.david.justworks.serverCommunication.CommunicationMethods;
import com.david.justworks.ui.knowledges.KnowledgeViewer;

import java.util.ArrayList;
import java.util.List;

public class CandidaturesForOneOfferActivity extends AppCompatActivity implements CandidatureAdapter.ClickListener{

    private RecyclerViewCandidaturesBinding binding;
    CandidatureAdapter adapter;
    int offerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = RecyclerViewCandidaturesBinding.inflate(getLayoutInflater());
        offerId = (Integer) getIntent().getExtras().get("offerId");

        RecyclerView recyclerView = binding.candidatureRecyclerView;
        adapter = new CandidatureAdapter(getApplicationContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));


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

        setContentView(binding.getRoot());
    }


    @Override
    public void onItemClick(View v, int position) {
        Candidature candidature = adapter.getCandidatureAtPosition(position);
        Intent intent = new Intent(getApplicationContext(), KnowledgeViewer.class);
        intent.putExtra("idCandidature",candidature.getId());
        startActivity(intent);
    }

    private void manageList(String state) {
        CommunicationMethods.getInstance().sendMessage(CL_CANDIDATURES_FOR_ONE_OFFER+":"+offerId+":"+state);
        String[] processedInput = CommunicationMethods.getInstance().recieveMessage().split(":");
        List<Candidature> candidatures = new ArrayList<>();
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
            if(candidatures.size() != 0){
                adapter.setCandidatures(candidatures);
            }else{
                adapter.cleanCandidatures();
            }

        }
    }
}