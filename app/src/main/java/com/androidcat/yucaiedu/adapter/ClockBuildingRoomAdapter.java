package com.androidcat.yucaiedu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;

import com.androidcat.acnet.entity.Room;
import com.androidcat.utilities.LogUtil;
import com.androidcat.yucaiedu.R;

import java.util.List;

public class ClockBuildingRoomAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private List<Room> rooms;

    public ClockBuildingRoomAdapter(Context context, List<Room> rooms) {
        this.context = context;
        this.rooms = rooms;
        this.inflater = LayoutInflater.from(context);
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
            convertView = inflater.inflate(R.layout.item_room_clockbuilding, null);
            vh.classTv = convertView.findViewById(R.id.roomRb);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        //set data
        final Room room = rooms.get(position);
        vh.classTv.setText(room.name);
        if (room.isChecked){
            vh.classTv.setChecked(true);
        }else {
            vh.classTv.setChecked(false);
        }
        //当RadioButton被选中时，将其状态记录进States中，并更新其他RadioButton的状态使它们不被选中
        vh.classTv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                room.isChecked = true;
                for (Room item : rooms){
                    if (item.name.equals(room.name)){
                        LogUtil.e("","textMenu.text.equals(menu.text)->"+room.name);
                        continue;
                    }
                    item.isChecked = false;
                }
                notifyDataSetChanged();
            }
        });
        return convertView;
    }

    static class ViewHolder {
        RadioButton classTv;
    }

}
