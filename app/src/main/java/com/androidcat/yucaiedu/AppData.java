package com.androidcat.yucaiedu;

import com.androidcat.acnet.entity.MenuItm;
import com.androidcat.acnet.entity.User;
import com.androidcat.acnet.entity.response.BuildingsResponse;
import com.androidcat.acnet.entity.response.GradeListResponse;
import com.androidcat.acnet.entity.response.MenuResponse;

import java.util.HashMap;
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

    static {
        for (int i = 1; i < 15; i++){
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
                menuItm.dictLabel = "上午巡堂";
                menuItm.parent = "课堂管理";
                rcMenuItmMap.put(R.id.morningRb,menuItm);
            }
            if (i==11){
                MenuItm menuItm = new MenuItm();
                menuItm.dictLabel = "午间管理";
                menuItm.parent = "课堂管理";
                rcMenuItmMap.put(R.id.noonRb,menuItm);
            }
            if (i==12){
                MenuItm menuItm = new MenuItm();
                menuItm.dictLabel = "下午巡堂";
                menuItm.parent = "课堂管理";
                rcMenuItmMap.put(R.id.afternoonRb,menuItm);
            }
            if (i==13){
                MenuItm menuItm = new MenuItm();
                menuItm.dictLabel = "教学队列";
                menuItm.parent = "队列管理";
                rcMenuItmMap.put(R.id.tsQueueRb,menuItm);
            }
            if (i==14){
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
                saMenuItmMap.put(R.id.dutyRb,menuItm);
            }
            if (i==3){
                MenuItm menuItm = new MenuItm();
                menuItm.dictLabel = "护校队";
                menuItm.parent = "安全管理";
                saMenuItmMap.put(R.id.protectRb,menuItm);
            }
            if (i==4){
                MenuItm menuItm = new MenuItm();
                menuItm.dictLabel = "特长训练";
                menuItm.parent = "安全管理";
                saMenuItmMap.put(R.id.specialTrainingRb,menuItm);
            }
            if (i==5){
                MenuItm menuItm = new MenuItm();
                menuItm.dictLabel = "门禁安全";
                menuItm.parent = "安全管理";
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
                saMenuItmMap.put(R.id.outsideRb,menuItm);
            }
            if (i==10){
                MenuItm menuItm = new MenuItm();
                menuItm.dictLabel = "文明办公";
                menuItm.parent = "卫生保洁";
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
    }
}
