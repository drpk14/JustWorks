package com.david.justworks.ui.offers;

import static com.david.justworks.serverCommunication.Messages.CL_MY_PROFILES;

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
import com.david.justworks.adapters.ProfileAdapter;
import com.david.justworks.databinding.FragmentOfferFilterBinding;
import com.david.justworks.entities.Profile;
import com.david.justworks.serverCommunication.CommunicationMethods;

import java.util.ArrayList;
import java.util.List;

public class OffersFilterFragment extends Fragment implements ProfileAdapter.ClickListener {

    private FragmentOfferFilterBinding binding;
    ProfileAdapter adapter;
    int selectedProfileId;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentOfferFilterBinding.inflate(inflater, container, false);

        RecyclerView recyclerView = binding.recyclerView;
        adapter = new ProfileAdapter(this.getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        CommunicationMethods.getInstance().sendMessage(CL_MY_PROFILES);
        String[] processedInput = CommunicationMethods.getInstance().recieveMessage().split(":");
        List <Profile> profiles = new ArrayList<>();
        for(int i= 1;i<processedInput.length;i=i+3){
            Profile profile = new Profile(Integer.parseInt(processedInput[i]),processedInput[i+1]);
            String[] labels = processedInput[i+2].split(",");
            List labelsList = new ArrayList(0);
            if(labels.length >= 2){
                for(int j = 1;j<labels.length;j++){
                    labelsList.add(labels[j]);
                }
                profile.setLabelList(labelsList);
            }
            profiles.add(profile);
        }
        adapter.setOnItemClickListener(this);
        adapter.setKnowledges(profiles);

        binding.cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(OffersFilterFragment.this)
                        .navigate(R.id.action_nav_offers_filter_to_nav_all_offers);
            }
        });

        binding.filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                String text = String.valueOf(binding.offerNameTextField.getText());
                if(!text.trim().equals("")){
                    bundle.putString("offerName", text);
                }else{
                    bundle.putString("offerName", "notNameFilter");
                }

                bundle.putInt("profileId",selectedProfileId);

                NavHostFragment.findNavController(OffersFilterFragment.this)
                        .navigate(R.id.action_nav_offers_filter_to_nav_all_offers, bundle);
            }
        });

        binding.cleanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(OffersFilterFragment.this)
                        .navigate(R.id.action_nav_offers_filter_to_nav_all_offers);
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
        Profile profile = adapter.getProfileAtPosition(position);
        selectedProfileId = profile.getId();
        binding.profileNameText.setText(profile.getName());

        String labelsString = "";
        for(String label : profile.getLabelList()){
            labelsString += label;
            labelsString += ",";
        }
        binding.profileLabelsText.setText(labelsString);

    }
}