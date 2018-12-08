package com.androidcat.acnet.entity;

import java.io.Serializable;

public class MarkItem implements Serializable{
    public String desc;
    public int teacherScoreId;
    public int grade = -1;
    public boolean isChecked;
    public int teacherId;
    public String teacherName;
    public String remark = "";
}
