package com.androidcat.yucaiedu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

import com.androidcat.acnet.entity.TextMenu;
import com.androidcat.utilities.LogUtil;
import com.androidcat.yucaiedu.R;

import java.util.List;

public class ExpandableAdapter extends BaseExpandableListAdapter{
    private List<TextMenu> group;
    private List<List<TextMenu>> child;
    private Context context;
    public ExpandableAdapter(Context context,List<TextMenu> menu,List<List<TextMenu>> child){
        this.context = context;
        this.group = menu;
        this.child = child;
    }

    @Override
    public int getGroupCount() {
        return group.size();//获取父布局个数
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return child.get(groupPosition).size();//获取子布局个数
    }

    @Override
    public Object getGroup(int groupPosition) {
        return group.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return child.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
    //父布局样式
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        if(convertView==null){
            LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.menu_item, null);
        }
        TextMenu textMenu = (TextMenu) getGroup(groupPosition);
        TextView menuTxt = (TextView) convertView.findViewById(R.id.menuTxt);
        menuTxt.setText(textMenu.text);
        return convertView;
    }

    //子布局样式
    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        if(convertView==null){
            LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.menu_sub_item, null);
        }
        final TextMenu textMenu = (TextMenu) getChild(groupPosition, childPosition);
        final RadioButton menuTxt = convertView.findViewById(R.id.subMenuTxt);
        menuTxt.setText(textMenu.text);
        if (textMenu.isChecked){
            menuTxt.setChecked(true);
        }else {
            menuTxt.setChecked(false);
        }
        //当RadioButton被选中时，将其状态记录进States中，并更新其他RadioButton的状态使它们不被选中
        menuTxt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                textMenu.isChecked = true;
                for(List<TextMenu> list : child){
                    for (TextMenu menu : list){
                        if (textMenu.text.equals(menu.text)){
                            LogUtil.e("","textMenu.text.equals(menu.text)->"+menu.text);
                            continue;
                        }
                        menu.isChecked = false;
                    }
                }
                ExpandableAdapter.this.notifyDataSetChanged();
            }
        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}
