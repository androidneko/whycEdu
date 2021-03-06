package com.androidcat.acnet.entity;

import java.io.Serializable;
import java.util.List;

public class Room implements Serializable{
    public String parentId;
    public String deptId;
    public String deptName;
    public boolean isEmpty;
    public boolean isChecked;
    public String teacher;
    public String project;
    public int orderNum;
    public String scores;
    public List<Classroom> classesList;
}
