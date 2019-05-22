package com.cgy.hupu.widget;

import android.graphics.Color;
import android.text.Spannable;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.view.MotionEvent;
import android.widget.TextView;


/**
 * @author cgy
 * @desctiption
 * @date 2019/5/22 14:03
 */
public class MyMovementMethod extends LinkMovementMethod {

    private static MyMovementMethod sInstance;

    private BackgroundColorSpan mGray;

    private boolean mIsLinkHit = false;

    public static MyMovementMethod getInstance() {
        if (sInstance == null) {
            sInstance = new MyMovementMethod();
        }
        return sInstance;
    }

    @Override
    public boolean onTouchEvent(TextView widget, Spannable buffer, MotionEvent event) {
        if (mGray == null) {
            mGray = new BackgroundColorSpan(Color.parseColor("#eaeaea"));
        }

        mIsLinkHit = false;

        int action = event.getAction();

        if (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_UP) {
            int x = (int) event.getX();
            int y = (int) event.getY();

            x -= widget.getTotalPaddingLeft();
            y -= widget.getTotalPaddingTop();

            x += widget.getScrollX();
            y += widget.getScrollY();

            int line = widget.getLayout().getLineForVertical(y);
            int offset = widget.getLayout().getOffsetForHorizontal(line, x);

            ClickableSpan[] spans = buffer.getSpans(offset, offset, ClickableSpan.class);

            if (spans.length != 0) {
                int start = buffer.getSpanStart(spans[0]);
                int end = buffer.getSpanEnd(spans[0]);

                mIsLinkHit = true;

                if (action == MotionEvent.ACTION_DOWN) {
                    buffer.setSpan(mGray, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                } else if (action == MotionEvent.ACTION_UP) {
                    spans[0].onClick(widget);

                    buffer.removeSpan(mGray);
                }
                return true;
            }
        } else {
            buffer.removeSpan(mGray);
        }
        return super.onTouchEvent(widget, buffer, event);
    }

    public boolean isLinkHit() {
        boolean ret = mIsLinkHit;
        mIsLinkHit = false;
        return ret;
    }
}
