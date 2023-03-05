package com.david.justworks.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.david.justworks.R;

import java.util.ArrayList;
import java.util.List;

import com.david.justworks.entities.Offer;


public class OfferAdapter extends RecyclerView.Adapter<OfferAdapter.OfferViewHolder>{
    private List<Offer> offers;
    private final LayoutInflater mInflater;
    private static OfferAdapter.ClickListener clickListener;

    public OfferAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        offers = new ArrayList();
    }

    @NonNull
    @Override
    public OfferViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.offer_row, parent, false);
        return new OfferViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OfferViewHolder holder, int position) {
        if (offers != null) {
            Offer current = offers.get(position);
            holder.nameTextView.setText(current.getName());
            holder.businessmanTextView.setText(current.getName());
            holder.salaryTextView.setText(current.getSalary()+"");
            holder.ubicationTextView.setText(current.getUbication());
            String labelsString = "";
            for(int i = 0;i<current.getLabelsList().size();i++){

                labelsString += current.getLabelsList().get(i);
                if(i != current.getLabelsList().size()-1){
                    labelsString += " , ";
                }
            }

            holder.labelsTextView.setText(labelsString);
        }
    }

    @Override
    public int getItemCount() {
        return offers.size();
    }

    public void setOffers(List<Offer> offers){
        this.offers = offers;
        notifyDataSetChanged();
    }

    public Offer getOfferAtPosition (int position) {
        return offers.get(position);
    }

    public class OfferViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView nameTextView;
        private TextView businessmanTextView;
        private TextView salaryTextView;
        private TextView ubicationTextView;
        private TextView labelsTextView;

        public OfferViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.offerListViewNameText);
            businessmanTextView = itemView.findViewById(R.id.offerListViewBusinessmanText);
            salaryTextView = itemView.findViewById(R.id.offerListViewSalaryText);
            ubicationTextView = itemView.findViewById(R.id.offerListViewUbicationText);
            labelsTextView = itemView.findViewById(R.id.offerListViewLabelsText);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(v, getAdapterPosition());
        }
    }

    public void setOnItemClickListener(OfferAdapter.ClickListener clickListener) {
        OfferAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(View v, int position);
    }
}
