package com.androidcat.yucaiedu.fragment;

import android.content.Intent;
import android.os.Message;
import android.widget.ListView;

import com.androidcat.acnet.consts.OptMsgConst;
import com.androidcat.acnet.entity.ScoreEntity;
import com.androidcat.acnet.entity.response.GradeListResponse;
import com.androidcat.acnet.entity.response.LoginResponse;
import com.androidcat.acnet.manager.ClassesManager;
import com.androidcat.acnet.manager.UserManager;
import com.androidcat.utilities.Utils;
import com.androidcat.utilities.persistence.SPConsts;
import com.androidcat.utilities.persistence.SharePreferencesUtil;
import com.androidcat.yucaiedu.AppData;
import com.androidcat.yucaiedu.R;
import com.androidcat.yucaiedu.adapter.ClassScoreAdapter;
import com.androidcat.yucaiedu.ui.HomeActivity;
import com.androidcat.yucaiedu.ui.LoginActivity;

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
    ClassesManager classesManager;

    @Override
    protected void toHandleMessage(Message msg) {
        switch (msg.what) {
            case OptMsgConst.GRADE_LIST_FAIL:
                dismissLoadingDialog();
                if (msg.obj == null || Utils.isNull((String) msg.obj)) {
                    showToast("获取年级列表失败");
                } else {
                    showToast((String) msg.obj);
                }
                break;
            case OptMsgConst.MSG_LOGIN_START:
                break;
            case OptMsgConst.MSG_LOGIN_SUCCESS:
                dismissLoadingDialog();
                AppData.getAppData().gradeListResponse = (GradeListResponse) msg.obj;
                break;
            default:
                break;
        }
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
        classesManager.getGradeList(user.loginName,user.token);
    }

    @Override
    protected void initDataBeforeViewCreate() {
        classesManager = new ClassesManager(this.getActivity(), mHandler);
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
