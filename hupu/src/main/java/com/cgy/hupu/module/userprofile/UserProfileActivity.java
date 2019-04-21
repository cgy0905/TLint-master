package com.cgy.hupu.module.userprofile;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.cgy.hupu.R;
import com.cgy.hupu.module.BaseActivity;

public class UserProfileActivity extends BaseActivity {
    public static void startActivity(Context context, String uid) {
        Intent intent = new Intent(context, UserProfileActivity.class);
        intent.putExtra("uid", uid);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
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
