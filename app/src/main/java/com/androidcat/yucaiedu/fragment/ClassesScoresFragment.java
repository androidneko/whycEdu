package com.androidcat.yucaiedu.fragment;

import android.os.Message;
import android.view.View;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.androidcat.acnet.consts.OptMsgConst;
import com.androidcat.acnet.entity.ScoreEntity;
import com.androidcat.acnet.entity.response.GradeListResponse;
import com.androidcat.acnet.entity.response.ScoreListResponse;
import com.androidcat.acnet.manager.ClassesManager;
import com.androidcat.utilities.Utils;
import com.androidcat.utilities.listener.OnSingleClickListener;
import com.androidcat.yucaiedu.AppData;
import com.androidcat.yucaiedu.R;
import com.androidcat.yucaiedu.adapter.ClassScoreAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
public class ClassesScoresFragment extends BaseFragment {
    private RadioGroup gradeRg;
    private RadioGroup typeRg;
    View sortView;
    List<ScoreEntity> scoreEntities = new ArrayList<>();
    ClassScoreAdapter adapter;

    ListView scoreList;
    ClassesManager classesManager;

    String curGrade = "六年级";
    int curType = 1;
    static Map<Integer,Integer> typeMap = new HashMap<>();
    static Map<Integer,String> menuMap = new HashMap<>();
    static {
        menuMap.put(R.id.grade1Rb,"一年级");
        menuMap.put(R.id.grade2Rb,"二年级");
        menuMap.put(R.id.grade3Rb,"三年级");
        menuMap.put(R.id.grade4Rb,"四年级");
        menuMap.put(R.id.grade5Rb,"五年级");
        menuMap.put(R.id.grade6Rb,"六年级");

        typeMap.put(R.id.weekRb,1);
        typeMap.put(R.id.monthRb,2);
        typeMap.put(R.id.termRb,3);
        typeMap.put(R.id.yearRb,4);
    }

    @Override
    protected void childHandleEventMsg(Message msg) {
        super.childHandleEventMsg(msg);
        switch (msg.what) {
            case OptMsgConst.GET_CLASS_SCORES_FAIL:
                dismissLoadingDialog();
                showToast("获取班级得分失败，请确保网络畅通后重试");
                break;
            case OptMsgConst.GET_CLASS_SCORES_START:
                showProgressDialog("正在加载");
                break;
            case OptMsgConst.GET_CLASS_SCORES_SUCCESS:
                dismissLoadingDialog();
                ScoreListResponse scoreListResponse = (ScoreListResponse) msg.obj;
                scoreEntities = scoreListResponse.content;
                setupView();
                break;
            default:
                break;
        }
    }

    private RadioGroup.OnCheckedChangeListener onCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
            if(-1 == checkedId) {
                //此处是清除选中的回调。
                return;
            }
            View checkedView = mRootView.findViewById(checkedId);
            if (checkedView != null && checkedView instanceof RadioButton) {
                if (!((RadioButton) checkedView).isChecked()) {
                    //此处是某个选中按钮被取消的回调，在调用check方法修改选中的时候会触发
                    return;
                }
            }
            if (radioGroup == gradeRg){
                checkMenu(checkedId);
            }
            if (radioGroup == typeRg){
                checkType(checkedId);
            }
        }
    };

    private OnSingleClickListener onSingleClickListener = new OnSingleClickListener() {
        @Override
        public void onSingleClick(View view) {
            if (view == sortView){
                Collections.sort(scoreEntities);
                if (adapter != null) adapter.notifyDataSetChanged();
            }
        }
    };

    @Override
    protected int getResID() {
        return R.layout.fragment_classes_scores;
    }

    @Override
    protected void intLayout() {
        gradeRg = mRootView.findViewById(R.id.gradeRg);
        typeRg = mRootView.findViewById(R.id.typeRg);
        sortView = mRootView.findViewById(R.id.sortView);
        scoreList = mRootView.findViewById(R.id.scoreList);
    }

    @Override
    protected void setListener() {
        sortView.setOnClickListener(onSingleClickListener);
        gradeRg.setOnCheckedChangeListener(onCheckedChangeListener);
        typeRg.setOnCheckedChangeListener(onCheckedChangeListener);
    }

    @Override
    protected void initModule() {
        classesManager = new ClassesManager(this.getActivity(), baseHandler);
        getScoreList();
    }

    void getScoreList(){
        String loginName = AppData.getAppData().user.loginName;
        String token = AppData.getAppData().user.token;
        int gradeId = AppData.gradeMap.get(curGrade);
        int type = curType;
        classesManager.scoreList(loginName,token,gradeId,type);
    }

    void checkMenu(int checkedId){
        curGrade = menuMap.get(checkedId);
        getScoreList();
    }

    void checkType(int checkedId){
        curType = typeMap.get(checkedId);
        getScoreList();
    }

    @Override
    public void iOnResume() {
        super.iOnResume();
    }
    void setupView(){
        adapter = new ClassScoreAdapter(getActivity(),scoreEntities);
        scoreList.setAdapter(adapter);
    }
}
