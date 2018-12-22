package com.anroidcat.acwidgets.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.TypedValue;
import android.widget.TextView;

/**
 * Created by androidcat on 2018/12/21.
 */

public class ViewUtils {
    public static int dp2px(Context context, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                context.getResources().getDisplayMetrics());
    }

    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     *
     * @param dipValue
     *            （DisplayMetrics类中属性density）
     * @return
     */
    public static float dip2px(Context context, float dipValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return dipValue * scale + 0.5f;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static float getLineSpacingExtra(Context context, TextView view){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            return view.getLineSpacingExtra();
        }
        else{
            return dip2px(context, 8);
        }
    }

}
