package com.androidcat.yucaiedu.entity;

import com.androidcat.acnet.entity.BaseItem;
import com.androidcat.yucaiedu.R;

public class TeacherItem extends BaseItem {
    private int gender;
    public String genderStr;

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
        if (gender == 1){
            this.picResId = R.mipmap.teacher_male;
        }else {
            this.picResId = R.mipmap.teacher_female;
        }
    }
}
