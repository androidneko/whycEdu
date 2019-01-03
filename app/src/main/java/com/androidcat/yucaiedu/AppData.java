package com.androidcat.yucaiedu;

import com.androidcat.acnet.entity.Classroom;
import com.androidcat.acnet.entity.MenuItm;
import com.androidcat.acnet.entity.User;
import com.androidcat.acnet.entity.response.BuildingsResponse;
import com.androidcat.acnet.entity.response.GradeListResponse;
import com.androidcat.acnet.entity.response.MenuResponse;
import com.androidcat.utilities.LogUtil;

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

    //public static Map<Integer,MenuItm> rcMenuItmMap = new HashMap<>();
    //public static Map<Integer,MenuItm> saMenuItmMap = new HashMap<>();

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

    public static List<MenuItm> parentSaMenu = new ArrayList();
    public static List<ArrayList<MenuItm>> childSaMenu = new ArrayList<ArrayList<MenuItm>>();

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
        for (int k = 0;k < 4;k++){
            if (k == 0){
                MenuItm menuItm = new MenuItm();
                menuItm.dictLabel = "大事记";
                parentSaMenu.add(menuItm);
                ArrayList<MenuItm> child = new ArrayList();
                for (int i = 1;i <= 5;i++){
                    if (i == 1) {
                        MenuItm childItem = new MenuItm();
                        childItem.dictLabel = "学术交流";
                        childItem.parent = "大事记";
                        childItem.isChecked = true;
                        child.add(childItem);
                        //saMenuItmMap.put(R.id.recitingRb, childItem);
                    }
                    if (i == 2) {
                        MenuItm childItem = new MenuItm();
                        childItem.dictLabel = "课程开发";
                        childItem.parent = "大事记";
                        child.add(childItem);
                        //saMenuItmMap.put(R.id.readingRb, childItem);
                    }
                    if (i == 3) {
                        MenuItm childItem = new MenuItm();
                        childItem.dictLabel = "课堂教学";
                        childItem.parent = "大事记";
                        child.add(childItem);
                        //saMenuItmMap.put(R.id.meetingRb, childItem);
                    }
                    if (i == 4) {
                        MenuItm childItem = new MenuItm();
                        childItem.dictLabel = "教研活动";
                        childItem.parent = "大事记";
                        child.add(childItem);
                        //saMenuItmMap.put(R.id.exerciseRb, childItem);
                    }
                    if (i == 5) {
                        MenuItm childItem = new MenuItm();
                        childItem.dictLabel = "学生活动";
                        childItem.parent = "大事记";
                        child.add(childItem);
                        //saMenuItmMap.put(R.id.eyeExerciseRb, childItem);
                    }
                }
                childSaMenu.add(child);
            }
            if (k == 1){
                MenuItm menuItm = new MenuItm();
                menuItm.dictLabel = "安全管理";
                parentSaMenu.add(menuItm);
                ArrayList<MenuItm> child = new ArrayList();
                for (int i = 1;i <= 7;i++){
                    if (i == 1) {
                        MenuItm childItem = new MenuItm();
                        childItem.dictLabel = "教师执勤";
                        childItem.parent = "安全管理";
                        childItem.desc = "考评标准:1按时到岗 2佩戴袖章 3巡查提醒";
                        childItem.memo = "教师成员列表";

                        child.add(childItem);
                        //saMenuItmMap.put(R.id.dutyRb, childItem);
                    }
                    if (i == 2) {
                        MenuItm childItem = new MenuItm();
                        childItem.dictLabel = "护校队";
                        childItem.parent = "安全管理";
                        childItem.desc = "考评标准:1按时到岗 2佩戴袖章 3巡查提醒";
                        childItem.memo = "教师成员列表";
                        child.add(childItem);
                        //saMenuItmMap.put(R.id.protectRb, childItem);
                    }
                    if (i == 3) {
                        MenuItm childItem = new MenuItm();
                        childItem.dictLabel = "特长训练";
                        childItem.parent = "安全管理";
                        childItem.desc = "考评标准:1准时训练 2组织有序";
                        childItem.memo = "教师成员列表";
                        child.add(childItem);
                        //saMenuItmMap.put(R.id.specialTrainingRb, childItem);
                    }
                    if (i == 4) {
                        MenuItm childItem = new MenuItm();
                        childItem.dictLabel = "门禁安全";
                        childItem.parent = "安全管理";
                        childItem.desc = "考评标准:1电梯间随手关门 2学生离校出具班主任签条 3不通知家长到学校送学具等物品";
                        childItem.memo = "教师成员列表";
                        child.add(childItem);
                        //saMenuItmMap.put(R.id.safetyRb, childItem);
                    }
                    if (i == 5) {
                        MenuItm childItem = new MenuItm();
                        childItem.dictLabel = "意外伤害";
                        childItem.parent = "安全管理";
                        child.add(childItem);
                        //saMenuItmMap.put(R.id.accidentRb, childItem);
                    }
                    if (i == 6) {
                        MenuItm childItem = new MenuItm();
                        childItem.dictLabel = "放学清场";
                        childItem.parent = "安全管理";
                        childItem.desc = "";
                        childItem.memo = "班级列表";
                        child.add(childItem);
                        //saMenuItmMap.put(R.id.clearRb, childItem);
                    }
                    if (i == 7) {
                        MenuItm childItem = new MenuItm();
                        childItem.dictLabel = "离校巡查";
                        childItem.parent = "安全管理";
                        child.add(childItem);
                        //saMenuItmMap.put(R.id.leavingRb, childItem);
                    }
                }
                childSaMenu.add(child);
            }
            if (k == 2){
                MenuItm menuItm = new MenuItm();
                menuItm.dictLabel = "卫生保洁";
                parentSaMenu.add(menuItm);
                ArrayList<MenuItm> child = new ArrayList();
                for (int i = 1;i <= 2;i++){
                    if (i == 1) {
                        MenuItm childItem = new MenuItm();
                        childItem.dictLabel = "室外保洁";
                        childItem.parent = "卫生保洁";
                        child.add(childItem);
                        //saMenuItmMap.put(R.id.healthRb, childItem);
                    }
                    if (i == 2) {
                        MenuItm childItem = new MenuItm();
                        childItem.dictLabel = "文明办公";
                        childItem.parent = "卫生保洁";
                        child.add(childItem);
                        //saMenuItmMap.put(R.id.energyRb, childItem);
                    }
                }
                childSaMenu.add(child);
            }
            if (k == 3){
                MenuItm menuItm = new MenuItm();
                menuItm.dictLabel = "备注";
                parentSaMenu.add(menuItm);
                ArrayList<MenuItm> child = new ArrayList();
                for (int i = 1;i <= 1;i++){
                    if (i == 1) {
                        MenuItm childItem = new MenuItm();
                        childItem.dictLabel = "备注";
                        childItem.parent = "备注";
                        child.add(childItem);
                        //saMenuItmMap.put(R.id.firstRb, childItem);
                    }
                }
                childSaMenu.add(child);
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
                        //rcMenuItmMap.put(R.id.recitingRb, childItem);
                    }
                    if (i == 2) {
                        MenuItm childItem = new MenuItm();
                        childItem.dictLabel = "晨读";
                        childItem.parent = "自我管理";
                        child.add(childItem);
                        //rcMenuItmMap.put(R.id.readingRb, childItem);
                    }
                    if (i == 3) {
                        MenuItm childItem = new MenuItm();
                        childItem.dictLabel = "晨会";
                        childItem.parent = "自我管理";
                        child.add(childItem);
                        //rcMenuItmMap.put(R.id.meetingRb, childItem);
                    }
                    if (i == 4) {
                        MenuItm childItem = new MenuItm();
                        childItem.dictLabel = "广播操";
                        childItem.parent = "两操管理";
                        child.add(childItem);
                        //rcMenuItmMap.put(R.id.exerciseRb, childItem);
                    }
                    if (i == 5) {
                        MenuItm childItem = new MenuItm();
                        childItem.dictLabel = "眼保操";
                        childItem.parent = "自我管理";
                        child.add(childItem);
                        //rcMenuItmMap.put(R.id.eyeExerciseRb, childItem);
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
                        //rcMenuItmMap.put(R.id.restTimeRb, childItem);
                    }
                    if (i == 2) {
                        MenuItm childItem = new MenuItm();
                        childItem.dictLabel = "好人好事";
                        childItem.parent = "安全文明";
                        child.add(childItem);
                        //rcMenuItmMap.put(R.id.goodThingsRb, childItem);
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
                        //rcMenuItmMap.put(R.id.healthRb, childItem);
                    }
                    if (i == 2) {
                        MenuItm childItem = new MenuItm();
                        childItem.dictLabel = "班级节能";
                        childItem.parent = "卫生节能";
                        child.add(childItem);
                        //rcMenuItmMap.put(R.id.energyRb, childItem);
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
                        //rcMenuItmMap.put(R.id.firstRb, childItem);
                    }
                    if (i == 2) {
                        MenuItm childItem = new MenuItm();
                        childItem.dictLabel = "午间管理";
                        childItem.parent = "课堂管理";
                        child.add(childItem);
                        //rcMenuItmMap.put(R.id.noonRb, childItem);
                    }
                    if (i == 3) {
                        MenuItm childItem = new MenuItm();
                        childItem.dictLabel = "下午巡堂";
                        childItem.parent = "课堂管理";
                        child.add(childItem);
                        //rcMenuItmMap.put(R.id.sixthRb, childItem);
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
                        //rcMenuItmMap.put(R.id.tsQueueRb, childItem);
                    }
                    if (i == 2) {
                        MenuItm childItem = new MenuItm();
                        childItem.dictLabel = "放学队列";
                        childItem.parent = "队列管理";
                        child.add(childItem);
                        //rcMenuItmMap.put(R.id.afterSchoolRb, childItem);
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
