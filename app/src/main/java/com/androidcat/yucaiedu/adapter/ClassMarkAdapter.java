package com.androidcat.yucaiedu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import com.androidcat.acnet.entity.ClassMark;
import com.androidcat.acnet.entity.Room;
import com.androidcat.yucaiedu.AppData;
import com.androidcat.yucaiedu.R;
import com.androidcat.yucaiedu.ui.listener.OnRoomCheckedListener;

import java.util.List;

public class ClassMarkAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    List<ClassMark> classMarks;

    public ClassMarkAdapter(Context context, List<ClassMark> classMarks) {
        this.context = context;
        this.classMarks = classMarks;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return classMarks.size();
    }

    @Override
    public Object getItem(int position) {
        return classMarks.get(position);
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
            convertView = inflater.inflate(R.layout.item_class_mark, null);
            vh.classTv = convertView.findViewById(R.id.classTv);
            vh.markTv = convertView.findViewById(R.id.markTv);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        //set data
        final ClassMark classMark = classMarks.get(position);
        vh.classTv.setText(classMark.classesName);
        vh.markTv.setText(AppData.markMap.get(classMark.classesAchievement));

        return convertView;
    }

    static class ViewHolder {
        TextView classTv;
        TextView markTv;
    }

}
