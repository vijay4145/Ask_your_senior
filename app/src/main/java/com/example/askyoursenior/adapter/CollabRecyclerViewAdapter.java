package com.example.askyoursenior.adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.askyoursenior.R;

import com.example.askyoursenior.databinding.ProjectlistCardviewBinding;

import com.example.askyoursenior.homepage_fragments.collab_fragment.PojectDetailsOut;
import com.example.askyoursenior.model.CollabContainer;

import java.util.ArrayList;
import java.util.Objects;

public class CollabRecyclerViewAdapter extends RecyclerView.Adapter<CollabRecyclerViewAdapter.ViewHolder>{
    private ArrayList<CollabContainer> collabContainerArrayList;
    public CollabRecyclerViewAdapter(ArrayList<CollabContainer> collabContainerArrayList){
        this.collabContainerArrayList =  collabContainerArrayList;

    }

    public void setfilteredList(ArrayList<CollabContainer> filteredCollabContainerArrayList){
        this.collabContainerArrayList = filteredCollabContainerArrayList;
        notifyDataSetChanged();


    }
    @NonNull
    @Override
    public CollabRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ProjectlistCardviewBinding projectlistCardviewBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),R.layout.projectlist_cardview,parent,false);

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.projectlist_cardview,parent,false);
        return new ViewHolder(projectlistCardviewBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CollabRecyclerViewAdapter.ViewHolder holder, int position) {
        CollabContainer collabContainer = collabContainerArrayList.get(position);
        holder.projectlistCardviewBinding.collabDescription.setText(collabContainer.getProjectdescription());
        holder.projectlistCardviewBinding.by.setText(collabContainer.getUsername());
        holder.projectlistCardviewBinding.projectName.setText(collabContainer.getProjectname());

        holder.bind(collabContainer);
        holder.projectlistCardviewBinding.cardView.setOnClickListener(view -> {
            holder.cardClicked(collabContainer);
        });
    }

    @Override
    public int getItemCount() {
        return collabContainerArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ProjectlistCardviewBinding projectlistCardviewBinding;
        Context context;

        public ViewHolder(@NonNull ProjectlistCardviewBinding itemView) {
            super(itemView.getRoot());
             this.projectlistCardviewBinding = itemView ;
             this.context = itemView.getRoot().getContext();
//             context = itemView.getRoot().getContext();
        }

        public void bind(CollabContainer collabContainer) {
            if (collabContainer.getImageurl() != null && !collabContainer.getImageurl().equals("null")) {
                Glide.with(context).load(collabContainer.getImageurl())
                        .placeholder(R.drawable.ic_baseline_account_circle_24)
                        .into(projectlistCardviewBinding.projectIcon);
            }
            projectlistCardviewBinding.setProjectDetail(collabContainer);
            projectlistCardviewBinding.executePendingBindings();
        }
        public void cardClicked(CollabContainer collabContainer){
            Intent intent = new Intent(context , PojectDetailsOut.class);
            intent.putExtra("Project_name" , collabContainer.getProjectname());
            intent.putExtra("Project_description" , collabContainer.getProjectdescription());
            intent.putExtra("Project_imageurl",collabContainer.getImageurl());
            Pair<View, String> pair =new Pair<>(projectlistCardviewBinding.projectIcon , "project_icon_transition");
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity) context, pair);
            context.startActivity(intent, options.toBundle());
        }
    }


}
