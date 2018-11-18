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
            return new ITabView.TabIcon.Builder().setIcon(R.mipmap.ic_orders,R.mipmap.ic_orders).build();
        }
        if(position == 1){
            return new ITabView.TabIcon.Builder().setIcon(R.mipmap.ic_pay,R.mipmap.ic_pay).build();
        }
        if(position == 2){
            return new ITabView.TabIcon.Builder().setIcon(R.mipmap.ic_recharge,R.mipmap.ic_recharge).build();
        }
        if(position == 3){
            return new ITabView.TabIcon.Builder().setIcon(R.mipmap.ic_stations,R.mipmap.ic_stations).build();
        }
        if(position == 4){
            return new ITabView.TabIcon.Builder().setIcon(R.mipmap.ic_stations,R.mipmap.ic_stations).build();
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
