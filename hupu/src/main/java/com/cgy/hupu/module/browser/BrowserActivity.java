package com.cgy.hupu.module.browser;

import android.content.Context;
import android.content.Intent;

import com.cgy.hupu.module.BaseActivity;

public class BrowserActivity extends BaseActivity {

    public static void startActivity(Context context, String url, boolean external) {
        Intent intent = new Intent(context, BrowserActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("external", external);
        context.startActivity(intent);
    }

    public static void startActivity(Context context, String url) {
        startActivity(context, url, true);
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
