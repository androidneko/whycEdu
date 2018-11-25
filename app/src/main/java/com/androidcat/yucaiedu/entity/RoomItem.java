package com.androidcat.yucaiedu.entity;

import com.androidcat.acnet.entity.BaseItem;
import com.androidcat.yucaiedu.R;

public class RoomItem extends BaseItem {

    public void setType(int type){
        this.type = type;
        if (type == 1){
            this.picResId = R.mipmap.classroom;
        }else {
            this.picResId = R.mipmap.office;
        }
    }
}
