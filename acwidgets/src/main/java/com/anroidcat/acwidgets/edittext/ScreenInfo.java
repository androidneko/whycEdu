package com.anroidcat.acwidgets.edittext;

import android.app.Activity;
import android.util.DisplayMetrics;

/**
 * Created by androidcat on 2018/12/21.
 */

public class ScreenInfo {
    private int widthPixels;
    private int heightPixels;

    private static ScreenInfo instance;

    private ScreenInfo(Activity context) {
        DisplayMetrics dm = new DisplayMetrics();

        context.getWindowManager().getDefaultDisplay().getMetrics(dm);
        this.widthPixels = dm.widthPixels;
        this.heightPixels = dm.heightPixels;
    }

    public static void createInstance(Activity context){
        if(instance == null)
            instance = new ScreenInfo(context);
    }

    public static ScreenInfo getInstance(){
        return instance;
    }

    public static ScreenInfo getInstance(Activity context){
        if(instance == null)
            instance = new ScreenInfo(context);
        return instance;
    }

    /**
     * @return the number of pixel in the width of the screen.
     */
    public int getWidthPixels() {
        return widthPixels;
    }

    /**
     * @return the number of pixel in the height of the screen.
     */
    public int getHeightPixels() {
        return heightPixels;
    }

    public String getSize() {
        return widthPixels + "×" + heightPixels;
    }
}

