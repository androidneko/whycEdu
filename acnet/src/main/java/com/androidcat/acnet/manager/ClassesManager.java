package com.androidcat.acnet.manager;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.androidcat.acnet.consts.InterfaceCodeConst;
import com.androidcat.acnet.consts.OptMsgConst;
import com.androidcat.acnet.entity.request.BuildingsRequest;
import com.androidcat.acnet.entity.request.ChangePswRequest;
import com.androidcat.acnet.entity.request.ClassMarkListRequest;
import com.androidcat.acnet.entity.request.EventRequest;
import com.androidcat.acnet.entity.request.FastLoginRequest;
import com.androidcat.acnet.entity.request.GradeListRequest;
import com.androidcat.acnet.entity.request.LoginRequest;
import com.androidcat.acnet.entity.request.MarkItemRequest;
import com.androidcat.acnet.entity.request.MenuRcRequest;
import com.androidcat.acnet.entity.request.PostMarkRequest;
import com.androidcat.acnet.entity.request.QueryEventRequest;
import com.androidcat.acnet.entity.request.QueryTobeMarkedListRequest;
import com.androidcat.acnet.entity.request.RegisterRequest;
import com.androidcat.acnet.entity.request.ResetPasswordRequest;
import com.androidcat.acnet.entity.request.SaHistoryRequest;
import com.androidcat.acnet.entity.request.ScoreListRequest;
import com.androidcat.acnet.entity.request.UserRequest;
import com.androidcat.acnet.entity.request.ValidateCodeRequest;
import com.androidcat.acnet.entity.response.BaseResponse;
import com.androidcat.acnet.entity.response.BuildingsResponse;
import com.androidcat.acnet.entity.response.ClassMarkListResponse;
import com.androidcat.acnet.entity.response.GradeListResponse;
import com.androidcat.acnet.entity.response.LoginResponse;
import com.androidcat.acnet.entity.response.MarkClassResponse;
import com.androidcat.acnet.entity.response.MarkRoomResponse;
import com.androidcat.acnet.entity.response.MarkTeacherResponse;
import com.androidcat.acnet.entity.response.MenuResponse;
import com.androidcat.acnet.entity.response.QueryEventResponse;
import com.androidcat.acnet.entity.response.RegistResponse;
import com.androidcat.acnet.entity.response.SaHistoryResponse;
import com.androidcat.acnet.entity.response.ScoreListResponse;
import com.androidcat.acnet.entity.response.StringContentResponse;
import com.androidcat.acnet.entity.response.UserInfoResponse;
import com.androidcat.acnet.okhttp.callback.EntityResponseHandler;
import com.androidcat.acnet.okhttp.callback.RawResponseHandler;
import com.androidcat.utilities.persistence.SpUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Project: FuelMore
 * Author: androidcat
 * Email:androidcat@126.com
 * Created at: 2017-7-21 17:08:36
 * add function description here...
 */
public class ClassesManager extends BaseManager {

    public ClassesManager(Context context, Handler handler){
        super(context,handler);
    }

    public void login(String userName,String pwd,RawResponseHandler rawResponseHandler){
        LoginRequest request = new LoginRequest();
        request.loginName = (userName);
        request.setPasswd(pwd);
        post(InterfaceCodeConst.TYPE_LOGIN, getPostJson(request), rawResponseHandler);
    }

    public void getGradeList(String loginName,String sessionId){
        GradeListRequest request = new GradeListRequest();
        request.loginName = loginName;
        request.sessionId = sessionId;
        post(InterfaceCodeConst.TYPE_GET_GRADE_LIST, getPostJson(request), new EntityResponseHandler<GradeListResponse>() {
            @Override
            public void onStart(int code) {
                handler.sendEmptyMessage(OptMsgConst.GRADE_LIST_START);
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                Message msg = new Message();
                msg.obj = error_msg;
                msg.what = OptMsgConst.GRADE_LIST_FAIL;
                handler.sendMessage(msg);
            }

            @Override
            public void onSuccess(int statusCode, GradeListResponse response) {
                Message msg = new Message();
                msg.obj = response;
                msg.what = OptMsgConst.GRADE_LIST_SUCCESS;
                handler.sendMessage(msg);
            }
        });
    }

    public void getMenuRc(String loginName,String sessionId){
        MenuRcRequest request = new MenuRcRequest();
        request.loginName = loginName;
        request.sessionId = sessionId;
        post(InterfaceCodeConst.TYPE_DICT, getPostJson(request), new EntityResponseHandler<MenuResponse>() {
            @Override
            public void onStart(int code) {
                handler.sendEmptyMessage(OptMsgConst.GET_DICT_START);
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                Message msg = new Message();
                msg.obj = error_msg;
                msg.what = OptMsgConst.GET_DICT_FAIL;
                handler.sendMessage(msg);
            }

            @Override
            public void onSuccess(int statusCode, MenuResponse response) {
                Message msg = new Message();
                msg.obj = response;
                msg.what = OptMsgConst.GET_DICT_SUCCESS;
                handler.sendMessage(msg);
            }
        });
    }

    public void getBuildings(String loginName,String sessionId,String dateStr,String project){
        BuildingsRequest request = new BuildingsRequest();
        request.loginName = loginName;
        request.sessionId = sessionId;
        request.dateStr = dateStr;
        request.project = project;
        post(InterfaceCodeConst.TYPE_BUILDINGS, getPostJson(request), new EntityResponseHandler<BuildingsResponse>() {
            @Override
            public void onStart(int code) {
                handler.sendEmptyMessage(OptMsgConst.MSG_BUILDINGS_START);
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                Message msg = new Message();
                msg.obj = error_msg;
                msg.what = OptMsgConst.MSG_BUILDINGS_FAIL;
                handler.sendMessage(msg);
            }

            @Override
            public void onSuccess(int statusCode, BuildingsResponse response) {
                Message msg = new Message();
                msg.obj = response;
                msg.what = OptMsgConst.MSG_BUILDINGS_SUCCESS;
                handler.sendMessage(msg);
            }
        });
    }

    public void postMark(String loginName,String sessionId,String dateStr,String deptId,String menu,String mark){
        PostMarkRequest request = new PostMarkRequest();
        request.loginName = loginName;
        request.sessionId = sessionId;
        request.dateStr = dateStr;
        request.timetable = menu;
        request.deptId = deptId;
        request.grade = mark;

        post(InterfaceCodeConst.TYPE_POST_MARK, getPostJson(request), new EntityResponseHandler<BaseResponse>() {
            @Override
            public void onStart(int code) {
                handler.sendEmptyMessage(OptMsgConst.POST_MARK_START);
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                Message msg = new Message();
                msg.obj = error_msg;
                msg.what = OptMsgConst.POST_MARK_FAIL;
                handler.sendMessage(msg);
            }

            @Override
            public void onSuccess(int statusCode, BaseResponse response) {
                Message msg = new Message();
                msg.obj = response;
                msg.what = OptMsgConst.POST_MARK_SUCCESS;
                handler.sendMessage(msg);
            }
        });
    }

    public void postEvent(String loginName,String sessionId,String memId,String date,String type,String msg){
        EventRequest request = new EventRequest();
        request.loginName = loginName;
        request.sessionId = sessionId;
        request.dateStr = date;
        if (!TextUtils.isEmpty(memId)){
            request.memId = memId;
        }
        request.memContent = msg;
        request.typeCode = type;

        post(InterfaceCodeConst.TYPE_POST_EVENT, getPostJson(request), new EntityResponseHandler<BaseResponse>() {
            @Override
            public void onStart(int code) {
                handler.sendEmptyMessage(OptMsgConst.POST_EVENT_START);
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                Message msg = new Message();
                msg.obj = error_msg;
                msg.what = OptMsgConst.POST_EVENT_FAIL;
                handler.sendMessage(msg);
            }

            @Override
            public void onSuccess(int statusCode, BaseResponse response) {
                Message msg = new Message();
                msg.obj = response;
                msg.what = OptMsgConst.POST_EVENT_SUCCESS;
                handler.sendMessage(msg);
            }
        });
    }

    public void classMarkList(String loginName,String token,int classGradeId,String timetable,String dateStr){
        ClassMarkListRequest request = new ClassMarkListRequest();
        request.loginName = loginName;
        request.sessionId = token;
        request.classGradeId = classGradeId+"";
        request.timetable = timetable;
        request.dateStr = dateStr;
        post(InterfaceCodeConst.TYPE_MARK_RECORD, getPostJson(request), new EntityResponseHandler<ClassMarkListResponse>() {
            @Override
            public void onStart(int code) {
                handler.sendEmptyMessage(OptMsgConst.MSG_MARK_RECORD_START);
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                Message msg = new Message();
                msg.obj = error_msg;
                msg.what = OptMsgConst.MSG_MARK_RECORD_FAIL;
                handler.sendMessage(msg);
            }

            @Override
            public void onSuccess(int statusCode, ClassMarkListResponse response) {
                Message msg = new Message();
                msg.obj = response;
                msg.what = OptMsgConst.MSG_MARK_RECORD_SUCCESS;
                handler.sendMessage(msg);
            }
        });
    }

    public void scoreList(String loginName,String token,int classGradeId,int type){
        ScoreListRequest request = new ScoreListRequest();
        request.loginName = loginName;
        request.sessionId = token;
        request.classGradeId = classGradeId+"";
        request.type = type+"";
        post(InterfaceCodeConst.TYPE_GET_CLASS_SCORES, getPostJson(request), new EntityResponseHandler<ScoreListResponse>() {
            @Override
            public void onStart(int code) {
                handler.sendEmptyMessage(OptMsgConst.GET_CLASS_SCORES_START);
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                Message msg = new Message();
                msg.obj = error_msg;
                msg.what = OptMsgConst.GET_CLASS_SCORES_FAIL;
                handler.sendMessage(msg);
            }

            @Override
            public void onSuccess(int statusCode, ScoreListResponse response) {
                Message msg = new Message();
                msg.obj = response;
                msg.what = OptMsgConst.GET_CLASS_SCORES_SUCCESS;
                handler.sendMessage(msg);
            }
        });
    }

    public void saTeacherList(String loginName,String token,String project){
        QueryTobeMarkedListRequest request = new QueryTobeMarkedListRequest();
        request.loginName = loginName;
        request.sessionId = token;
        request.project = project;
        request.type = "1";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date());
        request.dateStr = date;
        post(InterfaceCodeConst.TYPE_GET_SA_LIST, getPostJson(request), new EntityResponseHandler<MarkTeacherResponse>() {
            @Override
            public void onStart(int code) {
                handler.sendEmptyMessage(OptMsgConst.GET_SA_LIST_START);
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                Message msg = new Message();
                msg.obj = error_msg;
                msg.what = OptMsgConst.GET_SA_LIST_FAIL;
                handler.sendMessage(msg);
            }

            @Override
            public void onSuccess(int statusCode, MarkTeacherResponse response) {
                Message msg = new Message();
                msg.obj = response;
                msg.arg1 = 1;
                msg.what = OptMsgConst.GET_SA_LIST_SUCCESS;
                handler.sendMessage(msg);
            }
        });
    }

    public void saClassList(String loginName,String token,String project){
        QueryTobeMarkedListRequest request = new QueryTobeMarkedListRequest();
        request.loginName = loginName;
        request.sessionId = token;
        request.project = project;
        request.type = "3";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date());
        request.dateStr = date;
        post(InterfaceCodeConst.TYPE_GET_SA_LIST, getPostJson(request), new EntityResponseHandler<MarkClassResponse>() {
            @Override
            public void onStart(int code) {
                handler.sendEmptyMessage(OptMsgConst.GET_SA_LIST_START);
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                Message msg = new Message();
                msg.obj = error_msg;
                msg.what = OptMsgConst.GET_SA_LIST_FAIL;
                handler.sendMessage(msg);
            }

            @Override
            public void onSuccess(int statusCode, MarkClassResponse response) {
                Message msg = new Message();
                msg.obj = response;
                msg.arg1 = 3;
                msg.what = OptMsgConst.GET_SA_LIST_SUCCESS;
                handler.sendMessage(msg);
            }
        });
    }

    public void saRoomList(String loginName,String token,String project){
        QueryTobeMarkedListRequest request = new QueryTobeMarkedListRequest();
        request.loginName = loginName;
        request.sessionId = token;
        request.project = project;
        request.type = "2";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date());
        request.dateStr = date;
        post(InterfaceCodeConst.TYPE_GET_SA_LIST, getPostJson(request), new EntityResponseHandler<MarkRoomResponse>() {
            @Override
            public void onStart(int code) {
                handler.sendEmptyMessage(OptMsgConst.GET_SA_LIST_START);
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                Message msg = new Message();
                msg.obj = error_msg;
                msg.what = OptMsgConst.GET_SA_LIST_FAIL;
                handler.sendMessage(msg);
            }

            @Override
            public void onSuccess(int statusCode, MarkRoomResponse response) {
                Message msg = new Message();
                msg.obj = response;
                msg.arg1 = 2;
                msg.what = OptMsgConst.GET_SA_LIST_SUCCESS;
                handler.sendMessage(msg);
            }
        });
    }

    public void saMark(String loginName,String token,String project,String grade,String type,String id,String commName){
        MarkItemRequest request = new MarkItemRequest();
        request.loginName = loginName;
        request.sessionId = token;
        request.project = project;
        request.type = type;
        request.grade = grade;
        request.teacherId = id;
        request.commName = commName;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date());
        request.dateStr = date;
        post(InterfaceCodeConst.TYPE_SA_MARK, getPostJson(request), new EntityResponseHandler<BaseResponse>() {
            @Override
            public void onStart(int code) {
                handler.sendEmptyMessage(OptMsgConst.SA_MARK_START);
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                Message msg = new Message();
                msg.obj = error_msg;
                msg.what = OptMsgConst.SA_MARK_FAIL;
                handler.sendMessage(msg);
            }

            @Override
            public void onSuccess(int statusCode, BaseResponse response) {
                Message msg = new Message();
                msg.obj = response;
                msg.what = OptMsgConst.SA_MARK_SUCCESS;
                handler.sendMessage(msg);
            }
        });
    }

    public void saHistory(String loginName,String token,String date){
        SaHistoryRequest request = new SaHistoryRequest();
        request.loginName = loginName;
        request.sessionId = token;
        request.dateStr = date;
        post(InterfaceCodeConst.TYPE_SA_HISTORY, getPostJson(request), new EntityResponseHandler<SaHistoryResponse>() {
            @Override
            public void onStart(int code) {
                handler.sendEmptyMessage(OptMsgConst.MSG_SA_HISTORY_START);
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                Message msg = new Message();
                msg.obj = error_msg;
                msg.what = OptMsgConst.MSG_SA_HISTORY_FAIL;
                handler.sendMessage(msg);
            }

            @Override
            public void onSuccess(int statusCode, SaHistoryResponse response) {
                Message msg = new Message();
                msg.obj = response;
                msg.what = OptMsgConst.MSG_SA_HISTORY_SUCCESS;
                handler.sendMessage(msg);
            }
        });
    }

    public void queryEvent(String loginName,String token,String date,String typeCode){
        QueryEventRequest request = new QueryEventRequest();
        request.loginName = loginName;
        request.sessionId = token;
        request.dateStr = date;
        request.typeCode = typeCode;
        post(InterfaceCodeConst.QUERY_EVENT, getPostJson(request), new EntityResponseHandler<QueryEventResponse>() {
            @Override
            public void onStart(int code) {
                handler.sendEmptyMessage(OptMsgConst.QUERY_EVENT_START);
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                Message msg = new Message();
                msg.obj = error_msg;
                msg.what = OptMsgConst.QUERY_EVENT_FAIL;
                handler.sendMessage(msg);
            }

            @Override
            public void onSuccess(int statusCode, QueryEventResponse response) {
                Message msg = new Message();
                msg.obj = response;
                msg.what = OptMsgConst.QUERY_EVENT_SUCCESS;
                handler.sendMessage(msg);
            }
        });
    }
}
