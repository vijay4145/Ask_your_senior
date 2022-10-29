package com.example.askyoursenior.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.askyoursenior.homepage_fragments.collab_fragment.ProjectDetails;
import com.example.askyoursenior.R;
import com.example.askyoursenior.adapter.CollabRecyclerViewAdapter;
import com.example.askyoursenior.model.CollabContainer;

import java.util.ArrayList;

public class CollabProjectFragment extends Fragment {

    RecyclerView collabrv;
    ArrayList <CollabContainer> CollabContainer;
    public CollabProjectFragment() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_collab_project, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        collabrv = view.findViewById(R.id.projectListRecycler);
        Button button = view.findViewById(R.id.add_new_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_new_button(v);
            }
        });
        CollabContainer = new ArrayList<>();
        CollabContainer.add(new CollabContainer("sahill.07" , "This is project Description" ,"sahill.07","library"));

        CollabRecyclerViewAdapter  collabRecyclerViewAdapter = new CollabRecyclerViewAdapter(CollabContainer);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        collabrv.setLayoutManager(layoutManager);
        collabrv.setNestedScrollingEnabled(false);
        collabrv.setAdapter(collabRecyclerViewAdapter);

    }

    public void add_new_button(View view){
        Intent intent = new Intent(view.getContext(),ProjectDetails.class);
        startActivity(intent);
    }

}