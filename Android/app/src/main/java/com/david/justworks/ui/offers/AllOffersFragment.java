package com.david.justworks.ui.offers;

import static com.david.justworks.serverCommunication.Messages.CL_ALL_OFFERS;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.david.justworks.R;
import com.david.justworks.adapters.OfferAdapter;
import com.david.justworks.databinding.FragmentAllOffersBinding;
import com.david.justworks.entities.Offer;
import com.david.justworks.serverCommunication.CommunicationMethods;

import java.util.ArrayList;
import java.util.List;

public class AllOffersFragment extends Fragment implements OfferAdapter.ClickListener {

    private FragmentAllOffersBinding binding;
    OfferAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAllOffersBinding.inflate(inflater, container, false);

        RecyclerView recyclerView = binding.offersRecyclerView;
        adapter = new OfferAdapter(this.getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        Bundle args = getArguments();
        String offerName = "notNameFilter";
        int profileId = 0;
        if(args != null){
            profileId = args.getInt("profileId", 0);
            offerName = args.getString("offerName", "notNameFilter");
        }

        CommunicationMethods.getInstance().sendMessage(CL_ALL_OFFERS+":"+profileId+":"+offerName);
        String[] processedInput = CommunicationMethods.getInstance().recieveMessage().split(":");
        List <Offer> offers = new ArrayList<>();
        for(int i= 1;i<processedInput.length;i=i+8){
            Offer newOffer = new Offer(Integer.parseInt(processedInput[i]),processedInput[i+1],processedInput[i+2],processedInput[i+3],processedInput[i+4],Integer.parseInt(processedInput[i+5]),processedInput[i+6]);
            String[] labels = processedInput[i+7].split(",");
            List labelsList = new ArrayList(0);
            if(labels.length >= 2){
                for(int j = 1;j<labels.length;j++){
                    labelsList.add(labels[j]);
                }
                newOffer.setLabelsList(labelsList);
            }
            offers.add(newOffer);
        }
        adapter.setOnItemClickListener(this);
        adapter.setOffers(offers);

        binding.filterPaneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(AllOffersFragment.this)
                        .navigate(R.id.action_nav_all_offers_to_nav_offers_filter);
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
        Offer offer = adapter.getOfferAtPosition(position);
        Bundle bundle = new Bundle();
        bundle.putInt("offerId", offer.getId());
        NavHostFragment.findNavController(AllOffersFragment.this).popBackStack(R.id.nav_all_offers, false);
        NavHostFragment.findNavController(AllOffersFragment.this)
                .navigate(R.id.action_nav_all_offers_to_nav_offer_viewer, bundle);
    }
}