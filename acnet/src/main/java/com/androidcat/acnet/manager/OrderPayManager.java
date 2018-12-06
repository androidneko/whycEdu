package com.androidcat.acnet.manager;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.androidcat.acnet.consts.InterfaceCodeConst;
import com.androidcat.acnet.consts.OptMsgConst;
import com.androidcat.acnet.entity.request.AddRechargeRequest;
import com.androidcat.acnet.entity.request.GatherRequest;
import com.androidcat.acnet.entity.request.QrcodeRequest;
import com.androidcat.acnet.entity.response.StringContentResponse;
import com.androidcat.acnet.okhttp.callback.EntityResponseHandler;
import com.androidcat.acnet.okhttp.callback.RawResponseHandler;

/**
 * Project: FuelMore
 * Author: androidcat
 * Email:androidcat@126.com
 * Created at: 2017-7-21 17:08:36
 * add function description here...
 */
public class OrderPayManager extends BaseManager {

    public OrderPayManager(Context context, Handler handler){
        super(context, handler);
    }

    public void requestQrcode(String userName,String userId,String companyId,String authority,String ciphertext,RawResponseHandler rawResponseHandler){
        QrcodeRequest request = new QrcodeRequest();
        request.userName = (userName);
        request.userId = (userId);
        request.authority = (authority);
        request.companyId = (companyId);
        request.ciphertext = (ciphertext);
        post(InterfaceCodeConst.TYPE_GET_QR_CODE, getPostJson(request), rawResponseHandler);
    }

    public void requestQrcode(String userName,String userId,String companyId,String companyName,String authority,String ciphertext){
        QrcodeRequest request = new QrcodeRequest();
        request.userName = (userName);
        request.userId = (userId);
        request.authority = (authority);
        request.companyId = (companyId);
        request.companyName = companyName;
        request.ciphertext = (ciphertext);
        post(InterfaceCodeConst.TYPE_GET_QR_CODE, getPostJson(request), new EntityResponseHandler<StringContentResponse>() {
            @Override
            public void onStart(int code) {
                handler.sendEmptyMessage(OptMsgConst.MSG_GET_QR_CODE_START);
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                Message msg = new Message();
                msg.obj = error_msg;
                msg.what = OptMsgConst.MSG_GET_QR_CODE_FAIL;
                handler.sendMessage(msg);
            }

            @Override
            public void onSuccess(int statusCode, StringContentResponse response) {
                Message msg = new Message();
                msg.obj = response;
                msg.what = OptMsgConst.MSG_GET_QR_CODE_SUCCESS;
                handler.sendMessage(msg);
            }
        });
    }

    public void addRecharge(String userName,String userId,String ciphertext,String companyId,String amount,String companyName){
        AddRechargeRequest request = new AddRechargeRequest();
        request.userName = (userName);
        request.userId = (userId);
        request.ciphertext = ciphertext;
        request.amount = (amount);
        request.companyId = (companyId);
        request.company_name = (companyName);
        post(InterfaceCodeConst.TYPE_SA_MARK, getPostJson(request), new EntityResponseHandler<StringContentResponse>() {
            @Override
            public void onStart(int code) {
                handler.sendEmptyMessage(OptMsgConst.MSG_ADD_RECHARGE_START);
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                Message msg = new Message();
                msg.obj = error_msg;
                msg.what = OptMsgConst.MSG_ADD_RECHARGE_FAIL;
                handler.sendMessage(msg);
            }

            @Override
            public void onSuccess(int statusCode, StringContentResponse response) {
                Message msg = new Message();
                msg.obj = response;
                msg.what = OptMsgConst.MSG_ADD_RECHARGE_SUCCESS;
                handler.sendMessage(msg);
            }
        });
    }

    public void gather(String userName,String userId,String authority,String ciphertext,String companyId,String companyName,String pointId,String amount, String qrcode){
        GatherRequest request = new GatherRequest();
        request.businessUserName = (userName);
        request.businessUserId = (userId);
        request.authority = authority;
        request.ciphertext = ciphertext;
        request.companyId = companyId;
        request.companyName = companyName;
        request.pointId = pointId;
        request.amountMoney = amount;
        request.cipherqrcode = qrcode;
        post(InterfaceCodeConst.TYPE_GATHER, getPostJson(request), new EntityResponseHandler<StringContentResponse>() {
            @Override
            public void onStart(int code) {
                Message msg = new Message();
                msg.what = OptMsgConst.MSG_GATHER_START;
                handler.sendMessage(msg);
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                Message msg = new Message();
                msg.obj = error_msg;
                msg.what = OptMsgConst.MSG_GATHER_FAIL;
                handler.sendMessage(msg);
            }

            @Override
            public void onSuccess(int statusCode, StringContentResponse response) {
                Message msg = new Message();
                msg.obj = response;
                msg.what = OptMsgConst.MSG_GATHER_SUCCESS;
                handler.sendMessage(msg);
            }
        });
    }
}
