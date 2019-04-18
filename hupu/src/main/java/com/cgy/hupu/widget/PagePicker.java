package com.cgy.hupu.widget;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.cgy.hupu.R;

/**
 * Created by cgy on 2019/4/18.
 */
public class PagePicker extends PopupWindow implements View.OnClickListener {

    public PagePicker(Context context) {
        this(context, null);
    }

    public PagePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setWidth(ViewGroup.LayoutParams.FILL_PARENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setBackgroundDrawable(new BitmapDrawable());//这样设置才能点击屏幕外dismiss
        this.setOutsideTouchable(true);
        this.setAnimationStyle(R.style.popwindow_anim_style);

        LayoutInflater mLayoutInflater = LayoutInflater.from(context);
        View rootView = mLayoutInflater.inflate(R.layout.page_picker_view, null);
        rootView.findViewById(R.id.btn_cancel).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }
}
