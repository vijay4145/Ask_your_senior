package com.example.askyoursenior.homepage_fragments.collab_fragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.askyoursenior.R;
import com.example.askyoursenior.databinding.ActivityPojectDetailsOutBinding;

import com.example.askyoursenior.model.CollabContainer;
import com.example.askyoursenior.shared_preferences_operation.UserDetailFromLocalDb;

public class PojectDetailsOut extends AppCompatActivity {
    ActivityPojectDetailsOutBinding pojectDetailsOutBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poject_details_out);
        pojectDetailsOutBinding = DataBindingUtil.setContentView(this, R.layout.activity_poject_details_out);

        CollabContainer collabContainer = getProjectDetails();
        pojectDetailsOutBinding.setProjectDetails(collabContainer);

        if (collabContainer.getImageurl().equals("null")) ;
        {
            Glide.with(this).load(collabContainer.getImageurl())
                    .placeholder(R.drawable.ic_baseline_account_circle_24)
                    .into(pojectDetailsOutBinding.proIconout);
        }

        pojectDetailsOutBinding.linkedInIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String LinkedIn_link = UserDetailFromLocalDb.getLinkedinId(view.getContext());
                openLinkinBrowser(LinkedIn_link);
            }
        });

        pojectDetailsOutBinding.githubIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String github_link = UserDetailFromLocalDb.getGithubId(view.getContext());
                openLinkinBrowser(github_link);
            }
        });

        pojectDetailsOutBinding.whatsappIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLinkinWhatsapp();
            }
        });

    }


    private void openLinkinWhatsapp(){
        String phonenum =UserDetailFromLocalDb.getPhoneNumber(pojectDetailsOutBinding.getRoot().getContext());
        String msg = "Hello this is " + UserDetailFromLocalDb.getUserName(pojectDetailsOutBinding.getRoot().getContext()) + " looking forward to hear from you...";
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.setType("text/plain");
        sendIntent.putExtra(Intent.EXTRA_TEXT, msg);
        sendIntent.putExtra("jid", phonenum + "@s.whatsapp.net"); //phone number without "+" prefix
        sendIntent.setPackage("com.whatsapp");
        startActivity(sendIntent);
    }

    private void openLinkinBrowser(String link){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
        pojectDetailsOutBinding.getRoot().getContext().startActivity(browserIntent);
    }

    CollabContainer getProjectDetails () {
        Intent intent = getIntent();
        CollabContainer collabContainer = new CollabContainer();
        collabContainer.setUsername(intent.getStringExtra("username"));
        collabContainer.setProjectname(intent.getStringExtra("project_name"));
        collabContainer.setProjectdescription(intent.getStringExtra("project_description"));
        return collabContainer;
    }
}

