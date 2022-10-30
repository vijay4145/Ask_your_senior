package com.example.askyoursenior.fragments.collab_fragment;


import  androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.askyoursenior.DialogBox;
import com.example.askyoursenior.R;
import com.example.askyoursenior.databinding.ActivityProjectDetails2Binding;
import com.example.askyoursenior.firebaseoperation.FirebaseStorageOperation;
import com.example.askyoursenior.firebaseoperation.RealtimeDatabaseOperation;
import com.example.askyoursenior.general_functions.CreateDialogBox;
import com.example.askyoursenior.model.ProjectDetailModel;
import com.example.askyoursenior.model.SharedPreferenceDb;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.w3c.dom.Text;

import java.util.Objects;


public class ProjectDetails extends AppCompatActivity {


    private static final int GALLARY_REQ_CODE = 101;
    private Uri selectedImageUri;
    ActivityResultLauncher<Intent> intentLauncher;
    ProgressDialog dialog;
    ActivityProjectDetails2Binding projectDetails2Binding;
    ProjectDetailModel projectDetailModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        projectDetails2Binding = DataBindingUtil.setContentView(this, R.layout.activity_project_details2);
        projectDetailModel = new ProjectDetailModel();

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
            openDialog("LinkedIn id Link");
        });

        projectDetails2Binding.emailIcon.setOnClickListener(view ->{
            openDialog("EmailId");
        });

        projectDetails2Binding.githubIcon.setOnClickListener(view ->{
            openDialog("Github Link");
        });

        projectDetails2Binding.whatsappIcon.setOnClickListener(view ->{
            openDialog("Whatsapp number");
        });

        projectDetails2Binding.addProjectButton.setOnClickListener(view ->{
            addProjectDetailsToFirebase();
        });

    }

    public void openDialog(String mediaName){
        Dialog dialog = CreateDialogBox.setDialogbox(this, R.layout.layout_dialog);
        ((TextInputLayout)dialog.findViewById(R.id.id_link_edittextlayout)).setHint(mediaName);
        dialog.findViewById(R.id.add_button_in_link_add_dialog_box).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaterialAutoCompleteTextView materialAutoCompleteTextView = dialog.findViewById(R.id.id_link_edittext);
                String id = materialAutoCompleteTextView.getText().toString();
                if(mediaName.equals("LinkedIn id Link"))
                    projectDetailModel.setLinkedInIdLink(id);
                else if(mediaName.equals("Github Link"))
                    projectDetailModel.setGithubIdLink(id);
                else if(mediaName.equals("Whatsapp number"))
                    projectDetailModel.setWhatsappNumber(id);
                dialog.dismiss();
            }
        });
        dialog.show();
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
        String project_name = projectDetails2Binding.projectNameEdittext.getText().toString();
        String project_des = projectDetails2Binding.projectDesEdittext.getText().toString();

        if (project_name.equals("") || project_des.equals("")) {
            Toast.makeText(this, "Please complete all the  Mandatory fields", Toast.LENGTH_SHORT).show();
        } else {
            dialog = new ProgressDialog(this);
            dialog.setCancelable(false);
            dialog.setMessage("Uploading Project Details ...");
            dialog.show();

            SharedPreferences shrd = getSharedPreferences(SharedPreferenceDb.DB_NAME, MODE_PRIVATE);
            String organization = shrd.getString(SharedPreferenceDb.ORGANIZATION_NAME, "no organization : fatal error");
            String username = shrd.getString(SharedPreferenceDb.USER_ID, "no user id : fatal error");
            projectDetailModel.setProjectDescription(project_des);
            projectDetailModel.setProjectName(project_name);
            projectDetailModel.setPostedBy(username);
            projectDetailModel.setImageUrl("null");


            if (selectedImageUri != null) {
                FirebaseStorageOperation.pushDataWithProIcon(this ,selectedImageUri, projectDetailModel,organization);
            }
            else{
                RealtimeDatabaseOperation.PushProjectDetails(this, projectDetailModel,organization);
            }
        }

    }
    public void submittedProjectDetails () {
        dialog.dismiss();
        finish();
    }


}


