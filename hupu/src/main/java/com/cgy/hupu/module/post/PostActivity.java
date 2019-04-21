package com.cgy.hupu.module.post;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.cgy.hupu.R;
import com.cgy.hupu.module.BaseActivity;

public class PostActivity extends BaseActivity {

    public static void startActivity(Context context, int type, String fid, String tid, String pid,
                                     String title){
        Intent intent = new Intent(context, PostActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("fid", fid);
        intent.putExtra("tid", tid);
        intent.putExtra("title", title);
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
