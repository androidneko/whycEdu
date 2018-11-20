package com.androidcat.yucaiedu.adapter;

import com.androidcat.yucaiedu.R;

import q.rorbin.verticaltablayout.adapter.TabAdapter;
import q.rorbin.verticaltablayout.widget.ITabView;

/**
 * Created by androidcat on 2018/11/16.
 */

public class HomeTabAdapter implements TabAdapter {
    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public ITabView.TabBadge getBadge(int position) {
        return null;
    }

    @Override
    public ITabView.TabIcon getIcon(int position) {
        if(position == 0){
            return new ITabView.TabIcon.Builder().setIcon(R.mipmap.tab_regular_pre,R.mipmap.tab_regular_nor).build();
        }
        if(position == 1){
            return new ITabView.TabIcon.Builder().setIcon(R.mipmap.tab_school_pre,R.mipmap.tab_school_nor).build();
        }
        if(position == 2){
            return new ITabView.TabIcon.Builder().setIcon(R.mipmap.tab_analyze_pre,R.mipmap.tab_classes_nor).build();
        }
        if(position == 3){
            return new ITabView.TabIcon.Builder().setIcon(R.mipmap.tab_classes_pre,R.mipmap.tab_classes_nor).build();
        }
        if(position == 4){
            return new ITabView.TabIcon.Builder().setIcon(R.mipmap.tab_settings_pre,R.mipmap.tab_settings_nor).build();
        }
        return null;
    }

    @Override
    public ITabView.TabTitle getTitle(int position) {
//        if(position == 0){
//            return new ITabView.TabTitle.Builder().setContent("日常规考评").setTextSize(16).setTextColor(R.color.text_black,R.color.text_black2).build();
//        }
//        if(position == 1){
//            return new ITabView.TabTitle.Builder().setContent("校务日志").setTextSize(16).setTextColor(R.color.text_black,R.color.text_black2).build();
//        }
//        if(position == 2){
//            return new ITabView.TabTitle.Builder().setContent("班级得分").setTextSize(16).setTextColor(R.color.text_black,R.color.text_black2).build();
//        }
//        if(position == 3){
//            return new ITabView.TabTitle.Builder().setContent("更多设置").setTextSize(16).setTextColor(R.color.text_black,R.color.text_black2).build();
//        }
        return null;
    }

    @Override
    public int getBackground(int position) {

        return 0;
    }
}
