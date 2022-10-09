package com.example.askyoursenior;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.example.askyoursenior.databinding.ActivityHomepageBinding;
import com.example.askyoursenior.homepage_fragments.CollabProjectFragment;
import com.example.askyoursenior.homepage_fragments.HomeFragment;
import com.example.askyoursenior.Profile_Fragments.ProfileFragment;

public class Homepage extends AppCompatActivity {
    ActivityHomepageBinding homepageBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homepageBinding = DataBindingUtil.setContentView(this, R.layout.activity_homepage);


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