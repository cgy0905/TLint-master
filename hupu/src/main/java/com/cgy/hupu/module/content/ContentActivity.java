package com.cgy.hupu.module.content;

import android.content.Context;
import android.content.Intent;

import com.cgy.hupu.module.BaseSwipeBackActivity;

/**
 * Created by cgy on 2019/4/17.
 */
public class ContentActivity extends BaseSwipeBackActivity {

    public static void startActivity(Context context, String fid, String tid, String pid, int page) {
        Intent intent = new Intent(context, ContentActivity.class);
        intent.putExtra("fid", fid);
        intent.putExtra("tid", tid);
        intent.putExtra("pid", pid);
        intent.putExtra("fid", page);
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
