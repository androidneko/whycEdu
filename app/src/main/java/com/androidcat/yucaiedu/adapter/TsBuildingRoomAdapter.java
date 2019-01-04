package com.androidcat.yucaiedu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import com.androidcat.acnet.entity.Room;
import com.androidcat.utilities.Utils;
import com.androidcat.yucaiedu.AppData;
import com.androidcat.yucaiedu.R;
import com.androidcat.yucaiedu.ui.listener.OnRoomCheckedListener;

import java.util.List;

public class TsBuildingRoomAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    public List<Room> rooms;
    public Room checkedRoom;
    public OnRoomCheckedListener onRoomCheckedListener;

    public TsBuildingRoomAdapter(Context context, List<Room> rooms) {
        this.context = context;
        this.rooms = rooms;
        this.inflater = LayoutInflater.from(context);
    }

    public void clearCheck(){
        checkedRoom = null;
        for (Room room : rooms){
            room.isChecked = false;
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return rooms.size();
    }

    @Override
    public Object getItem(int position) {
        return rooms.get(position);
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
            convertView = inflater.inflate(R.layout.item_room_tsbuilding, null);
            vh.classTv = convertView.findViewById(R.id.roomRb);
            vh.mark = convertView.findViewById(R.id.mark);
            vh.descTv = convertView.findViewById(R.id.descTv);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        //set data
        final Room room = rooms.get(position);
        if (room.classesList==null || room.classesList.size()==0){
            room.isEmpty = true;
            vh.classTv.setClickable(false);
        }else {
            vh.classTv.setClickable(true);
        }
        vh.classTv.setText("空闲".equals(room.deptName)?"":room.deptName);
        if (room.isChecked){
            vh.classTv.setChecked(true);
        }else {
            vh.classTv.setChecked(false);
        }
        if (!Utils.isNull(room.scores) && !"0".equals(room.scores)){
            int drawableRes = AppData.markLogoMap.get(room.scores);
            vh.mark.setBackgroundResource(drawableRes);
        }else {
            vh.mark.setBackgroundResource(0);
        }
        if (!Utils.isNull(room.desc)){
            vh.descTv.setText(room.desc);
        }
        if (!room.isEmpty){
            //当RadioButton被选中时，将其状态记录进States中，并更新其他RadioButton的状态使它们不被选中
            vh.classTv.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if(room.isChecked || room.isEmpty) return;
                    for (Room item : rooms){
                        item.isChecked = false;
                    }
                    room.isChecked = true;
                    checkedRoom = room;
                    if(onRoomCheckedListener != null)
                        onRoomCheckedListener.onRoomChecked(room);
                    notifyDataSetChanged();
                }
            });
        }
        return convertView;
    }

    static class ViewHolder {
        RadioButton classTv;
        View mark;
        TextView descTv;
    }

}
