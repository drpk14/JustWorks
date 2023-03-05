package com.david.justworks.ui.offers;

import static com.david.justworks.serverCommunication.Messages.*;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.david.justworks.R;
import com.david.justworks.entities.Offer;
import com.david.justworks.serverCommunication.CommunicationMethods;
import com.david.justworks.ui.candidatures.CandidaturesForOneOfferActivity;

import java.util.ArrayList;
import java.util.List;

public class OfferViewer extends AppCompatActivity {
    private Offer offer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_viewer);

        CommunicationMethods.getInstance().sendMessage(CL_OFFER_DETAILS+":"+(Integer) getIntent().getExtras().get("idOffer"));
        String[] processedInput = CommunicationMethods.getInstance().recieveMessage().split(":");


        TextView nameTextView = findViewById(R.id.offerViewerNameText);
        TextView businessmanNameTextView = findViewById(R.id.offerViewerBusinessmanText);
        TextView salaryTextView = findViewById(R.id.offerrViewerSalaryText);
        TextView ubicationTextView = findViewById(R.id.offerrViewerUbicationText);
        TextView labelsTextView = findViewById(R.id.offerViewerLabelsText);

        String labelsString = "";
        Button candidaturebutton = findViewById(R.id.offerViewerButton);
        int option = Integer.parseInt(processedInput[1]);

        switch (option){
            case 1:
                candidaturebutton.setText(R.string.see_candidatures);
                candidaturebutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), CandidaturesForOneOfferActivity.class);
                        intent.putExtra("offerId",offer.getId());
                        startActivity(intent);
                    }
                });

                break;
            case 2:
                candidaturebutton.setVisibility(View.INVISIBLE);
                break;
            case 3:
                candidaturebutton.setText(R.string.candidate);
                candidaturebutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        CommunicationMethods.getInstance().sendMessage(CL_CHECK_IF_CANDIDATURE_IS_ABLE+":"+offer.getId());
                        String[] processedInput = CommunicationMethods.getInstance().recieveMessage().split(":");
                        if(processedInput[1].equals("C")){
                            CommunicationMethods.getInstance().sendMessage(CL_ADD_CANDIDATURE+":"+offer.getId());
                            String[] processedInput2 = CommunicationMethods.getInstance().recieveMessage().split(":");
                            if(processedInput2[1].equals("C")){
                                Toast.makeText(getApplicationContext(), "Candidature donne", Toast.LENGTH_SHORT).show();
                            }
                        }else if(processedInput[1].equals("I")){
                            if(processedInput[2].equals("Some")){
                                AlertDialog.Builder builder = new AlertDialog.Builder(OfferViewer.this);

                                builder.setMessage("You don't have one knowledge for each label, Do you want to make candidature?");
                                builder.setCancelable(true);

                                builder.setPositiveButton(
                                    "Yes",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            CommunicationMethods.getInstance().sendMessage(CL_ADD_CANDIDATURE+":"+offer.getId());
                                            String[] processedInput2 = CommunicationMethods.getInstance().recieveMessage().split(":");
                                            if(processedInput2[1].equals("C")){
                                                Toast.makeText(getApplicationContext(), "Candidature donne", Toast.LENGTH_SHORT).show();
                                            }
                                            dialog.cancel();
                                        }
                                    });

                                builder.setNegativeButton(
                                    "No",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {

                                            dialog.cancel();
                                        }
                                    });


                                AlertDialog alert = builder.create();
                                alert.show();
                            }else{
                                Toast.makeText(getApplicationContext(), "You cannot make the candidature", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
                break;
        }

        for(int i= 2;i<processedInput.length;i=i+8){
            offer = new Offer(Integer.parseInt(processedInput[i]),processedInput[i+1],processedInput[i+2],processedInput[i+3],processedInput[i+4],Integer.parseInt(processedInput[i+5]),processedInput[i+6]);
            String[] labels = processedInput[i+7].split(",");
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

        nameTextView.setText("Name:   "+offer.getName());
        businessmanNameTextView.setText("Businessman name:  "+offer.getUser());
        salaryTextView.setText(String.valueOf("Salary:  "+offer.getSalary()));
        ubicationTextView.setText("Ubication:  "+offer.getUbication());
        labelsTextView.setText("Labels:  "+labelsString);



    }
}