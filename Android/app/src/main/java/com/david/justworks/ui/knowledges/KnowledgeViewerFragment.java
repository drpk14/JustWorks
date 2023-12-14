package com.david.justworks.ui.knowledges;

import static com.david.justworks.serverCommunication.Messages.CL_KNOWLEDGE_DETAILS;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.david.justworks.R;
import com.david.justworks.databinding.FragmentKnowledgeViewerBinding;
import com.david.justworks.entities.Knowledge;
import com.david.justworks.serverCommunication.CommunicationMethods;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class KnowledgeViewerFragment extends Fragment {

    private FragmentKnowledgeViewerBinding binding;
    private Knowledge knowledge;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentKnowledgeViewerBinding.inflate(inflater, container, false);

        Bundle args = getArguments();

        CommunicationMethods.getInstance().sendMessage(CL_KNOWLEDGE_DETAILS+":"+args.getInt("knowledgeId", 0));
        String[] processedInput = CommunicationMethods.getInstance().recieveMessage().split(":");


        String labelsString = "";
        for(int i= 1;i<processedInput.length;i=i+9){
            knowledge = new Knowledge(Integer.parseInt(processedInput[i]),processedInput[i+1],processedInput[i+2],processedInput[i+3],processedInput[i+4],processedInput[i+5], LocalDate.parse(processedInput[i+6]), LocalDate.parse(processedInput[i+7]));
            String[] labels = processedInput[i+8].split(",");
            List labelsList = new ArrayList(0);
            if(labels.length >= 2){
                for(int j = 1;j<labels.length;j++){
                    labelsString += labels[j];
                    if(i != labelsList.size()-1){
                        labelsString += " , ";
                    }
                    labelsList.add(labels[j]);

                }
            }
        }

        binding.knowledgeViewerNameText.setText("Name:  "+knowledge.getName());
        binding.knowledgeViewerPlaceText.setText("Place:  "+knowledge.getPlace());
        binding.knowledgeViewerTitleText.setText("Title:  "+knowledge.getTitle());
        binding.knowledgeViewerInitDateText.setText("Init Date:  "+knowledge.getInitDate().toString());
        binding.knowledgeViewerFinishDateText.setText("Finish Date:  "+knowledge.getFinishDate().toString());
        binding.knowledgeViewerLabelsText.setText("Labels:  "+labelsString);

        return binding.getRoot();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}