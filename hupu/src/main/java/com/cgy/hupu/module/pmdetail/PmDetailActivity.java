package com.cgy.hupu.module.pmdetail;

import android.content.Context;
import android.content.Intent;

import com.cgy.hupu.module.BaseSwipeBackActivity;

/**
 * @author cgy
 * @desctiption
 * @date 2019/5/21 18:28
 */
public class PmDetailActivity extends BaseSwipeBackActivity {

    public static void startActivity(Context context, String uid, String name) {
        Intent intent = new Intent(context, PmDetailActivity.class);
        intent.putExtra("uid", uid);
        intent.putExtra("name", name);
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
