package com.cgy.hupu.module.messagelist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.cgy.hupu.R;
import com.cgy.hupu.module.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cgy on 2018/10/17  10:47
 */
public class MessageActivity extends BaseActivity {

    @BindView(R.id.appbar_layout)
    AppBarLayout appbar;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, MessageActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int initContentView() {
        return R.layout.activity_message;
    }

    @Override
    public void initInjector() {

    }

    @Override
    public void initUiAndListener() {

    }

    @Override
    protected boolean isApplyStatusBarTranslucency() {
        return false;
    }

    @Override
    protected boolean isApplyStatusBarColor() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
