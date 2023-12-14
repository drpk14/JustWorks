package com.david.justworks.ui.knowledges;

import static com.david.justworks.serverCommunication.Messages.CL_MY_WORK_EXPERIENCE;

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
import com.david.justworks.adapters.KnowledgeAdapter;
import com.david.justworks.databinding.RecyclerViewKnowledgeBinding;
import com.david.justworks.entities.Knowledge;
import com.david.justworks.serverCommunication.CommunicationMethods;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MyWorkExperienceFragment extends Fragment implements KnowledgeAdapter.ClickListener {

    private RecyclerViewKnowledgeBinding binding;
    KnowledgeAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = RecyclerViewKnowledgeBinding.inflate(inflater, container, false);

        RecyclerView recyclerView = binding.knowledgeRecyclerView;
        adapter = new KnowledgeAdapter(this.getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        CommunicationMethods.getInstance().sendMessage(CL_MY_WORK_EXPERIENCE);
        String[] processedInput = CommunicationMethods.getInstance().recieveMessage().split(":");
        List <Knowledge> knowledges = new ArrayList<>();
        for(int i= 1;i<processedInput.length;i=i+9){
            Knowledge knowledge = new Knowledge(Integer.parseInt(processedInput[i]),processedInput[i+1],processedInput[i+2],processedInput[i+3],processedInput[i+4],processedInput[i+5],LocalDate.parse(processedInput[i+6]), LocalDate.parse(processedInput[i+7]));
            String[] labels = processedInput[i+8].split(",");
            List labelsList = new ArrayList(0);
            if(labels.length >= 2){
                for(int j = 1;j<labels.length;j++){
                    labelsList.add(labels[j]);
                }
                knowledge.setLabels(labelsList);
            }
            knowledges.add(knowledge);
        }

        adapter.setOnItemClickListener(this);
        adapter.setKnowledges(knowledges);

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onItemClick(View v, int position) {
        Knowledge knowledge = adapter.getKnowledgeAtPosition(position);
        Bundle bundle = new Bundle();
        bundle.putInt("knowledgeId", knowledge.getId());

        NavHostFragment.findNavController(MyWorkExperienceFragment.this)
                .navigate(R.id.action_nav_my_work_experience_to_nav_knowledge_viewer,bundle);
    }
}