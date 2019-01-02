package com.androidcat.yucaiedu;

import com.androidcat.acnet.entity.Classroom;
import com.androidcat.acnet.entity.MenuItm;
import com.androidcat.acnet.entity.TextMenu;
import com.androidcat.acnet.entity.User;
import com.androidcat.acnet.entity.response.BuildingsResponse;
import com.androidcat.acnet.entity.response.GradeListResponse;
import com.androidcat.acnet.entity.response.MenuResponse;
import com.androidcat.utilities.LogUtil;
import com.androidcat.utilities.persistence.SpUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppData {
    private static final String TAG = "AppData";
    private static AppData appData;
    private AppData(){
    }

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
    public static Map<String,Integer> markLogoMap = new HashMap<>();
    public static Map<Integer,Integer> markMenuMap = new HashMap<>();
    public static Map<String,Integer> gradeMap = new HashMap<>();
    public static Map<Integer,String> eventMenuMap = new HashMap<>();
    public static List<Classroom> grade1Classes = new ArrayList<>();
    public static List<Classroom> grade2Classes = new ArrayList<>();
    public static List<Classroom> grade3Classes = new ArrayList<>();
    public static List<Classroom> grade4Classes = new ArrayList<>();
    public static List<Classroom> grade5Classes = new ArrayList<>();
    public static List<Classroom> grade6Classes = new ArrayList<>();

    public static List<MenuItm> parentMenu = new ArrayList();
    public static List<ArrayList<MenuItm>> childMenu = new ArrayList<ArrayList<MenuItm>>();

    public void loadData(){
        loadRcMenu();
        loadSaMenu();
        //评分字典
        markMap.put(3,"A");
        markMap.put(2,"B");
        markMap.put(1,"C");
        markLogoMap.put("3",R.drawable.gold);
        markLogoMap.put("2",R.drawable.silver);
        markLogoMap.put("1",R.drawable.copper);

        markMenuMap.put(R.id.aRb,3);
        markMenuMap.put(R.id.bRb,2);
        markMenuMap.put(R.id.cRb,1);

        gradeMap.put("一年级",100);
        gradeMap.put("二年级",200);
        gradeMap.put("三年级",300);
        gradeMap.put("四年级",400);
        gradeMap.put("五年级",500);
        gradeMap.put("六年级",600);

        eventMenuMap.put(0,"学术交流");
        eventMenuMap.put(1,"课程开发");
        eventMenuMap.put(2,"课堂教学");
        eventMenuMap.put(3,"教研活动");
        eventMenuMap.put(4,"学生活动");
    }

    public void loadSaMenu(){
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
    }

    public void loadRcMenu() {
        long now = System.currentTimeMillis();

        for (int k = 0;k < 5;k++){
            if (k == 0){
                MenuItm menuItm = new MenuItm();
                menuItm.dictLabel = "自我管理";
                parentMenu.add(menuItm);
                ArrayList<MenuItm> child = new ArrayList();
                for (int i = 1;i <= 5;i++){
                    if (i == 1) {
                        MenuItm childItem = new MenuItm();
                        childItem.dictLabel = "经典诵读";
                        childItem.parent = "自我管理";
                        childItem.isChecked = true;
                        child.add(childItem);
                        rcMenuItmMap.put(R.id.recitingRb, childItem);
                    }
                    if (i == 2) {
                        MenuItm childItem = new MenuItm();
                        childItem.dictLabel = "晨读";
                        childItem.parent = "自我管理";
                        child.add(childItem);
                        rcMenuItmMap.put(R.id.readingRb, childItem);
                    }
                    if (i == 3) {
                        MenuItm childItem = new MenuItm();
                        childItem.dictLabel = "晨会";
                        childItem.parent = "自我管理";
                        child.add(childItem);
                        rcMenuItmMap.put(R.id.meetingRb, childItem);
                    }
                    if (i == 4) {
                        MenuItm childItem = new MenuItm();
                        childItem.dictLabel = "广播操";
                        childItem.parent = "两操管理";
                        child.add(childItem);
                        rcMenuItmMap.put(R.id.exerciseRb, childItem);
                    }
                    if (i == 5) {
                        MenuItm childItem = new MenuItm();
                        childItem.dictLabel = "眼保操";
                        childItem.parent = "自我管理";
                        child.add(childItem);
                        rcMenuItmMap.put(R.id.eyeExerciseRb, childItem);
                    }
                }
                childMenu.add(child);
            }
            if (k == 1){
                MenuItm menuItm = new MenuItm();
                menuItm.dictLabel = "安全文明";
                parentMenu.add(menuItm);
                ArrayList<MenuItm> child = new ArrayList();
                for (int i = 1;i <= 2;i++){
                    if (i == 1) {
                        MenuItm childItem = new MenuItm();
                        childItem.dictLabel = "大课间";
                        childItem.parent = "安全文明";
                        child.add(childItem);
                        rcMenuItmMap.put(R.id.restTimeRb, childItem);
                    }
                    if (i == 2) {
                        MenuItm childItem = new MenuItm();
                        childItem.dictLabel = "好人好事";
                        childItem.parent = "安全文明";
                        child.add(childItem);
                        rcMenuItmMap.put(R.id.goodThingsRb, childItem);
                    }
                }
                childMenu.add(child);
            }
            if (k == 2){
                MenuItm menuItm = new MenuItm();
                menuItm.dictLabel = "卫生节能";
                parentMenu.add(menuItm);
                ArrayList<MenuItm> child = new ArrayList();
                for (int i = 1;i <= 2;i++){
                    if (i == 1) {
                        MenuItm childItem = new MenuItm();
                        childItem.dictLabel = "班级卫生";
                        childItem.parent = "卫生节能";
                        child.add(childItem);
                        rcMenuItmMap.put(R.id.healthRb, childItem);
                    }
                    if (i == 2) {
                        MenuItm childItem = new MenuItm();
                        childItem.dictLabel = "班级节能";
                        childItem.parent = "卫生节能";
                        child.add(childItem);
                        rcMenuItmMap.put(R.id.energyRb, childItem);
                    }
                }
                childMenu.add(child);
            }
            if (k == 3){
                MenuItm menuItm = new MenuItm();
                menuItm.dictLabel = "课堂管理";
                parentMenu.add(menuItm);
                ArrayList<MenuItm> child = new ArrayList();
                for (int i = 1;i <= 3;i++){
                    if (i == 1) {
                        MenuItm childItem = new MenuItm();
                        childItem.dictLabel = "上午巡堂";
                        childItem.parent = "课堂管理";
                        child.add(childItem);
                        rcMenuItmMap.put(R.id.firstRb, childItem);
                    }
                    if (i == 2) {
                        MenuItm childItem = new MenuItm();
                        childItem.dictLabel = "午间管理";
                        childItem.parent = "课堂管理";
                        child.add(childItem);
                        rcMenuItmMap.put(R.id.noonRb, childItem);
                    }
                    if (i == 3) {
                        MenuItm childItem = new MenuItm();
                        childItem.dictLabel = "下午巡堂";
                        childItem.parent = "课堂管理";
                        child.add(childItem);
                        rcMenuItmMap.put(R.id.sixthRb, childItem);
                    }
                }
                childMenu.add(child);
            }
            if (k == 4){
                MenuItm menuItm = new MenuItm();
                menuItm.dictLabel = "队列管理";
                parentMenu.add(menuItm);
                ArrayList<MenuItm> child = new ArrayList();
                for (int i = 1;i <= 2;i++){
                    if (i == 1) {
                        MenuItm childItem = new MenuItm();
                        childItem.dictLabel = "教学队列";
                        childItem.parent = "队列管理";
                        child.add(childItem);
                        rcMenuItmMap.put(R.id.tsQueueRb, childItem);
                    }
                    if (i == 2) {
                        MenuItm childItem = new MenuItm();
                        childItem.dictLabel = "放学队列";
                        childItem.parent = "队列管理";
                        child.add(childItem);
                        rcMenuItmMap.put(R.id.afterSchoolRb, childItem);
                    }
                }
                childMenu.add(child);
            }
        }
        LogUtil.e(TAG,"loadRcMenu cost:" + (System.currentTimeMillis()-now)+"ms");
    }

    public void loadClasses() {
        long now = System.currentTimeMillis();
        LogUtil.e(TAG,"loadRcMenu cost:" + (System.currentTimeMillis()-now)+"ms");
    }
}
