package com.david.justworks.ui.knowledges;

import static com.david.justworks.serverCommunication.Messages.CL_KNOWLEDGE_DETAILS;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.david.justworks.R;
import com.david.justworks.entities.Knowledge;
import com.david.justworks.serverCommunication.CommunicationMethods;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class KnowledgeViewer extends AppCompatActivity {
    private Knowledge knowledge;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_knowledge_viewer);

        CommunicationMethods.getInstance().sendMessage(CL_KNOWLEDGE_DETAILS+":"+(Integer) getIntent().getExtras().get("idKnowledge"));
        String[] processedInput = CommunicationMethods.getInstance().recieveMessage().split(":");


        TextView nameTextView = findViewById(R.id.knowledgeViewerNameText);
        TextView placeTextView = findViewById(R.id.knowledgeViewerPlaceText);
        TextView titleTextView = findViewById(R.id.knowledgeViewerTitleText);
        TextView initDateTextView = findViewById(R.id.knowledgeViewerInitDateText);
        TextView finishDateTextView = findViewById(R.id.knowledgeViewerFinishDateText);
        TextView labelsTextView = findViewById(R.id.knowledgeViewerLabelsText);


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

        nameTextView.setText("Name:  "+knowledge.getName());
        placeTextView.setText("Place:  "+knowledge.getPlace());
        titleTextView.setText("Title:  "+knowledge.getTitle());
        initDateTextView.setText("Init Date:  "+knowledge.getInitDate().toString());
        finishDateTextView.setText("Finish Date:  "+knowledge.getFinishDate().toString());
        labelsTextView.setText("Labels:  "+labelsString);


    }
}