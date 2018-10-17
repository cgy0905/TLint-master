package com.cgy.hupu.module.messagelist;

import android.content.Context;
import android.content.Intent;

import com.cgy.hupu.module.BaseActivity;

/**
 * Created by cgy on 2018/10/17  10:47
 */
public class MessageActivity extends BaseActivity {

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, MessageActivity.class);
        context.startActivity(intent);
    }
    @Override
    public int initContentView() {
        return 0;
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
}
