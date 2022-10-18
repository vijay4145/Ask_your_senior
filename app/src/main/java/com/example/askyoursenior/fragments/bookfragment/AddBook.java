package com.example.askyoursenior.fragments.bookfragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.example.askyoursenior.R;
import com.example.askyoursenior.databinding.ActivityAddBookBinding;

public class AddBook extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActivityAddBookBinding activityAddBookBinding;
        super.onCreate(savedInstanceState);
        activityAddBookBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_book);
    }
}