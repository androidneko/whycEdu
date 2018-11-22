package com.androidcat.acnet.entity;

import android.support.annotation.NonNull;

import java.io.Serializable;

public class ScoreEntity implements Serializable,Comparable<ScoreEntity>{
    public String claz;
    public int score;
    public int bean;
    public int melon;
    public int left;

    @Override
    public int compareTo(@NonNull ScoreEntity scoreEntity) {
        return this.score - scoreEntity.score;
    }
}
