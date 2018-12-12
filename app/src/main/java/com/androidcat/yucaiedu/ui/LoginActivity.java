package com.androidcat.yucaiedu.ui;

import android.content.Intent;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;

import com.androidcat.acnet.consts.OptMsgConst;
import com.androidcat.acnet.entity.User;
import com.androidcat.acnet.entity.response.LoginResponse;
import com.androidcat.acnet.manager.UserManager;
import com.androidcat.utilities.Utils;
import com.androidcat.utilities.listener.OnSingleClickListener;
import com.androidcat.utilities.persistence.SPConsts;
import com.androidcat.utilities.persistence.SpUtil;
import com.androidcat.yucaiedu.AppConst;
import com.androidcat.yucaiedu.AppData;
import com.androidcat.yucaiedu.R;
import com.androidcat.yucaiedu.ui.activity.BaseActivity;
import com.androidcat.yucaiedu.ui.activity.HomeActivity;
import com.anroidcat.acwidgets.ClearEditText;

//import de.greenrobot.event.EventBus;

public class LoginActivity extends BaseActivity {
    private static final String TAG = "LoginActivity";
    private final static int MSG_LOGIN_CANCLE = 0x0004;

    private View mLogin = null;
    private ClearEditText usernameTxt;
    private ClearEditText pwdTxt;
    private UserManager loginManager;
    private String loginName;
    private String pwdtxtbefore;

    private OnSingleClickListener onClickListener = new OnSingleClickListener() {
        @Override
        public void onSingleClick(View v) {
            switch (v.getId()) {
                case R.id.vg_login:
                    //gotoHome();
                    String nameTxt = usernameTxt.getText().toString().trim();
                    pwdtxtbefore = pwdTxt.getText().toString().trim();
                    if (Utils.isNull(nameTxt)) {
                        showToast("请输入您的账号");
                        return;
                    }
                    //验证手机号是否合法
                    /*if (!checkPhoneNumber(nameTxt)) {
                        showToast("请输入合法的手机号");
                        return;
                    }*/
                    if (Utils.isNull(pwdtxtbefore)) {
                        showToast("请输入密码");
                        return;
                    }
                    if (pwdtxtbefore.length() < 6) {
                        showToast("密码不能少于6位");
                        return;
                    }
                    String password = pwdtxtbefore;
                    login(nameTxt, password);
                    break;
            }
        }
    };

    @Override
    protected int getResID() {
        return R.layout.activity_login;
    }

    @Override
    protected void intLayout() {
        mLogin =  findViewById(R.id.vg_login);
        usernameTxt = (ClearEditText) findViewById(R.id.usernameTxt);
        pwdTxt = (ClearEditText) findViewById(R.id.pwdTxt);
    }


    protected void onResume() {
        super.onResume();
        dismissLoadingDialog();
        loginName = user.loginName;
        if (!Utils.isNull(loginName)) {
            usernameTxt.setText(loginName);
        }
        pwdTxt.setText(SpUtil.getValue(SPConsts.CONV));
    }

    @Override
    protected void setListener() {
        loginManager = new UserManager(this, baseHandler);
        mLogin.setOnClickListener(onClickListener);
    }

    private void login(final String username, final String pwd) {
        loginManager.login(username, pwd);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (AppConst.IS_MAINACTIVITY_START) {
                gotoHome();
            }
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void handleEventMsg(Message msg) {
        super.handleEventMsg(msg);
        switch (msg.what) {
            case OptMsgConst.MSG_LOGIN_FAIL:
                dismissLoadingDialog();
                if (msg.obj == null || Utils.isNull((String) msg.obj)) {
                    showToast("登录失败");
                } else {
                    showToast((String) msg.obj);
                }
                break;
            case OptMsgConst.MSG_LOGIN_START:
                showLoadingDialog("正在登录...");
                break;
            case OptMsgConst.MSG_LOGIN_SUCCESS:
                dismissLoadingDialog();
                saveUser((LoginResponse) msg.obj);
                SpUtil.setValue(SPConsts.CONV,this.pwdtxtbefore);
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dismissLoadingDialog();
        cancelToast();
        //EventBus.getDefault().unregister(this);
    }

    private void saveUser(LoginResponse loginResponse){
        User user = new User();
        user.userId = loginResponse.content.userId;
        user.userName = loginResponse.content.userName;
        user.token = loginResponse.sessionId;
        user.avatar = loginResponse.content.avatar;
        user.deptId = loginResponse.content.deptId;
        user.loginName = loginResponse.content.loginName;
        user.phonenumber = loginResponse.content.phonenumber;
        AppData.getAppData().user = user;
        // TODO: 2017-8-21 add more properties here
        SpUtil.setObject(user);
    }

    private void gotoHome(){
        Intent intent = new Intent();
        intent.setClass(LoginActivity.this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);   //注意本行的FLAG设置
        startActivity(intent);
        finish();
    }
}

