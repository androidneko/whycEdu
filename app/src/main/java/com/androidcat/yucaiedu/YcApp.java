package com.androidcat.yucaiedu;

import android.app.Application;
import android.content.Context;

import com.androidcat.utilities.persistence.SharePreferencesUtil;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;

public class YcApp extends Application{

    @Override
    public void onCreate() {
        super.onCreate();

        //初始化图片加载
        initImageLoader(this);
        //初始化缓存Context
        initSharePreference();
        //初始化日志工具
        //initLogger();
        initFont();
    }

    void initFont(){
        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()
                                .setDefaultFontPath("fonts/jianshi_default.otf")
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build());
    }

    private static void initImageLoader(Context context) {
        // This configuration tuning is custom. You can tune every option, you
        // may tune some of them,
        // or you can create default configuration by
        // ImageLoaderConfiguration.createDefault(this);
        // method.
//        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
//                context).threadPriority(Thread.NORM_PRIORITY - 2)// 设置线程的优先级
//                .denyCacheImageMultipleSizesInMemory()// 当同一个Uri获取不同大小的图片，缓存到内存时，只缓存一个。默认会缓存多个不同的大小的相同图片
//                .discCacheFileNameGenerator(new Md5FileNameGenerator())// 设置缓存文件的名字
//                .discCacheFileCount(100)// 缓存文件的最大个数
//                .tasksProcessingOrder(QueueProcessingType.LIFO)// 设置图片下载和显示的工作队列排序
//                .build();

        // Initialize ImageLoader with configuration
        //ImageLoader.getInstance().init(config);
    }

    private void initSharePreference(){
        SharePreferencesUtil.init(this);
    }
}
