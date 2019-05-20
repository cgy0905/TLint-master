package com.cgy.hupu.module.setting;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.cgy.hupu.R;
import com.cgy.hupu.injector.HasComponent;
import com.cgy.hupu.module.BaseSwipeBackActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingActivity extends BaseSwipeBackActivity implements HasComponent<SettingComponent> {

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, SettingActivity.class);
        context.startActivity(intent);
    }

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private SettingComponent mSettingComponent;

    @Override
    public int initContentView() {
        return R.layout.base_content_toolbar_layout;
    }

    @Override
    public void initInjector() {
        mSettingComponent = DaggerSettingComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
    }

    @Override
    public void initUiAndListener() {
        ButterKnife.bind(this);
        initToolBar(toolbar);
        setTitle("设置");
        getFragmentManager().beginTransaction().replace(R.id.fl_content, new SettingFragment()).commit();
    }

    @Override
    protected boolean isApplyStatusBarTranslucency() {
        return false;
    }

    @Override
    protected boolean isApplyStatusBarColor() {
        return true;
    }

    @Override
    public SettingComponent getComponent() {
        return mSettingComponent;
    }

}
