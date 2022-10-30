package com.example.askyoursenior.fragments.collab_fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.askyoursenior.R;
import com.example.askyoursenior.adapter.CollabRecyclerViewAdapter;
import com.example.askyoursenior.model.BookDetailModel;
import com.example.askyoursenior.model.ProjectDetailModel;
import com.example.askyoursenior.model.RealtimeDatabaseModel;
import com.example.askyoursenior.model.SharedPreferenceDb;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CollabProjectFragment extends Fragment {

    RecyclerView collabrv;
    CollabRecyclerViewAdapter collabRecyclerViewAdapter;
    ArrayList <ProjectDetailModel> projectDetailModel;
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

        projectDetailModel = new ArrayList<>();
        projectDetailModel.add(new ProjectDetailModel("Library Management system", "sahill07", "Project for sale", "null", "null", "null", "null"));
        collabRecyclerViewAdapter = new CollabRecyclerViewAdapter(projectDetailModel);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        collabrv.setLayoutManager(layoutManager);
        collabrv.setAdapter(collabRecyclerViewAdapter);
        updateBookDetailList();

    }

    public void add_new_button(View view){
        Intent intent = new Intent(view.getContext(),ProjectDetails.class);
        startActivity(intent);
    }

    private void updateBookDetailList() {
        SharedPreferences shrd = getContext().getSharedPreferences(SharedPreferenceDb.DB_NAME, MODE_PRIVATE);
        String orgname = shrd.getString(SharedPreferenceDb.ORGANIZATION_NAME, "no organization: fatal error");

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child(RealtimeDatabaseModel.PROJECT_DETAILS).child(orgname).get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    projectDetailModel.add(dataSnapshot1.getValue(ProjectDetailModel.class));
                    Log.d("firebase", "project name is " + projectDetailModel.get(projectDetailModel.size()-1).getProjectName());
                }
                for(ProjectDetailModel projectDetailModel: projectDetailModel)
                    Toast.makeText(getContext(), projectDetailModel.getProjectName(), Toast.LENGTH_SHORT).show();
                collabRecyclerViewAdapter.setfilteredList(projectDetailModel);
            }
        });;
    }

}