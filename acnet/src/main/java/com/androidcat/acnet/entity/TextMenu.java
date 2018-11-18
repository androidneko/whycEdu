package com.androidcat.acnet.entity;

import java.io.Serializable;

public class TextMenu implements Serializable {
    public String text;
    public boolean isChecked;

    public TextMenu(String text){
        this(text,false);
    }

    public TextMenu(String text,boolean isChecked){
        this.isChecked = isChecked;
        this.text = text;
    }
}
