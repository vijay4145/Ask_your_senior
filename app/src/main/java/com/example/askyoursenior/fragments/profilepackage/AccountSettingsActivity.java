package com.example.askyoursenior.fragments.profilepackage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.askyoursenior.R;
import com.example.askyoursenior.databinding.ActivityAccountSettingsBinding;
import com.example.askyoursenior.firebaseoperation.RealtimeDatabaseOperation;
import com.example.askyoursenior.model.SharedPreferenceDb;
import com.example.askyoursenior.model.User;
import com.example.askyoursenior.shared_preferences_operation.UserDetailFromLocalDb;

public class AccountSettingsActivity extends AppCompatActivity {
    ActivityAccountSettingsBinding activityAccountSettingsBinding;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         activityAccountSettingsBinding = DataBindingUtil.setContentView(this, R.layout.activity_account_settings);


         user = new User();
         user.setUsername(UserDetailFromLocalDb.getUserid(this));
         user.setName(UserDetailFromLocalDb.getUserName(this));
         user.setOrganisationname(UserDetailFromLocalDb.getOrgname(this));
         activityAccountSettingsBinding.setUserDetails(user);
         updateSavedExtraDetails();

         activityAccountSettingsBinding.updateProfileButton.setOnClickListener(view -> {
             updateProfile();
         });

    }

    private void updateSavedExtraDetails() {
        String linkedidLink = UserDetailFromLocalDb.getLinkedinId(this);
        String githubIdLink = UserDetailFromLocalDb.getGithubId(this);
        String phoneNumber = UserDetailFromLocalDb.getPhoneNumber(this);

        if(!linkedidLink.equals(SharedPreferenceDb.NOT_FOUND_ERROR)) {
            activityAccountSettingsBinding.linkedinEdittext.setText(linkedidLink);
        }
        if(!githubIdLink.equals(SharedPreferenceDb.NOT_FOUND_ERROR)){
            activityAccountSettingsBinding.githubEdittext.setText(githubIdLink);
        }
        if(!phoneNumber.equals(SharedPreferenceDb.NOT_FOUND_ERROR)){
            activityAccountSettingsBinding.phoneNumberEdittext.setText(phoneNumber);
        }
    }

    private void updateProfile() {

//        if(!activityAccountSettingsBinding.githubEdittext.getText().toString().equals("")
//            || !activityAccountSettingsBinding.linkedinEdittext.getText().toString().equals("")
//            || !activityAccountSettingsBinding.phoneNumberEdittext.getText().toString().equals("")){
//            if(!activityAccountSettingsBinding.githubEdittext.getText().toString().equals(""))
                user.setGithubLink(activityAccountSettingsBinding.githubEdittext.getText().toString());
//            if(!activityAccountSettingsBinding.linkedinEdittext.getText().toString().equals(""))
                user.setLinkedinId(activityAccountSettingsBinding.linkedinEdittext.getText().toString());
//            if(!activityAccountSettingsBinding.phoneNumberEdittext.getText().toString().equals(""))
                user.setPhone_number(activityAccountSettingsBinding.phoneNumberEdittext.getText().toString());
            RealtimeDatabaseOperation.PushUserExtraDetails(this, user);
//        }
    }


}