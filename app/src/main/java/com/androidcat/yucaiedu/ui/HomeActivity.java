package com.androidcat.yucaiedu.ui;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.androidcat.yucaiedu.R;
import com.androidcat.yucaiedu.adapter.MyPagerAdapter;

import q.rorbin.verticaltablayout.VerticalTabLayout;

public class HomeActivity extends AppCompatActivity {

    private ViewPager viewpager;
    private VerticalTabLayout tablayout;

    private MyPagerAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mAdapter = new MyPagerAdapter(getSupportFragmentManager());
        viewpager = (ViewPager) findViewById(R.id.viewpager);
        tablayout = (VerticalTabLayout) findViewById(R.id.vertical_tab);

        viewpager.setAdapter(mAdapter);

        tablayout.setupWithViewPager(viewpager);
    }

}
