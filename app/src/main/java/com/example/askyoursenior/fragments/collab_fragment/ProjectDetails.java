package com.example.askyoursenior.fragments.collab_fragment;


import  androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.askyoursenior.DialogBox;
import com.example.askyoursenior.R;
import com.example.askyoursenior.databinding.ActivityProjectDetails2Binding;
import com.example.askyoursenior.general_functions.CreateDialogBox;
import com.example.askyoursenior.model.CollabContainer;
import com.example.askyoursenior.model.SharedPreferenceDb;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



public class ProjectDetails extends AppCompatActivity {


    private static final int GALLARY_REQ_CODE = 101;
    private Uri selectedImageUri;
    ActivityResultLauncher<Intent> intentLauncher;
    ProgressDialog dialog;
    ActivityProjectDetails2Binding projectDetails2Binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        projectDetails2Binding = DataBindingUtil.setContentView(this, R.layout.activity_project_details2);

        projectDetails2Binding.proIcon.setOnClickListener(view -> {
            addProjectDetailsToFirebase();
        });

        projectDetails2Binding.proIcon.setOnClickListener(view -> {
            getPhotoFromGallary();
        });
        intentLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        selectedImageUri = data.getData();
                        projectDetails2Binding.proIcon.setImageURI(data.getData());
                    }
                });


        projectDetails2Binding.linkedInIcon.setOnClickListener(view -> {
            openDialog();
        });

        projectDetails2Binding.emailIcon.setOnClickListener(view ->{
            openDialog();
        });

        projectDetails2Binding.phoneIcon.setOnClickListener(view ->{
            openDialog();
        });

        projectDetails2Binding.githubIcon.setOnClickListener(view ->{
            openDialog();
        });

        projectDetails2Binding.whatsappIcon.setOnClickListener(view ->{
            openDialog();
        });

    }

    public void openDialog(){
        DialogBox dialogBox = new DialogBox();
        dialogBox.show(getSupportFragmentManager(),"Dialog Box");
    }

    private void getPhotoFromGallary() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE
        }, GALLARY_REQ_CODE);
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_PICK);
            intent.setType("image/*");
            intentLauncher.launch(intent);
        }
    }


    private void addProjectDetailsToFirebase() {
        CollabContainer collabContainer = new CollabContainer();
        String project_name = projectDetails2Binding.projectNameEdittext.getText().toString();
        String project_des = projectDetails2Binding.projectDesEdittext.getText().toString();

        if (project_name.equals("") || project_des.equals("")) {
            Toast.makeText(this, "Please complete all the fields", Toast.LENGTH_SHORT).show();
        } else {
            dialog = new ProgressDialog(this);
            dialog.setCancelable(false);
            dialog.setMessage("Uploading Project Details ...");
            dialog.show();

            SharedPreferences shrd = getSharedPreferences(SharedPreferenceDb.DB_NAME, MODE_PRIVATE);
            String organization = shrd.getString(SharedPreferenceDb.USER_ID, "no organization : fatal error");
            String username = shrd.getString(SharedPreferenceDb.USER_ID, "no user id : fatal error");
            collabContainer.setProjectdescription(project_des);
            collabContainer.setProjectname(project_name);
            collabContainer.setUsername(username);
            collabContainer.setImageurl("null");


            if (selectedImageUri != null) {
//                    FirebaseStorageOperation.pushDataWithproIcon(this , selectedImageUri,collabContainer);
            }
        }

    }
    public void submittedProjectDetails () {
        dialog.dismiss();
        finish();
    }


}


