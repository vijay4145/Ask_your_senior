package com.example.askyoursenior;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.askyoursenior.databinding.ActivityHomepageBinding;
import com.example.askyoursenior.homepage_fragments.CollabProjectFragment;
import com.example.askyoursenior.homepage_fragments.HomeFragment;
import com.example.askyoursenior.homepage_fragments.profilepackage.ProfileFragment;
import com.example.askyoursenior.model.SharedPreferenceDb;
import com.example.askyoursenior.notifications.NotificationOfNewUserRegisteredOnSameOrganization;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

public class Homepage extends AppCompatActivity {
    ActivityHomepageBinding homepageBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homepageBinding = DataBindingUtil.setContentView(this, R.layout.activity_homepage);
        SharedPreferences getshared = getSharedPreferences(SharedPreferenceDb.DB_NAME, MODE_PRIVATE);
        String val = getshared.getString(SharedPreferenceDb.ORGANIZATION_NAME, "NOT_FOUND");
        NotificationOfNewUserRegisteredOnSameOrganization.subscribe(val);



//        -----------------------------
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(Homepage.this, "token not generated", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();

                        // Log and toast
                        homepageBinding.token.setText(token);
                        homepageBinding.token1.setText(token);
                        Toast.makeText(Homepage.this, "succesful", Toast.LENGTH_SHORT).show();
                    }
                });

//        -------------------


        HomeFragment fragmenthome = new HomeFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_content, fragmenthome).commit();

        homepageBinding.bottomNavigation.setOnItemSelectedListener(item -> {
            if(item.getItemId() == R.id.home_page_section_in_bottom_navigation){
                HomeFragment fragment = new HomeFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_content, fragment).commit();
            }else if(item.getItemId() == R.id.collab_in_project_section_in_bottom_navigation){
                CollabProjectFragment fragment = new CollabProjectFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_content, fragment).commit();
            }else if(item.getItemId() == R.id.profile_section_in_bottom_navigation){
                ProfileFragment fragment = new ProfileFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_content, fragment).commit();
            }
            return true;
        });


    }

}