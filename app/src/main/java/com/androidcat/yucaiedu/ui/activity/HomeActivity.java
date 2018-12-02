package com.androidcat.yucaiedu.ui.activity;

import android.content.Intent;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;

import com.androidcat.utilities.LogUtil;
import com.androidcat.yucaiedu.R;
import com.androidcat.yucaiedu.adapter.HomeTabAdapter;
import com.androidcat.yucaiedu.fragment.AnalyzeFragment;
import com.androidcat.yucaiedu.fragment.BaseFragment;
import com.androidcat.yucaiedu.fragment.ClassesScoresFragment;
import com.androidcat.yucaiedu.fragment.RegularCheckFragment;
import com.androidcat.yucaiedu.fragment.SchoolAffairsFragment;
import com.androidcat.yucaiedu.fragment.SettingsFragment;

import q.rorbin.verticaltablayout.VerticalTabLayout;
import q.rorbin.verticaltablayout.widget.TabView;

/**
 * Created by Administrator on 2016/4/12.
 */
public class HomeActivity extends BaseActivity {

    private static final String TAG = "SLMMainActivity_Logger";
    private long firstTime;// 记录点击返回时第一次的时间毫秒值
    private BaseFragment m_fragmentCurr = null;
    private BaseFragment currentFrag_0;
    private BaseFragment currentFrag_1;
    private BaseFragment currentFrag_2;
    private BaseFragment currentFrag_3;
    private BaseFragment currentFrag_4;
    private VerticalTabLayout tablayout;
    private HomeTabAdapter tabAdapter = new HomeTabAdapter();

    private int tabIndex = 0;
    private FragmentManager fragmentManager;

    private VerticalTabLayout.OnTabSelectedListener onTabSelectedListener = new VerticalTabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabView tab, int position) {
            if(position == 0){
                showHomeTab();
            }
            if(position == 1){
                showLifeTab();
            }
            if(position == 2){
                showWalletTab();
            }
            if(position == 3){
                showMyPageTab();
            }
            if(position == 4){
                showSettingTab();
            }
        }

        @Override
        public void onTabReselected(TabView tab, int position) {

        }
    };


    @Override
    protected void handleEventMsg(Message msg) {
        super.handleEventMsg(msg);
        switch (msg.what) {
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtil.e(TAG, "Activity onResume产生 myOnResume");
        if (true) {
            switch (tabIndex) {
                case 1:
                    currentFrag_0.iOnResume();
                    break;
                case 2:
                    currentFrag_1.iOnResume();
                    break;
                case 3:
                    currentFrag_2.iOnResume();
                    break;
                case 4:
                    currentFrag_3.iOnResume();
                    break;
                case 5:
                    currentFrag_4.iOnResume();
                    break;
            }
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        m_fragmentCurr.onWindowFocusChanged(hasFocus);
    }

    @Override
    protected int getResID() {
        return R.layout.activity_home;
    }

    @Override
    protected void intLayout() {
        tablayout = (VerticalTabLayout) findViewById(R.id.vertical_tab);
        fragmentManager = getSupportFragmentManager();
        currentFrag_0 = new RegularCheckFragment();
        currentFrag_1 = new SchoolAffairsFragment();
        currentFrag_2 = new AnalyzeFragment();
        currentFrag_3 = new ClassesScoresFragment();
        currentFrag_4 = new SettingsFragment();

        initFirstTab(currentFrag_0);
        tablayout.setTabAdapter(tabAdapter);
    }

    @Override
    protected void setListener() {
        tablayout.addOnTabSelectedListener(onTabSelectedListener);
    }

    @Override
    protected void initModule() {
        super.initModule();
    }

    //跟随Activity启动的第一个Fragment避免两次iOnResume，所以不使用封装好的updateFragment
    private void initFirstTab(Fragment fragment) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        hideFragments(fragmentTransaction);
        if (!fragment.isAdded()) {
            fragmentTransaction.add(R.id.vg_fragment_content, fragment).show(fragment).commit();
            fragmentManager.executePendingTransactions();
        } else if (fragment.isHidden()) {
            fragmentTransaction.show(fragment).commit();
        }
        fragmentManager.executePendingTransactions();
        m_fragmentCurr = (BaseFragment) fragment;
        if (m_fragmentCurr instanceof RegularCheckFragment) {
            tabIndex = 1;
        }
        if (m_fragmentCurr instanceof SchoolAffairsFragment) {
            tabIndex = 2;
        }
        if (m_fragmentCurr instanceof AnalyzeFragment) {
            tabIndex = 3;
        }
        if (m_fragmentCurr instanceof ClassesScoresFragment) {
            tabIndex = 4;
        }
        if (m_fragmentCurr instanceof SettingsFragment) {
            tabIndex = 5;
        }
    }

    private void showHomeTab() {
        if (tabIndex == 1) {
            return;
        }
        updateFragment(currentFrag_0);
        tabIndex = 1;
    }

    private void showLifeTab() {
        if (tabIndex == 2) {
            return;
        }
        updateFragment(currentFrag_1);
        tabIndex = 2;
    }

    public void showWalletTab() {
        if (tabIndex == 3) {
            return;
        }
        updateFragment(currentFrag_2);
        tabIndex = 3;
    }

    private void showMyPageTab() {
        if (tabIndex == 4) {
            return;
        }
        updateFragment(currentFrag_3);
        tabIndex = 4;
    }

    private void showSettingTab() {
        if (tabIndex == 5) {
            return;
        }
        updateFragment(currentFrag_4);
        tabIndex = 5;
    }

    public synchronized void updateFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        hideFragments(fragmentTransaction);
        m_fragmentCurr = (BaseFragment) fragment;
        if (!fragment.isAdded()) {
            fragmentTransaction.add(R.id.vg_fragment_content, fragment).show(fragment).commitAllowingStateLoss();
            fragmentManager.executePendingTransactions();
        } else if (fragment.isHidden()) {
            fragmentTransaction.show(fragment).commitAllowingStateLoss();
        }
        LogUtil.e(TAG, "切换tab产生 iOnResume");
        m_fragmentCurr.iOnResume();
    }

    // 当fragment已被实例化，就隐藏起来
    public void hideFragments(FragmentTransaction ft) {
        if (currentFrag_0 != null)
            ft.hide(currentFrag_0);
        if (currentFrag_1 != null)
            ft.hide(currentFrag_1);
        if (currentFrag_2 != null)
            ft.hide(currentFrag_2);
        if (currentFrag_3 != null)
            ft.hide(currentFrag_3);
        if (currentFrag_4 != null)
            ft.hide(currentFrag_4);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //钱包界面需要处理onActivityResult
        m_fragmentCurr.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - firstTime) > 2000) {
                //弹出提示，可以有多种方式
                showToast("再按一次退出程序");
                firstTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
