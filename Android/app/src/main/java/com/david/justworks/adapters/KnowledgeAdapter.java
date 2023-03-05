package com.david.justworks.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.david.justworks.R;
import com.david.justworks.entities.Knowledge;
import com.david.justworks.entities.Offer;

import java.util.ArrayList;
import java.util.List;


public class KnowledgeAdapter extends RecyclerView.Adapter<KnowledgeAdapter.KnowledgeViewHolder>{
    private List<Knowledge> knowledges;
    private final LayoutInflater mInflater;
    private static KnowledgeAdapter.ClickListener clickListener;

    public KnowledgeAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        knowledges = new ArrayList();
    }

    @NonNull
    @Override
    public KnowledgeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.knowledge_row, parent, false);
        return new KnowledgeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull KnowledgeViewHolder holder, int position) {
        if (knowledges != null) {
            Knowledge current = knowledges.get(position);
            holder.nameTextView.setText( current.getName());
            holder.workerTextView.setText(current.getWorkerName());
            holder.placeTextView.setText(current.getPlace());
            holder.titleTextView.setText(current.getTitle());

            String labelsString = "";
            for(int i = 0;i<current.getLabels().size();i++){

                labelsString += current.getLabels().get(i);
                if(i != current.getLabels().size()-1){
                    labelsString += " , ";
                }
            }
            holder.labelsTextView.setText(labelsString);
        }
    }

    @Override
    public int getItemCount() {
        return knowledges.size();
    }

    public void setKnowledges(List<Knowledge> knowledges){
        this.knowledges = knowledges;
        notifyDataSetChanged();
    }

    public Knowledge getKnowledgeAtPosition (int position) {
        return knowledges.get(position);
    }

    public class KnowledgeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView nameTextView;
        private TextView workerTextView;
        private TextView placeTextView;
        private TextView titleTextView;
        private TextView labelsTextView;

        public KnowledgeViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.knowledgeListViewNameText);
            workerTextView = itemView.findViewById(R.id.knowledgeListViewWorkerText);
            placeTextView = itemView.findViewById(R.id.knowledgeListViewPlaceText);
            titleTextView = itemView.findViewById(R.id.knowledgeListViewTittleText);
            labelsTextView = itemView.findViewById(R.id.knowledgeListViewLabelsText);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(v, getAdapterPosition());
        }
    }

    public void setOnItemClickListener(KnowledgeAdapter.ClickListener clickListener) {
        KnowledgeAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(View v, int position);
    }
}
