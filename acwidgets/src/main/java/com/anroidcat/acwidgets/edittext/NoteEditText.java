package com.anroidcat.acwidgets.edittext;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.ScrollView;

import com.anroidcat.acwidgets.R;
import com.anroidcat.acwidgets.utils.ViewUtils;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by androidcat on 2018/12/21.
 */

public class NoteEditText extends EditText {

    private Context context;
    private boolean initialized;
    // 画笔 用来画下划线
    private Paint paint;
    private Paint mNumPaint;
    private Paint mRectPaint;
    private int mNumberLength;
    private float mTextSize; // sp

    private ScrollView mScrollView;

    public static final String KEY_TEXT_SIZE = "textSize";
    private String KEY_NUMBER_LENGTH = "_numberLength";
    public static final String KEY_SHOW_LINE = "showLine";
    public static final String KEY_SHOW_LINE_NUMBER = "showLineNumber";

    public static final float DEFAULT_VALUE_TEXT_SIZE = 20;
    public static final boolean DEFAULT_VALUE_SHOW_LINE = true;
    public static final boolean DEFAULT_VALUE_SHOW_LINE_NUMBER = false;
    private boolean showLine;
    private boolean showLineNumber;

    private int mPaddingLeft;

    private static final int LINE_OFFSET = 50;

    float displayPaddingLeft;

    public NoteEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
        setTextCursor();
    }

    // 自定义光标
    private void setTextCursor() {
        try {
            Method createEditorIfNeeded = ReflectionUtils.getMethod(
                    "android.widget.TextView", "createEditorIfNeeded", null);
            if (createEditorIfNeeded != null) {
                createEditorIfNeeded.setAccessible(true);
                createEditorIfNeeded.invoke(this, new Object[0]);
                Field editor = ReflectionUtils.getField(
                        "android.widget.TextView", "mEditor");
                if (editor != null) {
                    editor.setAccessible(true);
                    Field cursorDrawable = ReflectionUtils.getField(
                            "android.widget.Editor", "mCursorDrawable");
                    if (cursorDrawable != null) {
                        cursorDrawable.setAccessible(true);
                        Array.set(cursorDrawable.get(editor.get(this)), 0,
                                new LineSpaceCursorDrawable(context, this));
                        Array.set(cursorDrawable.get(editor.get(this)), 1,
                                new LineSpaceCursorDrawable(context, this));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void init() {
        showLine = true;
        if (showLine) {
            paint = new Paint();
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(ViewUtils.dip2px(context, 0.5f));
            paint.setColor(context.getResources().getColor(
                    R.color.red_one));
            // 开启抗锯齿 较耗内存
            paint.setAntiAlias(true);
        }
        showLineNumber = false;
        if (showLineNumber) {
            mNumPaint = new Paint();
            mNumPaint.setStyle(Paint.Style.FILL);
            mNumPaint.setColor(context.getResources().getColor(
                    R.color.gray));
            // 开启抗锯齿 较耗内存
            mNumPaint.setAntiAlias(true);

            mPaddingLeft = getPaddingLeft();

            mRectPaint = new Paint();
            mRectPaint.setStyle(Paint.Style.FILL);
            mRectPaint.setColor(context.getResources().getColor(
                    R.color.text_black2));// Color.rgb(153,
            // 148,
            // 252));
        }
        displayPaddingLeft = ViewUtils.dip2px(context, 5);
        //setTextSize(Cache.read_float(KEY_TEXT_SIZE, DEFAULT_VALUE_TEXT_SIZE));
        setTextSize(18);
    }

    // 添加弹性效果
    public void setScrollView(ScrollView scrollView) {
        mScrollView = scrollView;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        try {
            if (!showLine && !showLineNumber)
                return;

            if (!initialized)
                return;

            // 得到总行数
            int lineCount = getLineCount();
            // 得到每行的高度
            int lineHeight = getLineHeight();

            int lineStart = 0;

            if (mScrollView != null) {
                lineStart = (mScrollView.getScrollY() - getPaddingTop())
                        / lineHeight - LINE_OFFSET;
                lineStart = lineStart >= 0 ? lineStart : 0;
            }
            int lineEnd = lineStart
                    + ScreenInfo.getInstance().getHeightPixels() / lineHeight
                    + LINE_OFFSET * 2;

            if (showLineNumber) {

                int numberLength = getNumberLength(lineCount);
                numberLength = numberLength > 2 ? numberLength : 2;
                if (numberLength != mNumberLength) {
                    /*Logger.d("numberLength != mNumberLength lineCount:"
                            + lineCount + " getVisibility:"
                            + this.getVisibility());*/
                    mNumberLength = numberLength;
                    //Cache.write_int(KEY_NUMBER_LENGTH, mNumberLength);
                    adjustPadding();
                }

                // 绘制行号背景
                canvas.drawRect(new Rect(0, mScrollView.getScrollY()
                        - (int) ViewUtils.dip2px(context, 600), mPaddingLeft
                        - (int) displayPaddingLeft, mScrollView.getScrollY()
                        + ScreenInfo.getInstance().getHeightPixels()
                        + (int) ViewUtils.dip2px(context, 600)), mRectPaint);

                float textWidth = getNumberTextWidth(mNumPaint);

                for (int i = lineStart; i < lineEnd; i++) {
                    int x = (int) (mPaddingLeft - displayPaddingLeft
                            - ViewUtils.dip2px(context, 15) / 2 - getNumberLength(i + 1)
                            * textWidth);
                    int y = ((i + 1) * getLineHeight()) - (getLineHeight() / 8);
                    canvas.drawText(String.valueOf(i + 1), x, y, mNumPaint);
                }
            }

            if (showLine) {
                // 根据行数循环画线
                for (int i = lineStart; i < lineEnd; i++) {
                    int lineY = (i + 1) * lineHeight;
                    canvas.drawLine(
                            0,
                            this.getPaddingTop()
                                    - ViewUtils.getLineSpacingExtra(context,
                                    this) / 2 + lineY,
                            this.getWidth(),
                            this.getPaddingTop()
                                    - ViewUtils.getLineSpacingExtra(context,
                                    this) / 2 + lineY, paint);
                }
            }
        } finally {
            super.onDraw(canvas);
        }

    }

    // 调整行号的宽度
    private void adjustPadding() {
        mPaddingLeft = (int) (ViewUtils.dip2px(context, 15)
                + displayPaddingLeft + mNumberLength
                * getNumberTextWidth(mNumPaint));
        setPadding(mPaddingLeft, getPaddingTop(), getPaddingRight(),
                getPaddingBottom());
    }

    public void initText(CharSequence text) {
        if (!TextUtils.isEmpty(text))
            setText(text);
        initialized = true;
    }

    @Override
    public void setTextSize(float size) {
        /*Logger.d("mTextSize" + mTextSize);
        Logger.d("mTextSize size:" + size);
        Logger.d("mTextSize" + Calculator.equals(size, mTextSize));*/
        if (size != mTextSize) {
            super.setTextSize(size);
            mTextSize = size;
            //Logger.d("mTextSize" + mTextSize);
            if (showLineNumber) {
                mNumPaint.setTextSize(getTextSize());
                adjustPadding();
            }
            //Cache.write_float(KEY_TEXT_SIZE, mTextSize);
        }
    }

    public float getTextSizeSP() {
        return mTextSize;
    }

    public static float getNumberTextWidth(Paint paint) {
        float[] widths = new float[1];
        paint.getTextWidths("0", widths);
        return widths[0];
    }

    public void initNumberLength(String filePath) {
        if (showLineNumber) {
            KEY_NUMBER_LENGTH = filePath + KEY_NUMBER_LENGTH;
            mNumberLength = /*Cache.read_int(KEY_NUMBER_LENGTH,
                    getNumberLength(getLineCount()))*/100;
            adjustPadding();
        }
    }

    public static int getTextWidth(Paint paint, String str) {
        int iRet = 0;
        if (str != null && str.length() > 0) {
            int len = str.length();
            float[] widths = new float[len];
            paint.getTextWidths(str, widths);
            for (int j = 0; j < len; j++) {
                iRet += (int) Math.ceil(widths[j]);
            }
        }
        return iRet;
    }

    private int getNumberLength(int n) {
        int length = 0;
        while (n > 0) {
            n = n / 10;
            length++;
        }
        return length;
    }

}

