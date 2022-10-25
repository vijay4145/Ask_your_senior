package com.example.askyoursenior.fragments.profilepackage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.example.askyoursenior.R;
import com.example.askyoursenior.databinding.ActivityApplicationInfoBinding;

public class ApplicationInfo extends AppCompatActivity {
    ActivityApplicationInfoBinding activityApplicationInfoBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityApplicationInfoBinding = DataBindingUtil.setContentView(this, R.layout.activity_application_info);


        activityApplicationInfoBinding.githubIcon.setOnClickListener(view -> {
            String askYourSeniorGithubLink = "https://github.com/vijay4145/Ask_your_senior";
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(askYourSeniorGithubLink));
            startActivity(browserIntent);
        });

    }
}