package com.androidcat.acnet.consts;

/**
 * Created by Administrator on 2017/8/4.
 */

public class InterfaceUrl {
    //public static final String BASE_URL = "http://120.25.249.105:8081";
    public static final String BASE_URL = "http://220.249.102.138:8010";

    public static final String URL_CONTROLLER = "/api/v1/";

    public static final String MARK_RECORD_URL = BASE_URL + "/app/getStatistics";

    public static final String POST_MARK_URL = BASE_URL + "/app/saveClassesScore";

    public static final String LOGIN_URL = BASE_URL + "/app/userLogin";
    public static final String GET_GRADE_LIST_URL = BASE_URL + "/app/getAllClassesRoot";
    public static final String DICT_URL = BASE_URL + "/app/getDictByType";
    public static final String BUILDINGS_URL = BASE_URL + "/app/getAllBuilding";
    public static final String EVENT_MSG_URL = BASE_URL + "/app/saveMemorabilia";
    public static final String CLASS_SCORES_URL = BASE_URL + "/app/getScoreStatistics";
    public static final String SA_LIST_URL = BASE_URL + "/app/searchTeacherScore";
    public static final String SA_MARK_URL = BASE_URL + "/app/saveTeacherScore";
    public static final String SA_HISTORY_URL = BASE_URL + "/app/searchSchoolLog";

    public static final String USERINFO_URL = BASE_URL + "/api/v1/user/findByUserId";
    public static final String GATHER_URL = BASE_URL + "/api/v1/saveOrderInfo";


    public static String getUrl(int code){
        switch (code){
            case InterfaceCodeConst.TYPE_LOGIN:
                return LOGIN_URL;
            case InterfaceCodeConst.TYPE_GET_GRADE_LIST:
                return GET_GRADE_LIST_URL;
            case InterfaceCodeConst.TYPE_DICT:
                return DICT_URL;
            case InterfaceCodeConst.TYPE_POST_EVENT:
                return EVENT_MSG_URL;
            case InterfaceCodeConst.TYPE_BUILDINGS:
                return BUILDINGS_URL;
            case InterfaceCodeConst.TYPE_MARK_RECORD:
                return MARK_RECORD_URL;
            case InterfaceCodeConst.TYPE_GET_CLASS_SCORES:
                return CLASS_SCORES_URL;
            case InterfaceCodeConst.TYPE_POST_MARK:
                return POST_MARK_URL;
            case InterfaceCodeConst.TYPE_GET_SA_LIST:
                return SA_LIST_URL;
            case InterfaceCodeConst.TYPE_SA_MARK:
                return SA_MARK_URL;
            case InterfaceCodeConst.TYPE_SA_HISTORY:
                return SA_HISTORY_URL;
            case InterfaceCodeConst.TYPE_GATHER:
                return GATHER_URL;
            case InterfaceCodeConst.TYPE_GET_USERINFO:
                return USERINFO_URL;
        }
        return "";
    }

}
