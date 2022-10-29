package com.example.askyoursenior.homepage_fragments.collab_fragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.example.askyoursenior.R;
import com.example.askyoursenior.databinding.ActivityPojectDetailsOutBinding;

import com.example.askyoursenior.model.CollabContainer;

public class PojectDetailsOut extends AppCompatActivity {
    ActivityPojectDetailsOutBinding pojectDetailsOutBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_poject_details_out);
        pojectDetailsOutBinding = DataBindingUtil.setContentView(this, R.layout.activity_poject_details_out);

        CollabContainer collabContainer = getProjectDetails();
        pojectDetailsOutBinding.setProjectDetails(collabContainer);

        if(collabContainer.getImageurl().equals("null"));{
            Glide.with(this).load(collabContainer.getImageurl())
                    .placeholder(R.drawable.ic_baseline_account_circle_24)
                    .into(pojectDetailsOutBinding.proIconout);
        }

        pojectDetailsOutBinding.linkedInIcon.setOnClickListener(view -> sendMessageThroughWLinkedIn(collabContainer.getUsername(), collabContainer.getProjectdescription(), collabContainer.getProjectname()));
    }
    private void sendMessageThroughWLinkedIn( String username , String projectDescription , String projectname){
        String msg = "Hello this is " + username + " looking forward to know more about your Project " + projectname + " that you posted in Ask Your Senior Application";
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.setType("text/plain");
        sendIntent.putExtra(Intent.EXTRA_TEXT, msg);
//        sendIntent.putExtra("jid",  + "@s.whatsapp.net"); //phone number without "+" prefix
        sendIntent.setPackage("com.linkedIn");
        startActivity(sendIntent);
    }

    CollabContainer getProjectDetails(){
        Intent intent =getIntent();
        CollabContainer collabContainer = new CollabContainer();
        collabContainer.setUsername(intent.getStringExtra("username"));
        collabContainer.setProjectname(intent.getStringExtra("project_name"));
        collabContainer.setProjectdescription(intent.getStringExtra("project_description"));
        return collabContainer;
    }
}

