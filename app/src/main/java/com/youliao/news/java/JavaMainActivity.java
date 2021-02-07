package com.youliao.news.java;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.youliao.news.R;

public class JavaMainActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_java);
        ViewPager mViewPager = findViewById(R.id.view_pager);
        PagerAdapter adapter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return 6;
            }

            @Override
            public Fragment getItem(int i) {
                return new JavaFragment();
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                return ((Fragment)object).getView() == view;
            }

            @Override
            public void restoreState(Parcelable state, ClassLoader loader) {
            }
        };
        mViewPager.setAdapter(adapter);
    }
}
