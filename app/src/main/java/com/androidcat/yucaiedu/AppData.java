package com.androidcat.yucaiedu;

import com.androidcat.acnet.entity.User;
import com.androidcat.acnet.entity.response.GradeListResponse;

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
}
