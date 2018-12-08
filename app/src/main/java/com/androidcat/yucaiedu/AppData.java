package com.androidcat.yucaiedu;

import android.widget.RadioButton;

import com.androidcat.acnet.entity.MenuItm;
import com.androidcat.acnet.entity.User;
import com.androidcat.acnet.entity.response.BuildingsResponse;
import com.androidcat.acnet.entity.response.GradeListResponse;
import com.androidcat.acnet.entity.response.MenuResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppData {
    private static AppData appData;
    private AppData(){}

    public static AppData getAppData(){
        if(appData == null)
            appData = new AppData();
        return appData;
    }

    public User user;
    public GradeListResponse gradeListResponse ;
    public MenuResponse menuResponse;
    public BuildingsResponse buildingsResponse;

    public static Map<Integer,MenuItm> rcMenuItmMap = new HashMap<>();
    public static Map<Integer,MenuItm> saMenuItmMap = new HashMap<>();

    public static Map<Integer,String> markMap = new HashMap<>();
    public static Map<Integer,Integer> markMenuMap = new HashMap<>();
    public static Map<String,Integer> gradeMap = new HashMap<>();

    static {
        for (int i = 1; i < 20; i++){
            if (i==1){
                MenuItm menuItm = new MenuItm();
                menuItm.dictLabel = "经典诵读";
                menuItm.parent = "自我管理";
                rcMenuItmMap.put(R.id.recitingRb,menuItm);
            }
            if (i==2){
                MenuItm menuItm = new MenuItm();
                menuItm.dictLabel = "晨读";
                menuItm.parent = "自我管理";
                rcMenuItmMap.put(R.id.readingRb,menuItm);
            }
            if (i==3){
                MenuItm menuItm = new MenuItm();
                menuItm.dictLabel = "晨会";
                menuItm.parent = "自我管理";
                rcMenuItmMap.put(R.id.meetingRb,menuItm);
            }
            if (i==4){
                MenuItm menuItm = new MenuItm();
                menuItm.dictLabel = "广播操";
                menuItm.parent = "两操管理";
                rcMenuItmMap.put(R.id.exerciseRb,menuItm);
            }
            if (i==5){
                MenuItm menuItm = new MenuItm();
                menuItm.dictLabel = "眼保操";
                menuItm.parent = "自我管理";
                rcMenuItmMap.put(R.id.eyeExerciseRb,menuItm);
            }
            if (i==6){
                MenuItm menuItm = new MenuItm();
                menuItm.dictLabel = "大课间";
                menuItm.parent = "安全文明";
                rcMenuItmMap.put(R.id.restTimeRb,menuItm);
            }
            if (i==7){
                MenuItm menuItm = new MenuItm();
                menuItm.dictLabel = "好人好事";
                menuItm.parent = "安全文明";
                rcMenuItmMap.put(R.id.goodThingsRb,menuItm);
            }
            if (i==8){
                MenuItm menuItm = new MenuItm();
                menuItm.dictLabel = "班级卫生";
                menuItm.parent = "卫生节能";
                rcMenuItmMap.put(R.id.healthRb,menuItm);
            }
            if (i==9){
                MenuItm menuItm = new MenuItm();
                menuItm.dictLabel = "班级节能";
                menuItm.parent = "卫生节能";
                rcMenuItmMap.put(R.id.energyRb,menuItm);
            }
            if (i==10){
                MenuItm menuItm = new MenuItm();
                menuItm.dictLabel = "第一节";
                menuItm.parent = "上午巡堂";
                rcMenuItmMap.put(R.id.firstRb,menuItm);
            }
            if (i==11){
                MenuItm menuItm = new MenuItm();
                menuItm.dictLabel = "第二节";
                menuItm.parent = "上午巡堂";
                rcMenuItmMap.put(R.id.secondRb,menuItm);
            }
            if (i==12){
                MenuItm menuItm = new MenuItm();
                menuItm.dictLabel = "第三节";
                menuItm.parent = "上午巡堂";
                rcMenuItmMap.put(R.id.thirdRb,menuItm);
            }
            if (i==13){
                MenuItm menuItm = new MenuItm();
                menuItm.dictLabel = "第四节";
                menuItm.parent = "上午巡堂";
                rcMenuItmMap.put(R.id.forthRb,menuItm);
            }
            if (i==14){
                MenuItm menuItm = new MenuItm();
                menuItm.dictLabel = "午间管理";
                menuItm.parent = "课堂管理";
                rcMenuItmMap.put(R.id.noonRb,menuItm);
            }
            if (i==15){
                MenuItm menuItm = new MenuItm();
                menuItm.dictLabel = "第五节";
                menuItm.parent = "下午巡堂";
                rcMenuItmMap.put(R.id.fifthRb,menuItm);
            }
            if (i==16){
                MenuItm menuItm = new MenuItm();
                menuItm.dictLabel = "第六节";
                menuItm.parent = "下午巡堂";
                rcMenuItmMap.put(R.id.sixthRb,menuItm);
            }
            if (i==17){
                MenuItm menuItm = new MenuItm();
                menuItm.dictLabel = "第七节";
                menuItm.parent = "下午巡堂";
                rcMenuItmMap.put(R.id.seventhRb,menuItm);
            }
            if (i==18){
                MenuItm menuItm = new MenuItm();
                menuItm.dictLabel = "教学队列";
                menuItm.parent = "队列管理";
                rcMenuItmMap.put(R.id.tsQueueRb,menuItm);
            }
            if (i==19){
                MenuItm menuItm = new MenuItm();
                menuItm.dictLabel = "放学队列";
                menuItm.parent = "队列管理";
                rcMenuItmMap.put(R.id.afterSchoolRb,menuItm);
            }
        }

        //校务日志菜单
        for (int i = 1; i < 12; i++){
            if (i==1){
                MenuItm menuItm = new MenuItm();
                menuItm.dictLabel = "大事记";
                menuItm.parent = "";
                saMenuItmMap.put(R.id.eventRb,menuItm);
            }
            if (i==2){
                MenuItm menuItm = new MenuItm();
                menuItm.dictLabel = "教师执勤";
                menuItm.parent = "安全管理";
                menuItm.desc = "考评标准:1按时到岗 2佩戴袖章 3巡查提醒";
                menuItm.memo = "教师成员列表";
                saMenuItmMap.put(R.id.dutyRb,menuItm);
            }
            if (i==3){
                MenuItm menuItm = new MenuItm();
                menuItm.dictLabel = "护校队";
                menuItm.parent = "安全管理";
                menuItm.desc = "考评标准:1按时到岗 2佩戴袖章 3巡查提醒";
                menuItm.memo = "教师成员列表";
                saMenuItmMap.put(R.id.protectRb,menuItm);
            }
            if (i==4){
                MenuItm menuItm = new MenuItm();
                menuItm.dictLabel = "特长训练";
                menuItm.parent = "安全管理";
                menuItm.desc = "考评标准:1准时训练 2组织有序";
                menuItm.memo = "教师成员列表";
                saMenuItmMap.put(R.id.specialTrainingRb,menuItm);
            }
            if (i==5){
                MenuItm menuItm = new MenuItm();
                menuItm.dictLabel = "门禁安全";
                menuItm.parent = "安全管理";
                menuItm.desc = "考评标准:1电梯间随手关门 2学生离校出具班主任签条 3不通知家长到学校送学具等物品";
                menuItm.memo = "教师成员列表";
                saMenuItmMap.put(R.id.safetyRb,menuItm);
            }
            if (i==6){
                MenuItm menuItm = new MenuItm();
                menuItm.dictLabel = "意外伤害";
                menuItm.parent = "安全管理";
                saMenuItmMap.put(R.id.accidentRb,menuItm);
            }
            if (i==7){
                MenuItm menuItm = new MenuItm();
                menuItm.dictLabel = "放学清场";
                menuItm.parent = "安全管理";
                menuItm.desc = "";
                menuItm.memo = "班级列表";
                saMenuItmMap.put(R.id.clearRb,menuItm);
            }
            if (i==8){
                MenuItm menuItm = new MenuItm();
                menuItm.dictLabel = "离校巡查";
                menuItm.parent = "安全管理";
                saMenuItmMap.put(R.id.leavingRb,menuItm);
            }
            if (i==9){
                MenuItm menuItm = new MenuItm();
                menuItm.dictLabel = "室外保洁";
                menuItm.parent = "卫生保洁";
                menuItm.memo = "保洁员列表";
                menuItm.desc = "考评标准:1准时训练 2组织有序";
                saMenuItmMap.put(R.id.outsideRb,menuItm);
            }
            if (i==10){
                MenuItm menuItm = new MenuItm();
                menuItm.dictLabel = "文明办公";
                menuItm.parent = "卫生保洁";
                menuItm.desc = "考评标准:1准时训练 2组织有序";
                menuItm.memo = "办公室列表";
                saMenuItmMap.put(R.id.workingRb,menuItm);
            }
            if (i==11){
                MenuItm menuItm = new MenuItm();
                menuItm.dictLabel = "备注";
                menuItm.parent = "";
                saMenuItmMap.put(R.id.memoRb,menuItm);
            }
        }

        //评分字典
        markMap.put(3,"A");
        markMap.put(2,"B");
        markMap.put(1,"C");

        markMenuMap.put(R.id.aRb,3);
        markMenuMap.put(R.id.bRb,2);
        markMenuMap.put(R.id.cRb,1);

        gradeMap.put("一年级",100);
        gradeMap.put("二年级",200);
        gradeMap.put("三年级",300);
        gradeMap.put("四年级",400);
        gradeMap.put("五年级",500);
        gradeMap.put("六年级",600);
    }












    /*void loadMenu(){
        List<MenuItm> menuItmList = AppData.getAppData().menuResponse.content;
        for (int i =0;i<menuItmList.size();i++){
            MenuItm menuItm = menuItmList.get(i);
            if (menuItm != null){
                if (i==0){
                    AppData.rcMenuItmMap.put(R.id.recitingRb,menuItm);
                    ((RadioButton)mRootView.findViewById(R.id.recitingRb)).setText(menuItm.dictLabel);
                }
                if (i==1){
                    AppData.rcMenuItmMap.put(R.id.readingRb,menuItm);
                    ((RadioButton)mRootView.findViewById(R.id.readingRb)).setText(menuItm.dictLabel);
                }
                if (i==2){
                    AppData.rcMenuItmMap.put(R.id.meetingRb,menuItm);
                    ((RadioButton)mRootView.findViewById(R.id.meetingRb)).setText(menuItm.dictLabel);
                }
                if (i==3){
                    AppData.rcMenuItmMap.put(R.id.exerciseRb,menuItm);
                    ((RadioButton)mRootView.findViewById(R.id.exerciseRb)).setText(menuItm.dictLabel);
                }
                if (i==4){
                    AppData.rcMenuItmMap.put(R.id.eyeExerciseRb,menuItm);
                    ((RadioButton)mRootView.findViewById(R.id.eyeExerciseRb)).setText(menuItm.dictLabel);
                }
                if (i==5){
                    AppData.rcMenuItmMap.put(R.id.restTimeRb,menuItm);
                    ((RadioButton)mRootView.findViewById(R.id.restTimeRb)).setText(menuItm.dictLabel);
                }
                if (i==6){
                    AppData.rcMenuItmMap.put(R.id.goodThingsRb,menuItm);
                    ((RadioButton)mRootView.findViewById(R.id.goodThingsRb)).setText(menuItm.dictLabel);
                }
                if (i==7){
                    AppData.rcMenuItmMap.put(R.id.healthRb,menuItm);
                    ((RadioButton)mRootView.findViewById(R.id.healthRb)).setText(menuItm.dictLabel);
                }
                if (i==8){
                    AppData.rcMenuItmMap.put(R.id.energyRb,menuItm);
                    ((RadioButton)mRootView.findViewById(R.id.energyRb)).setText(menuItm.dictLabel);
                }
                if (i==9){
                    AppData.rcMenuItmMap.put(R.id.morningRb,menuItm);
                    ((RadioButton)mRootView.findViewById(R.id.morningRb)).setText(menuItm.dictLabel);
                }
                if (i==10){
                    AppData.rcMenuItmMap.put(R.id.noonRb,menuItm);
                    ((RadioButton)mRootView.findViewById(R.id.noonRb)).setText(menuItm.dictLabel);
                }
                if (i==11){
                    AppData.rcMenuItmMap.put(R.id.afternoonRb,menuItm);
                    ((RadioButton)mRootView.findViewById(R.id.afternoonRb)).setText(menuItm.dictLabel);
                }
                if (i==12){
                    AppData.rcMenuItmMap.put(R.id.tsQueueRb,menuItm);
                    ((RadioButton)mRootView.findViewById(R.id.tsQueueRb)).setText(menuItm.dictLabel);
                }
                if (i==13){
                    AppData.rcMenuItmMap.put(R.id.afterSchoolRb,menuItm);
                    ((RadioButton)mRootView.findViewById(R.id.afterSchoolRb)).setText(menuItm.dictLabel);
                }
            }
        }

        menuRc.check(R.id.recitingRb);
    }*/
}
