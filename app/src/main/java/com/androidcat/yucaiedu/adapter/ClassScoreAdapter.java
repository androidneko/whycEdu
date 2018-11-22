package com.androidcat.yucaiedu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.androidcat.acnet.entity.ScoreEntity;
import com.androidcat.yucaiedu.R;

import java.util.List;

public class ClassScoreAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private List<ScoreEntity> scoreEntities;

    public ClassScoreAdapter(Context context, List<ScoreEntity> cities) {
        this.context = context;
        this.scoreEntities = cities;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return scoreEntities.size();
    }

    @Override
    public Object getItem(int position) {
        return scoreEntities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            vh = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_score, null);
            vh.classTv = (TextView) convertView.findViewById(R.id.classTv);
            vh.scoreTv = (TextView) convertView.findViewById(R.id.scoreTv);
            vh.beanTv = (TextView) convertView.findViewById(R.id.beanTv);
            vh.melonTv = (TextView) convertView.findViewById(R.id.melonTv);
            vh.scoreLeftTv = (TextView) convertView.findViewById(R.id.scoreLeftTv);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        //set data
        final ScoreEntity scoreEntity = scoreEntities.get(position);
        vh.classTv.setText(scoreEntity.claz);
        vh.scoreTv.setText(""+scoreEntity.score);
        vh.beanTv.setText("x"+scoreEntity.bean);
        vh.melonTv.setText("x"+scoreEntity.melon);
        vh.scoreLeftTv.setText(""+scoreEntity.left);

        if (position % 2 == 0){
            convertView.setBackgroundColor(context.getResources().getColor(R.color.white));
        } else {
            convertView.setBackgroundColor(context.getResources().getColor(R.color.theme_green));
        }
        return convertView;
    }

    static class ViewHolder {
        TextView classTv;
        TextView scoreTv;
        TextView beanTv;
        TextView melonTv;
        TextView scoreLeftTv;
    }

}
