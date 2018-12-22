package com.anroidcat.acwidgets.edittext;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.ShapeDrawable;
import android.text.Editable;
import android.text.style.ImageSpan;
import android.widget.EditText;

import com.anroidcat.acwidgets.R;
import com.anroidcat.acwidgets.utils.ViewUtils;

/**
 * Created by androidcat on 2018/12/21.
 */

public class LineSpaceCursorDrawable extends ShapeDrawable {

    private Context context;
    private EditText view;

    public LineSpaceCursorDrawable(Context context, EditText view) {
        this.context = context;
        setDither(false);
        Resources res = view.getResources();
        getPaint().setColor(context.getResources().getColor(R.color.text_black2));//R.color.note_edittext_cursor_color));
        setIntrinsicWidth((int)ViewUtils.dip2px(context, 2));//res.getDimensionPixelSize(R.dimen.detail_notes_text_cursor_width));
        this.view = view;
    }

    public void setBounds(int left, int top, int right, int bottom) {

        Editable s = view.getText();
        ImageSpan[] imageSpans = s.getSpans(0, s.length(),
                ImageSpan.class);
        int selectionStart = view.getSelectionStart();
        for (ImageSpan span : imageSpans) {
            int start = s.getSpanStart(span);
            int end = s.getSpanEnd(span);
            if (selectionStart >= start && selectionStart <= end)
            {
                super.setBounds(left, top, right, top - 1);
                return;
            }
        }
        super.setBounds(left, top, right, top + view.getLineHeight() - (int) ViewUtils.getLineSpacingExtra(context, view));

    }
}

