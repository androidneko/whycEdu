package com.androidcat.yucaiedu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.androidcat.acnet.entity.ClassMark;
import com.androidcat.acnet.entity.MarkClassItem;
import com.androidcat.acnet.entity.MarkItem;
import com.androidcat.acnet.entity.MarkRoomItem;
import com.androidcat.acnet.entity.MarkTeacherItem;
import com.androidcat.acnet.entity.Room;
import com.androidcat.yucaiedu.AppData;
import com.androidcat.yucaiedu.R;
import com.androidcat.yucaiedu.ui.listener.OnItemCheckedListener;

import java.util.List;

public class TobeMarkedAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    List<MarkItem> markItems;
    public OnItemCheckedListener onItemCheckedListener;

    public TobeMarkedAdapter(Context context, List<MarkItem> markItems) {
        this.context = context;
        this.markItems = markItems;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return markItems.size();
    }

    @Override
    public Object getItem(int position) {
        return markItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            vh = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_marker, null);
            vh.markerIv = convertView.findViewById(R.id.markerIv);
            vh.markerRb = convertView.findViewById(R.id.markerRb);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        //set data
        final MarkItem markItem = markItems.get(position);
        if (markItem instanceof MarkTeacherItem){
            vh.markerIv.setBackgroundResource(((MarkTeacherItem)markItem).sex==1?R.mipmap.teacher_female:R.mipmap.teacher_male);
            vh.markerRb.setText(((MarkTeacherItem)markItem).userName);
        }
        if (markItem instanceof MarkRoomItem){
            vh.markerIv.setBackgroundResource(R.mipmap.office);
            vh.markerRb.setText(((MarkRoomItem)markItem).deptName);
        }
        if (markItem instanceof MarkClassItem){
            vh.markerIv.setBackgroundResource(R.mipmap.classroom);
            vh.markerRb.setText(((MarkClassItem)markItem).deptName);
        }
        if (markItem.isChecked){
            vh.markerRb.setChecked(true);
        }else {
            vh.markerRb.setChecked(false);
        }

        //当RadioButton被选中时，将其状态记录进States中，并更新其他RadioButton的状态使它们不被选中
        vh.markerRb.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(markItem.isChecked) return;
                for (MarkItem item : markItems){
                    item.isChecked = false;
                }
                markItem.isChecked = true;
                if(onItemCheckedListener != null)
                    onItemCheckedListener.onItemChecked(markItem);
                notifyDataSetChanged();
            }
        });

        return convertView;
    }

    static class ViewHolder {
        ImageView markerIv;
        RadioButton markerRb;
    }

}
