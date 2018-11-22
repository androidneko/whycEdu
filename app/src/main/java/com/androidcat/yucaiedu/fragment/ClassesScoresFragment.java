package com.androidcat.yucaiedu.fragment;

import android.os.Message;
import android.widget.ListView;

import com.androidcat.acnet.entity.ScoreEntity;
import com.androidcat.yucaiedu.R;
import com.androidcat.yucaiedu.adapter.ClassScoreAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@ActivityFragmentInject(
        contentViewId = R.layout.fragment_classes_scores,
        hasNavigationView = false)
public class ClassesScoresFragment extends BaseFragment {

    List<ScoreEntity> scoreEntities = new ArrayList<>();
    ClassScoreAdapter adapter;

    ListView scoreList;

    @Override
    protected void toHandleMessage(Message msg) {

    }

    @Override
    protected void findViewAfterViewCreate() {
        if (scoreList == null){
            scoreList = mRootView.findViewById(R.id.scoreList);
        }
    }

    @Override
    protected void initDataAfterFindView() {
        adapter = new ClassScoreAdapter(getActivity(),scoreEntities);
        scoreList.setAdapter(adapter);
    }

    @Override
    protected void initDataBeforeViewCreate() {
        for(int i = 0;i<10;i++){
            ScoreEntity entity = new ScoreEntity();
            entity.claz = "五"+ (i+1) + "班";
            entity.score = new Random().nextInt(100);
            entity.bean = new Random().nextInt(10);
            entity.melon = new Random().nextInt(10);
            entity.left = new Random().nextInt(10);
            scoreEntities.add(entity);
        }
    }
}
