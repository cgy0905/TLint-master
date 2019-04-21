package com.cgy.hupu.module;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.cgy.hupu.utils.SettingPrefUtil;
import com.cgy.hupu.widget.swipeback.SwipeBackActivityBase;
import com.cgy.hupu.widget.swipeback.SwipeBackActivityHelper;
import com.cgy.hupu.widget.swipeback.SwipeBackLayout;
import com.cgy.hupu.widget.swipeback.Utils;

/**
 * Created by cgy on 2018/10/15  15:32
 */
public abstract class BaseSwipeBackActivity extends BaseActivity implements SwipeBackActivityBase {
    private SwipeBackActivityHelper mHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHelper = new SwipeBackActivityHelper(this);
        mHelper.onActivityCreate();
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mHelper.onPostCreate();
    }

    @Override
    public View findViewById(int id) {
        View v = super.findViewById(id);
        if (v == null && mHelper != null) return mHelper.findViewById(id);
        return v;
    }

    @Override
    public SwipeBackLayout getSwipeBackLayout() {
        return mHelper.getSwipeBackLayout();
    }

    @Override
    public void setSwipeBackEnable(boolean enable) {
        getSwipeBackLayout().setEnableGesture(enable);
    }

    @Override
    public void scrollToFinishActivity() {
        Utils.convertActivityFromTranslucent(this);
        getSwipeBackLayout().scrollToFinishActivity();
    }

    @Override
    public void finish() {
        super.finish();
    }

    public void initToolBar(Toolbar toolbar) {
        if (null != toolbar) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowHomeEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        int mode = SettingPrefUtil.getSwipeBackEdgeMode(this);
        SwipeBackLayout swipeBackLayout = mHelper.getSwipeBackLayout();
        switch (mode) {
            case 0:
                swipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
                break;
            case 1:
                swipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_RIGHT);
                break;
            case 2:
                swipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_BOTTOM);
                break;
            case 3:
                swipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_ALL);
                break;
        }
    }
}
