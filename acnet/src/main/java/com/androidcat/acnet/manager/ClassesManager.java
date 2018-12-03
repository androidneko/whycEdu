package com.androidcat.acnet.manager;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.androidcat.acnet.consts.InterfaceCodeConst;
import com.androidcat.acnet.consts.OptMsgConst;
import com.androidcat.acnet.entity.request.BuildingsRequest;
import com.androidcat.acnet.entity.request.ChangePswRequest;
import com.androidcat.acnet.entity.request.EventRequest;
import com.androidcat.acnet.entity.request.FastLoginRequest;
import com.androidcat.acnet.entity.request.GradeListRequest;
import com.androidcat.acnet.entity.request.LoginRequest;
import com.androidcat.acnet.entity.request.MenuRcRequest;
import com.androidcat.acnet.entity.request.PostMarkRequest;
import com.androidcat.acnet.entity.request.RegisterRequest;
import com.androidcat.acnet.entity.request.ResetPasswordRequest;
import com.androidcat.acnet.entity.request.UserRequest;
import com.androidcat.acnet.entity.request.ValidateCodeRequest;
import com.androidcat.acnet.entity.response.BaseResponse;
import com.androidcat.acnet.entity.response.BuildingsResponse;
import com.androidcat.acnet.entity.response.GradeListResponse;
import com.androidcat.acnet.entity.response.LoginResponse;
import com.androidcat.acnet.entity.response.MenuResponse;
import com.androidcat.acnet.entity.response.RegistResponse;
import com.androidcat.acnet.entity.response.StringContentResponse;
import com.androidcat.acnet.entity.response.UserInfoResponse;
import com.androidcat.acnet.okhttp.callback.EntityResponseHandler;
import com.androidcat.acnet.okhttp.callback.RawResponseHandler;

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

    public void getBuildings(String loginName,String sessionId){
        BuildingsRequest request = new BuildingsRequest();
        request.loginName = loginName;
        request.sessionId = sessionId;
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

    public void postEvent(String loginName,String sessionId,String menuId,String date,String type,String msg){
        EventRequest request = new EventRequest();
        request.loginName = loginName;
        request.sessionId = sessionId;
        request.dateStr = date;
        //request.memId = menuId;
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

    public void getUserInfo(String userId,String companyId,String ciphertext){
        UserRequest request = new UserRequest();
        request.userId = (userId);
        request.companyId = (companyId);
        request.ciphertext = (ciphertext);
        post(InterfaceCodeConst.TYPE_GET_USERINFO, getPostJson(request), new EntityResponseHandler<UserInfoResponse>() {
            @Override
            public void onStart(int code) {
                handler.sendEmptyMessage(OptMsgConst.MSG_GET_USERINFO_START);
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                Message msg = new Message();
                msg.obj = error_msg;
                msg.what = OptMsgConst.MSG_GET_USERINFO_FAIL;
                handler.sendMessage(msg);
            }

            @Override
            public void onSuccess(int statusCode, UserInfoResponse response) {
                Message msg = new Message();
                msg.obj = response;
                msg.what = OptMsgConst.MSG_GET_USERINFO_SUCCESS;
                handler.sendMessage(msg);
            }
        });
    }

}
