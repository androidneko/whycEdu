package com.androidcat.acnet.manager;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.androidcat.acnet.consts.InterfaceCodeConst;
import com.androidcat.acnet.consts.OptMsgConst;
import com.androidcat.acnet.entity.request.ChangePswRequest;
import com.androidcat.acnet.entity.request.FastLoginRequest;
import com.androidcat.acnet.entity.request.LoginRequest;
import com.androidcat.acnet.entity.request.RegisterRequest;
import com.androidcat.acnet.entity.request.ResetPasswordRequest;
import com.androidcat.acnet.entity.request.UserRequest;
import com.androidcat.acnet.entity.request.ValidateCodeRequest;
import com.androidcat.acnet.entity.response.BaseResponse;
import com.androidcat.acnet.entity.response.LoginResponse;
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
public class UserManager extends BaseManager {

    public UserManager(Context context, Handler handler){
        super(context,handler);
    }

    public void login(String userName,String pwd,RawResponseHandler rawResponseHandler){
        LoginRequest request = new LoginRequest();
        request.loginName = (userName);
        request.setPasswd(pwd);
        post(InterfaceCodeConst.TYPE_LOGIN, getPostJson(request), rawResponseHandler);
    }

    public void login(String userName,String pwd){
        LoginRequest request = new LoginRequest();
        request.loginName = (userName);
        request.setPasswd(pwd);
        post(InterfaceCodeConst.TYPE_LOGIN, getPostJson(request), new EntityResponseHandler<LoginResponse>() {
            @Override
            public void onStart(int code) {
                handler.sendEmptyMessage(OptMsgConst.MSG_LOGIN_START);
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                Message msg = new Message();
                msg.obj = error_msg;
                msg.what = OptMsgConst.MSG_LOGIN_FAIL;
                handler.sendMessage(msg);
            }

            @Override
            public void onSuccess(int statusCode, LoginResponse response) {
                Message msg = new Message();
                msg.obj = response;
                msg.what = OptMsgConst.MSG_LOGIN_SUCCESS;
                handler.sendMessage(msg);
            }
        });
    }

    public void getVerifyCode(String phoneNum){
        ValidateCodeRequest request = new ValidateCodeRequest();
        request.mobile = (phoneNum);
        post(InterfaceCodeConst.TYPE_GET_VERIFY_CODE, getPostJson(request), new EntityResponseHandler<StringContentResponse>() {
            @Override
            public void onStart(int code) {
                handler.sendEmptyMessage(OptMsgConst.MSG_GET_VERIFYCODE_START);
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                Message msg = new Message();
                msg.obj = error_msg;
                msg.what = OptMsgConst.MSG_GET_VERIFYCODE_FAIL;
                handler.sendMessage(msg);
            }

            @Override
            public void onSuccess(int statusCode, StringContentResponse response) {
                Message msg = new Message();
                msg.obj = response;
                msg.what = OptMsgConst.MSG_GET_VERIFYCODE_SUCCESS;
                handler.sendMessage(msg);
            }
        });
    }

}
