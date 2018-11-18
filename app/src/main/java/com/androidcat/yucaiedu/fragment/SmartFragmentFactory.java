package com.androidcat.yucaiedu.fragment;

import java.util.HashMap;

public class SmartFragmentFactory {
    private static HashMap<Integer, BaseFragment> mBaseFragments = new HashMap<Integer, BaseFragment>();
    public static BaseFragment createFragment(int position){
        BaseFragment baseFragment = mBaseFragments.get(position);

        if (baseFragment == null) {
            switch (position) {
                case 0:
                    baseFragment = new RegularCheckFragment();
                    break;
                case 1:
                    baseFragment = new SchoolAffairsFragment();
                    break;
                case 2:
                    baseFragment = new AnalyzeFragment();
                    break;
                case 3:
                    baseFragment = new ClassesScoresFragment();
                    break;
                case 4:
                    baseFragment = new SettingsFragment();
                    break;
            }

        }

        mBaseFragments.put(position, baseFragment);
        return baseFragment;
    }
}
