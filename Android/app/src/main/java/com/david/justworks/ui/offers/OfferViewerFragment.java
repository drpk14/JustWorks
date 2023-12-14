package com.david.justworks.ui.offers;

import static com.david.justworks.serverCommunication.Messages.CL_ADD_CANDIDATURE;
import static com.david.justworks.serverCommunication.Messages.CL_CHECK_IF_CANDIDATURE_IS_ABLE;
import static com.david.justworks.serverCommunication.Messages.CL_OFFER_DETAILS;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.david.justworks.R;
import com.david.justworks.databinding.RecyclerViewOffersBinding;
import com.david.justworks.entities.Offer;
import com.david.justworks.databinding.FragmentOfferViewerBinding;
import com.david.justworks.serverCommunication.CommunicationMethods;
import com.david.justworks.ui.candidatures.CandidaturesForOneOfferActivity;
import com.david.justworks.ui.candidatures.CandidaturesForOneOfferFragment;

import java.util.ArrayList;
import java.util.List;


public class OfferViewerFragment extends Fragment {
    private FragmentOfferViewerBinding binding;
    private Offer offer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentOfferViewerBinding.inflate(inflater, container, false);
        Bundle args = getArguments();
        CommunicationMethods.getInstance().sendMessage(CL_OFFER_DETAILS+":"+args.getInt("offerId", 0));
        String[] processedInput = CommunicationMethods.getInstance().recieveMessage().split(":");
        String labelsString = "";
        int option = Integer.parseInt(processedInput[1]);

        switch (option){
            case 1:
                binding.offerViewerButton.setText(R.string.see_candidatures);
                binding.offerViewerButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("offerId", offer.getId());
                        NavHostFragment.findNavController(OfferViewerFragment.this)
                                .navigate(R.id.action_nav_offer_viewer_to_nav_candidature_for_one_offer,bundle);
                    }
                });

                break;
            case 2:
                binding.offerViewerButton.setVisibility(View.INVISIBLE);
                break;
            case 3:
                binding.offerViewerButton.setText("Make Candidature");
                binding.offerViewerButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CommunicationMethods.getInstance().sendMessage(CL_CHECK_IF_CANDIDATURE_IS_ABLE+":"+offer.getId());
                        String[] processedInput = CommunicationMethods.getInstance().recieveMessage().split(":");
                        if(processedInput[1].equals("C")){
                            CommunicationMethods.getInstance().sendMessage(CL_ADD_CANDIDATURE+":"+offer.getId());
                            String[] processedInput2 = CommunicationMethods.getInstance().recieveMessage().split(":");
                            if(processedInput2[1].equals("C")){
                                Toast.makeText(getContext(), "Candidature done", Toast.LENGTH_SHORT).show();
                                NavHostFragment.findNavController(OfferViewerFragment.this)
                                        .navigate(R.id.action_nav_offer_viewer_to_nav_my_candidatures);
                            }else{
                                Toast.makeText(getContext(), "You already have one candidature for this offer", Toast.LENGTH_SHORT).show();
                            }
                        }else if(processedInput[1].equals("I")){
                            if(processedInput[2].equals("NonOptionals")){
                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                                builder.setMessage("You don't have one knowledge for all the optionals label, Do you want to make candidature?");
                                builder.setCancelable(false);

                                builder.setPositiveButton(
                                        "Yes",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                CommunicationMethods.getInstance().sendMessage(CL_ADD_CANDIDATURE+":"+offer.getId());
                                                String[] processedInput2 = CommunicationMethods.getInstance().recieveMessage().split(":");
                                                if(processedInput2[1].equals("C")){
                                                    Toast.makeText(getContext(), "Candidature done", Toast.LENGTH_SHORT).show();
                                                    NavHostFragment.findNavController(OfferViewerFragment.this)
                                                            .navigate(R.id.action_nav_offer_viewer_to_nav_my_candidatures);
                                                }else{
                                                    Toast.makeText(getContext(), "You already have one candidature for this offer", Toast.LENGTH_SHORT).show();
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
                                Toast.makeText(getContext(), "You don't have one knowledge for each obligatory label for this offer", Toast.LENGTH_SHORT).show();
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

        binding.offerViewerNameText.setText("Name:   "+offer.getName());
        binding.offerViewerBusinessmanText.setText("Businessman name:  "+offer.getUser());
        binding.offerrViewerSalaryText.setText(String.valueOf("Salary:  "+offer.getSalary()));
        binding.offerrViewerUbicationText.setText("Ubication:  "+offer.getUbication());
        binding.offerViewerLabelsText.setText("Labels:  "+labelsString);

        return binding.getRoot();
    }
}
