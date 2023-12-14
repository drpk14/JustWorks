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
import com.david.justworks.entities.Profile;

import java.util.ArrayList;
import java.util.List;


public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ProfileViewHolder>{
    private List<Profile> profiles;
    private final LayoutInflater mInflater;
    private static ProfileAdapter.ClickListener clickListener;

    public ProfileAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        profiles = new ArrayList();
    }

    @NonNull
    @Override
    public ProfileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.profile_row, parent, false);
        return new ProfileViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileViewHolder holder, int position) {
        if (profiles != null) {
            Profile current = profiles.get(position);
            holder.profileNameTextView.setText( current.getName());
        }
    }

    @Override
    public int getItemCount() {
        return profiles.size();
    }

    public void setKnowledges(List<Profile> profiles){
        this.profiles = profiles;
        notifyDataSetChanged();
    }

    public Profile getProfileAtPosition (int position) {
        return profiles.get(position);
    }

    public class ProfileViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView profileNameTextView;

        public ProfileViewHolder(@NonNull View itemView) {
            super(itemView);
            profileNameTextView = itemView.findViewById(R.id.profileName);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(v, getAdapterPosition());
        }
    }

    public void setOnItemClickListener(ProfileAdapter.ClickListener clickListener) {
        ProfileAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(View v, int position);
    }
}
