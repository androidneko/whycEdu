package com.androidcat.yucaiedu.adapter;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.androidcat.yucaiedu.fragment.BaseFragment;
import com.androidcat.yucaiedu.fragment.SmartFragmentFactory;

public class MyPagerAdapter extends FragmentPagerAdapter {
    private String[] mTitles = {"日常规考评","校务日志","分析统计","班级得分","设置"};
    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public BaseFragment getItem(int position) {
        BaseFragment fragment = SmartFragmentFactory.createFragment(position);
        return fragment;
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
        //return "";
    }
}