package com.cgy.hupu.widget.state;

import android.view.View;
import android.view.animation.Animation;

/**
 * Created by cgy on 2018/10/15  13:18
 */
public abstract class AbstractShowState implements ShowState{
    protected View fragmentView;
    protected Animation animationIn;
    protected Animation animationOut;


    protected void showViewById(int viewId, boolean animate) {
        View content = fragmentView.findViewById(viewId);
        if (animate) {
            animationIn.reset();
            content.startAnimation(animationIn);
        } else {
            content.clearAnimation();
        }

        content.setVisibility(View.VISIBLE);
    }

    protected void dismissViewById(int viewId, boolean animate) {
        View content = fragmentView.findViewById(viewId);
        if (animate) {
            animationOut.reset();
            content.startAnimation(animationOut);
        } else {
            content.clearAnimation();
        }

        content.setVisibility(View.GONE);
    }

    @Override
    public void setFragmentView(View fragmentView) {
        this.fragmentView = fragmentView;
    }

    @Override
    public void setAnimIn(Animation in) {
        animationIn  = in;
    }

    @Override
    public void setAnimOut(Animation out) {
        animationOut = out;
    }
}
