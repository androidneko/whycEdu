package com.androidcat.yucaiedu.fragment;

import android.content.Intent;
import android.os.Message;
import android.view.View;

import com.androidcat.utilities.listener.OnSingleClickListener;
import com.androidcat.yucaiedu.R;
import com.androidcat.yucaiedu.ui.BaseActivity;
import com.androidcat.yucaiedu.ui.LoginActivity;

@ActivityFragmentInject(
        contentViewId = R.layout.fragment_settings,
        hasNavigationView = false)
public class SettingsFragment extends BaseFragment {

    View checkUpdate;
    View exit;

    private OnSingleClickListener onSingleClickListener = new OnSingleClickListener() {
        @Override
        public void onSingleClick(View view) {
            if (view == checkUpdate){
                ((BaseActivity)getActivity()).showLoadingDialog("正在检查...");
                mHandler.sendEmptyMessageDelayed(1113,2000);
            }
            if (view == exit){
                gotoLogin();
            }
        }
    };

    @Override
    protected void toHandleMessage(Message msg) {
        switch (msg.what){
            case 1113:
                ((BaseActivity)getActivity()).dismissLoadingDialog();
                ((BaseActivity)getActivity()).showToast("当前已是最新版本");
                break;
            default:
                break;
        }
    }

    @Override
    protected void findViewAfterViewCreate() {
        checkUpdate = mRootView.findViewById(R.id.checkUpdate);
        exit = mRootView.findViewById(R.id.exit);
    }

    @Override
    protected void initDataAfterFindView() {
        checkUpdate.setOnClickListener(onSingleClickListener);
        exit.setOnClickListener(onSingleClickListener);
    }

    @Override
    protected void initDataBeforeViewCreate() {

    }

    private void gotoLogin(){
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
}
