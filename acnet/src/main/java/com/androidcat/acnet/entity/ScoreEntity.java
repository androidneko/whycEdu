package com.androidcat.acnet.entity;

import android.support.annotation.NonNull;

import java.io.Serializable;

public class ScoreEntity implements Serializable,Comparable<ScoreEntity>{
    public String classesName;
    public int count;
    public int doumei;
    public int guage;
    public int shengyu;

    @Override
    public int compareTo(@NonNull ScoreEntity scoreEntity) {
        return scoreEntity.count - this.count;
    }
}
