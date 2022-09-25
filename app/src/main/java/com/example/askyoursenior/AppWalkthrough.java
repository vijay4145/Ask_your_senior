package com.example.askyoursenior;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.askyoursenior.adapter.AppWalkThroughSlideViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class AppWalkthrough extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_walkthrough);

        ViewPager viewPager = findViewById(R.id.pager);
        List<Integer> layoutlist = new ArrayList<>();
        layoutlist.add(R.layout.app_walkthrough_page1);
        layoutlist.add(R.layout.app_walkthrough_page2);
        layoutlist.add(R.layout.app_walkthrough_page3);

        AppWalkThroughSlideViewPagerAdapter appWalkThroughSlideViewPagerAdapter = new AppWalkThroughSlideViewPagerAdapter(this, layoutlist);
        viewPager.setAdapter(appWalkThroughSlideViewPagerAdapter);

        TextView skipbtn = findViewById(R.id.skip_button);
        TextView donebtn = findViewById(R.id.done_button);

        skipbtn.setTranslationX(-80f);
        skipbtn.animate().translationXBy(80f).setDuration(1200) ;


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if(position == 2){
                    btnTranslation(skipbtn, -80, false);
                    btnTranslation(donebtn, 80, true);
                }else if(position == 1 && skipbtn.getVisibility() == View.INVISIBLE){
                    btnTranslation(skipbtn, -80f, true);
                    btnTranslation(donebtn, 80f, false);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        donebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadLoginPage();
            }
        });
        skipbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadLoginPage();
            }
        });

    }

    void btnTranslation(TextView textView, float offset, boolean isvisible){
        if(isvisible) {
            textView.setTranslationX(offset);
            textView.setVisibility(View.VISIBLE);
            textView.animate().translationXBy(-offset).setDuration(400);
        }else{
            textView.animate().translationXBy(offset).setDuration(400);
            textView.setVisibility(View.INVISIBLE);
            textView.setTranslationX(-offset);
        }
    }

    void loadLoginPage(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }


}