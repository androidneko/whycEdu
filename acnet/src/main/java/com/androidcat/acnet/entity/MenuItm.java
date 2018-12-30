package com.androidcat.acnet.entity;

import java.io.Serializable;

public class MenuItm implements Serializable {
    public int dictCode;
    public String dictLabel;
    public String dictValue;
    public String dictType;
    public String parent;
    public String desc;
    public String memo;
    public boolean isChecked;
}
