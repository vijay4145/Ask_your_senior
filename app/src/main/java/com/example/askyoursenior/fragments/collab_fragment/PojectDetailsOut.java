package com.example.askyoursenior.fragments.collab_fragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.askyoursenior.R;
import com.example.askyoursenior.databinding.ActivityPojectDetailsOutBinding;

import com.example.askyoursenior.model.ProjectDetailModel;
import com.example.askyoursenior.model.SharedPreferenceDb;
import com.example.askyoursenior.shared_preferences_operation.UserDetailFromLocalDb;

public class PojectDetailsOut extends AppCompatActivity {
    ActivityPojectDetailsOutBinding pojectDetailsOutBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pojectDetailsOutBinding = DataBindingUtil.setContentView(this, R.layout.activity_poject_details_out);

        ProjectDetailModel projectDetailModel = getProjectDetails();
        pojectDetailsOutBinding.setProjectDetails(projectDetailModel);

        if(projectDetailModel.getImageUrl() != null && projectDetailModel.getImageUrl().equals("null")) {
            Glide.with(this).load(projectDetailModel.getImageUrl())
                    .placeholder(R.drawable.ic_baseline_account_circle_24)
                    .into(pojectDetailsOutBinding.proIconout);
        }

        pojectDetailsOutBinding.linkedInIcon.setOnClickListener(view -> {
            if(projectDetailModel.getLinkedInIdLink() != null && !projectDetailModel.getLinkedInIdLink().equals(""))
                openLinkInBrowser(projectDetailModel.getGithubIdLink());
        });

        pojectDetailsOutBinding.githubIcon.setOnClickListener(view -> {
            if(projectDetailModel.getGithubIdLink() != null && !projectDetailModel.getGithubIdLink().equals(""))
                openLinkInBrowser(projectDetailModel.getGithubIdLink());
        });

        pojectDetailsOutBinding.whatsappIcon.setOnClickListener(view -> openLinkinWhatsapp());

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

    void openLinkInBrowser(String link){
        if(link.equals(SharedPreferenceDb.NOT_FOUND_ERROR) || link.equals("")) {
            Toast.makeText(getApplicationContext(), "Please Complete Your Profile", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
        startActivity(browserIntent);
    }

    ProjectDetailModel getProjectDetails () {
        Intent intent = getIntent();
        ProjectDetailModel projectDetailModel = new ProjectDetailModel();
        projectDetailModel.setPostedBy(intent.getStringExtra("Project_posted_by"));
        projectDetailModel.setProjectName(intent.getStringExtra("Project_name"));
        projectDetailModel.setProjectDescription(intent.getStringExtra("Project_description"));
        projectDetailModel.setGithubIdLink(intent.getStringExtra("Project_githubIdLink"));
        projectDetailModel.setImageUrl(intent.getStringExtra("Project_imageurl"));
        projectDetailModel.setLinkedInIdLink(intent.getStringExtra("Project_poster_linkedIn_id"));
        projectDetailModel.setWhatsappNumber(intent.getStringExtra("Project_posted_by_whatsapp_number"));
        return projectDetailModel;
    }
}

