package com.androidcat.yucaiedu.ui.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.Window;
import android.widget.Toast;

import com.androidcat.acnet.entity.User;
import com.androidcat.utilities.LogUtil;
import com.androidcat.utilities.persistence.SpUtil;
import com.androidcat.yucaiedu.AppData;
import com.anroidcat.acwidgets.flippingdialog.FlippingLoadingDialog;

import java.lang.ref.WeakReference;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;

/**
 * Created by coolbear on 15/4/14.
 */
public abstract class BaseActivity extends FragmentActivity {

    private final static String TAG = "BaseActivity";
    protected User user;
    protected boolean isVisible = false;
    protected FlippingLoadingDialog mLoadingDialog;
    private Toast mToast;

    protected ActivityHandler baseHandler = new ActivityHandler(this);

    protected abstract int getResID();

    protected abstract void intLayout();

    protected abstract void setListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        LogUtil.e(TAG,this.getClass().getSimpleName()+"--onCreate");
        try {
            setContentView(getResID());
        } catch (OutOfMemoryError e) {
            System.gc();
            System.runFinalization();
            System.gc();
            setContentView(getResID());
        }
        mLoadingDialog = new FlippingLoadingDialog(this, "正在加载...");
        mLoadingDialog.setCancelable(false);
        user = (User) SpUtil.getObject(User.class);
        AppData.getAppData().user = user;
        intLayout();//模板方法模式
        intLayout(savedInstanceState);
        setListener();
        initModule();
    }

    protected void intLayout(Bundle savedInstanceState) {

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        LogUtil.e(TAG,this.getClass().getSimpleName() + "--onNewIntent");
        user = (User) SpUtil.getObject(User.class);
        AppData.getAppData().user = user;
    }

    protected void onResume() {
        super.onResume();
        isVisible = true;
        LogUtil.e(TAG,this.getClass().getSimpleName() + "--onResume");
        user = (User) SpUtil.getObject(User.class);
        AppData.getAppData().user = user;
    }

    protected void onPause() {
        super.onPause();
        isVisible = false;
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtil.e(TAG,this.getClass().getSimpleName() + "--onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtil.e(TAG,this.getClass().getSimpleName() + "--onDestroy");
        baseHandler.removeCallbacksAndMessages(null);
    }

    protected void handleEventMsg(Message msg) {
        switch (msg.what) {
            //case SdkMsgConst.GET_GUIDE_ADV_SUCCESS:
        }
        childHandleEventMsg(msg);
    }

    protected void childHandleEventMsg(Message msg) {
        //do nothing ...
        //only for when child need to handle those messages processed by super class
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
        Intent intent = new Intent(this, pClass);
        if (pBundle != null) {
            intent.putExtras(pBundle);
        }
        startActivity(intent);
    }

    protected void openActivityForResult(Class<?> pClass, Bundle pBundle,int requestCode) {
        Intent intent = new Intent(this, pClass);
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

    protected void initModule() {
        checkPermission();
    }

    protected void checkPermission() {
    }

    private void showDialog(Dialog dialog) {
        if (dialog == null || dialog.isShowing()) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (!isDestroyed() && !dialog.isShowing()) {
                dialog.show();
            }
        } else {
            if (!isFinishing() && !dialog.isShowing()) {
                dialog.show();
            }
        }
    }

    public void showLoadingDialog(String text) {
        mLoadingDialog.setText(text);
        showDialog(mLoadingDialog);
    }

    public void showLoadingDialog() {
        mLoadingDialog.setText("正在加载...");
        showDialog(mLoadingDialog);
    }

    public void showLoadingDialog(int text) {
        mLoadingDialog.setText(text);
        showDialog(mLoadingDialog);
    }

    public void dismissLoadingDialog() {
        dismissDialog(mLoadingDialog);
    }

    private void dismissDialog(Dialog dialog) {
        if (dialog == null || !dialog.isShowing()) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (!isDestroyed() && dialog.isShowing()) {
                dialog.dismiss();
            }
        } else {
            if (!isFinishing() && dialog.isShowing()) {
                dialog.dismiss();
            }
        }
    }

    public void showToast(String text) {
        if (mToast == null) {
            mToast = Toast.makeText(BaseActivity.this, text, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(text);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }

    public void showLongToast(String text) {
        if (mToast == null) {
            mToast = Toast.makeText(BaseActivity.this, text, Toast.LENGTH_LONG);
        } else {
            mToast.setText(text);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }

    public void cancelToast() {
        if (mToast != null) {
            mToast.cancel();
            mToast = null;
        }
    }

    public boolean checkPhoneNumber(String phone) {
        String telRegex = "[1][34578]\\d{9}";
        if (TextUtils.isEmpty(phone)) {
            return false;
        } else {
            return phone.matches(telRegex);
        }
    }

    public void canDismissDialog(Dialog dialog){
        if(dialog==null){
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (!isDestroyed() && dialog.isShowing()) {
                dialog.dismiss();
            }
        } else {
            if (!isFinishing() && dialog.isShowing()) {
                dialog.dismiss();
            }
        }
    }


    public void canShowDialog(Dialog dialog){
        if(dialog==null){
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (!isDestroyed() && !dialog.isShowing()) {
                dialog.show();
            }
        } else {
            if (!isFinishing() && !dialog.isShowing()) {
                dialog.show();
            }
        }
    }

    public static class ActivityHandler extends Handler {
        private final WeakReference<BaseActivity> mInstance;

        public ActivityHandler(BaseActivity instance) {
            mInstance = new WeakReference<BaseActivity>(instance);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            BaseActivity activity = mInstance.get();
            if (null == activity) {
                return;
            }
            activity.handleEventMsg(msg);
        }
    }

}