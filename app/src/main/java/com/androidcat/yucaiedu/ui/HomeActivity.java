package com.androidcat.yucaiedu.ui;

import android.os.Bundle;
import android.view.KeyEvent;

import com.androidcat.yucaiedu.R;
import com.androidcat.yucaiedu.adapter.HomeTabAdapter;
import com.androidcat.yucaiedu.adapter.MyPagerAdapter;
import com.anroidcat.acwidgets.SmartViewPager;

import q.rorbin.verticaltablayout.VerticalTabLayout;

public class HomeActivity extends BaseActivity {

    private long firstTime;// 记录点击返回时第一次的时间毫秒值

    private SmartViewPager viewpager;
    private VerticalTabLayout tablayout;

    private MyPagerAdapter mAdapter;
    private HomeTabAdapter tabAdapter = new HomeTabAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mAdapter = new MyPagerAdapter(getSupportFragmentManager());
        viewpager = (SmartViewPager) findViewById(R.id.viewpager);
        tablayout = (VerticalTabLayout) findViewById(R.id.vertical_tab);

        viewpager.setAdapter(mAdapter);
        viewpager.setScrollable(false);
        tablayout.setupWithViewPager(viewpager);
        tablayout.setTabAdapter(tabAdapter);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){// 点击了返回按键
            exitApp(2000);// 退出应用
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 退出应用
     * @param timeInterval 设置第二次点击退出的时间间隔
     */
    private void exitApp(long timeInterval) {
        if(System.currentTimeMillis() - firstTime >= timeInterval){
            showToast("再按一次退出程序");
            firstTime = System.currentTimeMillis();
        }else {
            finish();// 销毁当前activity
            System.exit(0);// 完全退出应用
        }
    }
}
