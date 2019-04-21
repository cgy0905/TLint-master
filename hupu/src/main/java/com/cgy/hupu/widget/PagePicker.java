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

    private MaterialNumberPicker picker;

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
        rootView.findViewById(R.id.btn_first).setOnClickListener(this);
        rootView.findViewById(R.id.btn_jump).setOnClickListener(this);
        rootView.findViewById(R.id.btn_last).setOnClickListener(this);
        picker = (MaterialNumberPicker) rootView.findViewById(R.id.picker);
        setContentView(rootView);
    }

    void jump() {
        int page = picker.getValue();
        if (mListener != null) {
            mListener.onJump(page);
        }
        dismiss();
    }
    void end() {
        int page = picker.getMaxValue();
        if (mListener != null) {
            mListener.onJump(page);
        }
        dismiss();
    }
    void first() {
        int page = picker.getMinValue();
        if (mListener != null) {
            mListener.onJump(page);
        }
        dismiss();
    }

    public void setMin(int min) {
        picker.setMinValue(min);
    }
    public void setMax(int max) {
        picker.setMaxValue(max);
    }
    public void setValue(int value) {
        picker.setValue(value);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_cancel:
                dismiss();
                break;
            case R.id.btn_first:
                first();
                break;
            case R.id.btn_last:
                end();
                break;
            case R.id.btn_jump:
                jump();
                break;
        }
    }
    private OnJumpListener mListener;

    public void setOnJumpListener(OnJumpListener listener) {
        mListener = listener;
    }
    public interface OnJumpListener {
        void onJump(int page);
    }
}
