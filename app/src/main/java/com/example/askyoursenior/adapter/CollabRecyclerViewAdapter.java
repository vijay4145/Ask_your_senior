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

import com.example.askyoursenior.fragments.collab_fragment.PojectDetailsOut;
import com.example.askyoursenior.model.ProjectDetailModel;

import java.util.ArrayList;

public class CollabRecyclerViewAdapter extends RecyclerView.Adapter<CollabRecyclerViewAdapter.ViewHolder>{
    private ArrayList<ProjectDetailModel> projectDetailModelArrayList;
    public CollabRecyclerViewAdapter(ArrayList<ProjectDetailModel> projectDetailModelArrayList){
        this.projectDetailModelArrayList = projectDetailModelArrayList;

    }

    public void setfilteredList(ArrayList<ProjectDetailModel> filteredProjectDetailModelArrayList){
        this.projectDetailModelArrayList = filteredProjectDetailModelArrayList;
        notifyDataSetChanged();


    }
    @NonNull
    @Override
    public CollabRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ProjectlistCardviewBinding projectlistCardviewBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),R.layout.projectlist_cardview,parent,false);
        return new ViewHolder(projectlistCardviewBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CollabRecyclerViewAdapter.ViewHolder holder, int position) {
        ProjectDetailModel projectDetailModel = projectDetailModelArrayList.get(position);
        holder.bind(projectDetailModel);
        holder.projectlistCardviewBinding.cardView.setOnClickListener(view -> {
            holder.cardClicked(projectDetailModel);
        });
    }

    @Override
    public int getItemCount() {
        return projectDetailModelArrayList.size();
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

        public void bind(ProjectDetailModel projectDetailModel) {
            if (projectDetailModel.getImageUrl() != null && !projectDetailModel.getImageUrl().equals("null")) {
                Glide.with(context).load(projectDetailModel.getImageUrl())
                        .placeholder(R.drawable.ic_baseline_account_circle_24)
                        .into(projectlistCardviewBinding.projectIcon);
            }
            projectlistCardviewBinding.setProjectDetail(projectDetailModel);
            projectlistCardviewBinding.executePendingBindings();
        }
        public void cardClicked(ProjectDetailModel projectDetailModel){
            Intent intent = new Intent(context , PojectDetailsOut.class);
            intent.putExtra("Project_name" , projectDetailModel.getProjectName());
            intent.putExtra("Project_description" , projectDetailModel.getProjectDescription());
            intent.putExtra("Project_imageurl", projectDetailModel.getImageUrl());
            intent.putExtra("Project_githubIdLink", projectDetailModel.getGithubIdLink());
            intent.putExtra("Project_posted_by",projectDetailModel.getPostedBy());
            intent.putExtra("Project_poster_linkedIn_id", projectDetailModel.getLinkedInIdLink());
            intent.putExtra("Project_posted_by_whatsapp_number",projectDetailModel.getWhatsappNumber());
            Pair<View, String> pair =new Pair<>(projectlistCardviewBinding.projectIcon , "project_icon_transition");
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity) context, pair);
            context.startActivity(intent, options.toBundle());
        }
    }


}
