package com.example.askyoursenior.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.askyoursenior.R;

import java.util.List;

public class AppWalkThroughSlideViewPagerAdapter extends PagerAdapter {
    private Context context;
    private List<Integer> layoutList;

    public AppWalkThroughSlideViewPagerAdapter(Context context, List<Integer> layoutList) {
        this.context = context;
        this.layoutList = layoutList;
    }

    @Override
    public int getCount() {
        return layoutList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(layoutList.get(position), container, false);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    
}
