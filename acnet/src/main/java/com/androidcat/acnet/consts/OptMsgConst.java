package com.androidcat.acnet.consts;

/**
 * Created by Administrator on 2016/2/15.
 */
public class OptMsgConst {
    //全局公共业务常量
    public final static int SHOW_LOADING = 0x0001; //打开加载框
    public final static int DISMISS_LOADING = 0x0002; //关闭加载框

    public static final int SHOW_PROGRESS = 0x0100;
    public static final int DISMISS_PROGRESS = 0x0101;

    public final static int TOKEN_ERROR = 0x1002; //登录超时
    public final static int GOTO_LOGIN = 0x1102; //跳转到登陆
    public final static int MSG_DISCONNECT = 0x0477;//无网络
    public final static int MSG_CONN_TIMEOUT = 0x0487;//无网络

    //获取广告
    public static final int MSG_CIRCLE = 0x1910;
    public final static int GET_DICT_START = 0x1540;
    public final static int GET_DICT_SUCCESS = 0x1541;
    public final static int GET_DICT_FAIL = 0x1542;

    //获取验证码
    public static final int MSG_GET_VERIFYCODE_SUCCESS = 0x1010;
    public static final int MSG_GET_VERIFYCODE_FAIL = 0x1011;
    public static final int MSG_GET_VERIFYCODE_START = 0x1012;

    //打分记录
    public static final int MSG_MARK_RECORD_SUCCESS = 0x1020;
    public static final int MSG_MARK_RECORD_FAIL = 0x1021;
    public static final int MSG_MARK_RECORD_START = 0x1022;

    //班级得分列表
    public static final int GET_CLASS_SCORES_SUCCESS = 0x0013;
    public static final int GET_CLASS_SCORES_FAIL = 0x0014;
    public static final int GET_CLASS_SCORES_START = 0x0015;

    //验证短信验证码
    public static final int GET_SA_LIST_SUCCESS = 0x1020;
    public static final int GET_SA_LIST_FAIL = 0x1021;
    public static final int GET_SA_LIST_START = 0x1022;

    //注册
    public static final int POST_MARK_SUCCESS = 0x0013;
    public static final int POST_MARK_FAIL = 0x0014;
    public static final int POST_MARK_START = 0x0015;

    public static final int RECHARGE_FAILED = 0x3012;

    //登录
    public static final int MSG_LOGIN_START = 0x1120;
    public static final int MSG_LOGIN_SUCCESS = 0x1121;
    public static final int MSG_LOGIN_FAIL = 0x1122;
    public static final int MSG_LOGIN_QUERY = 0x1123;
    public static final int MSG_LOGIN_CANCEL = 0x1124;

    //获取班级列表
    public static final int GRADE_LIST_START = 0x1160;
    public static final int GRADE_LIST_SUCCESS = 0x1161;
    public static final int GRADE_LIST_FAIL = 0x1162;
    public static final int GRADE_LIST_HAVENODATA = 0x1163;

    //获取建筑物
    public static final int MSG_BUILDINGS_SUCCESS = 0x1260;
    public static final int MSG_BUILDINGS_FAIL = 0x1261;
    public static final int MSG_BUILDINGS_START = 0x1262;

    //提交事件
    public static final int POST_EVENT_SUCCESS = 0x1270;
    public static final int POST_EVENT_FAIL = 0x1271;
    public static final int POST_EVENT_START = 0x1272;

    //获取用户信息
    public final static int MSG_SA_HISTORY_START = 0x1001;
    public final static int MSG_SA_HISTORY_FAIL = 0x1003;
    public final static int MSG_SA_HISTORY_SUCCESS = 0x1004;

    //收款
    public static final int MSG_GATHER_SUCCESS = 0x1020;
    public static final int MSG_GATHER_FAIL = 0x1021;
    public static final int MSG_GATHER_START = 0x1022;

    //获取站点列表
    public static final int SA_MARK_SUCCESS = 0x1023;
    public static final int SA_MARK_FAIL = 0x1024;
    public static final int SA_MARK_START = 0x1025;

    //获取二维码
    public static final int MSG_GET_QR_CODE_SUCCESS = 0x1026;
    public static final int MSG_GET_QR_CODE_FAIL = 0x1027;
    public static final int MSG_GET_QR_CODE_START = 0x1028;

    //充值
    public static final int MSG_ADD_RECHARGE_SUCCESS = 0x1029;
    public static final int MSG_ADD_RECHARGE_FAIL = 0x1030;
    public static final int MSG_ADD_RECHARGE_START = 0x1031;

    //新闻列表
    public static final int QUERY_EVENT_SUCCESS = 0x1032;
    public static final int QUERY_EVENT_FAIL = 0x1033;
    public static final int QUERY_EVENT_START = 0x1034;

    //订单列表
    public static final int MSG_GET_ORDER_LIST_SUCCESS = 0x1035;
    public static final int MSG_GET_ORDER_LIST_FAIL = 0x1036;
    public static final int MSG_GET_ORDER_LIST_START = 0x1037;

}

