package com.androidcat.acnet.entity;

import java.io.Serializable;

public class Room implements Serializable{
    public String parentId;
    public String deptId;
    public String deptName;
    public boolean isEmpty;
    public boolean isChecked;
    public String desc;
    public int orderNum;
}
