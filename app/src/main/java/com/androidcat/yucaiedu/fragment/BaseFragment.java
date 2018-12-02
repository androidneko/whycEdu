package com.androidcat.yucaiedu.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidcat.acnet.consts.OptMsgConst;
import com.androidcat.utilities.LogUtil;
import com.androidcat.yucaiedu.ui.LoginActivity;
import com.androidcat.yucaiedu.ui.activity.BaseActivity;

import java.lang.ref.WeakReference;

public abstract class BaseFragment extends Fragment {

    private final static String TAG = "BaseFragment";
    public FragmentHandler baseHandler;
    protected View mRootView = null;
    protected boolean hasFocus = false;
    protected boolean isVisible = false;

    protected abstract int getResID();

    protected abstract void intLayout();



    protected abstract void setListener();

    public void iOnResume() {
        isVisible = true;
        LogUtil.e(TAG,this.getClass().getSimpleName() + "--iOnResume");
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        this.hasFocus = hasFocus;
    }

    public void handleEventMsg(Message msg){
        //add process of common processing
        if (msg.what == OptMsgConst.TOKEN_ERROR){
            gotoLogin();
            showToast("您的登录状态已失效，请重新登录");
            return;
        }
        childHandleEventMsg(msg);
    }

    private void gotoLogin(){
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    protected void childHandleEventMsg(Message msg) {
        //do nothing ...
        //only for when child need to handle those messages processed by super class
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.e(TAG,this.getClass().getSimpleName() + "--onCreate");
        baseHandler = new FragmentHandler(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(getResID(), container, false);

        intLayout();
        setListener();
        initModule();

        return mRootView;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden){
            isVisible = false;
        }else {
            isVisible = true;
        }
        LogUtil.e(TAG,this.getClass().getSimpleName() + "--onHiddenChanged hidden:" + hidden);
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        LogUtil.e(TAG,this.getClass().getSimpleName() + "--onDestroyView");
        mRootView = null;
    }

    protected abstract void initModule();

    //////////////////////////////////////////////UI提示///////////////////////////////////////////////////
    public void showToast(String text) {
        ((BaseActivity)getActivity()).showToast(text);
    }


    protected void showProgressDialog(String msg) {
        ((BaseActivity)getActivity()).showLoadingDialog(msg);
    }

    protected void dismissLoadingDialog() {
        ((BaseActivity)getActivity()).dismissLoadingDialog();
    }

    protected void openActivity(Class<?> pClass) {
        openActivity(pClass, null);
    }

    protected void openActivityForResult(Class<?> pClass,int requestCode) {
        openActivityForResult(pClass, null, requestCode);
    }

    protected void openActivity(Intent intent) {
        String src = this.getClass().getSimpleName();
        String dst;
        if (intent.getComponent() != null){
            String dstClassName = intent.getComponent().getShortClassName();
            dst = dstClassName.substring(dstClassName.lastIndexOf('.')+1);
        }else {
            if (intent.getAction() != null){
                dst = intent.getAction();
            }else {
                dst = "unknown";
            }
        }
        startActivity(intent);
    }

    protected void openActivity(Class<?> pClass, Bundle pBundle) {
        Intent intent = new Intent(getActivity(), pClass);
        if (pBundle != null) {
            intent.putExtras(pBundle);
        }
        startActivity(intent);
    }

    protected void openActivityForResult(Class<?> pClass, Bundle pBundle,int requestCode) {
        Intent intent = new Intent(getActivity(), pClass);
        if (pBundle != null) {
            intent.putExtras(pBundle);
        }
        startActivityForResult(intent, requestCode);
    }

    protected void openActivityForResult(Intent intent,int requestCode) {
        String src = this.getClass().getSimpleName();
        String dst;
        if (intent.getComponent() != null){
            String dstClassName = intent.getComponent().getShortClassName();
            dst = dstClassName.substring(dstClassName.lastIndexOf('.')+1);
        }else {
            if (intent.getAction() != null){
                dst = intent.getAction();
            }else {
                dst = "unknown";
            }
        }
        startActivityForResult(intent, requestCode);
    }

    protected void openActivity(String pAction) {
        openActivity(pAction, null);
    }

    protected void openActivity(String pAction, Bundle pBundle) {
        Intent intent = new Intent(pAction);
        if (pBundle != null) {
            intent.putExtras(pBundle);
        }
        startActivity(intent);
    }

    //////////////////////////////////////////////handler定义///////////////////////////////////////////////////
    public static class FragmentHandler extends Handler{
        private final WeakReference<BaseFragment> mInstance;

        public FragmentHandler(BaseFragment instance) {
            mInstance = new WeakReference<BaseFragment>(instance);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            BaseFragment fragment = mInstance.get();
            if (null == fragment) {
                return;
            }
            fragment.handleEventMsg(msg);
        }
    }

}
