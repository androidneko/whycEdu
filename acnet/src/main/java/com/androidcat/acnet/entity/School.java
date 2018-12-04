package com.androidcat.acnet.entity;

import java.io.Serializable;
import java.util.List;

public class School implements Serializable{
    public String deptName;
    public String deptId;
    public String parentId;
    public int orderNum;
    public boolean isEmpty;
    public boolean isChecked;
    public String desc;
    public List<Building> children;
}
