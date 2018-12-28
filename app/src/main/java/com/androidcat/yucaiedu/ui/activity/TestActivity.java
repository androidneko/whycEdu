package com.androidcat.yucaiedu.ui.activity;

import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.androidcat.acnet.entity.MenuItm;
import com.androidcat.yucaiedu.AppData;
import com.androidcat.yucaiedu.R;
import com.androidcat.yucaiedu.adapter.MyExtendableListViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by androidcat on 2018/12/27.
 */

public class TestActivity extends BaseActivity{

    ExpandableListView expandableListView;

    List<MenuItm> parentMenu = AppData.parentMenu;
    List<ArrayList<MenuItm>> childMenu = AppData.childMenu;

    @Override
    protected int getResID() {
        return R.layout.activity_test;
    }

    @Override
    protected void intLayout() {
        expandableListView = (ExpandableListView)findViewById(R.id.expend_list);
    }

    @Override
    protected void setListener() {

        expandableListView.setAdapter(new MyExtendableListViewAdapter());
        //设置分组的监听
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                Toast.makeText(getApplicationContext(), parentMenu.get(groupPosition).dictLabel, Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        //设置子项布局监听
        /*expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Toast.makeText(getApplicationContext(), childMenu.get(groupPosition).get(childPosition).dictLabel, Toast.LENGTH_SHORT).show();
                return true;

            }
        });*/

        //控制他只能打开一个组
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                int count = new MyExtendableListViewAdapter().getGroupCount();
                for(int i = 0;i < count;i++){
                    if (i!=groupPosition){
                        expandableListView.collapseGroup(i);
                    }
                }
            }
        });
    }

}
