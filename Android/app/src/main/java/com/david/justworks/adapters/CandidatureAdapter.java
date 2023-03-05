package com.david.justworks.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.david.justworks.R;
import com.david.justworks.entities.Candidature;
import com.david.justworks.entities.Offer;

import java.util.ArrayList;
import java.util.List;


public class CandidatureAdapter extends RecyclerView.Adapter<CandidatureAdapter.CandidatureViewHolder>{
    private List<Candidature> candidatures;
    private final LayoutInflater mInflater;
    private static CandidatureAdapter.ClickListener clickListener;

    public CandidatureAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        candidatures = new ArrayList();
    }

    @NonNull
    @Override
    public CandidatureViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.candidature_row, parent, false);
        return new CandidatureViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CandidatureViewHolder holder, int position) {
        if (candidatures != null) {
            Candidature current = candidatures.get(position);

            holder.nameTextView.setText("Candidature from: "+current.getWorkerName()+" to the offer: "+ current.getOfferName());
        }
    }

    @Override
    public int getItemCount() {
        return candidatures.size();
    }

    public void setCandidatures(List<Candidature> candidatures){
        this.candidatures = candidatures;
        notifyDataSetChanged();
    }

    public void cleanCandidatures(){
        this.candidatures.clear();
        notifyDataSetChanged();
    }

    public Candidature getCandidatureAtPosition (int position) {
        return candidatures.get(position);
    }

    public class CandidatureViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView nameTextView;

        public CandidatureViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.candidatureListViewText);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(v, getAdapterPosition());
        }
    }

    public void setOnItemClickListener(CandidatureAdapter.ClickListener clickListener) {
        CandidatureAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(View v, int position);
    }
}
