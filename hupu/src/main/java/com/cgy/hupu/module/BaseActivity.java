package com.cgy.hupu.module;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.cgy.hupu.AppManager;
import com.cgy.hupu.MyApplication;
import com.cgy.hupu.R;
import com.cgy.hupu.injector.component.ActivityComponent;
import com.cgy.hupu.injector.component.ApplicationComponent;
import com.cgy.hupu.injector.module.ActivityModule;
import com.cgy.hupu.utils.ResourceUtil;
import com.cgy.hupu.utils.SettingPrefUtil;
import com.cgy.hupu.utils.StatusBarUtil;
import com.cgy.hupu.utils.ThemeUtil;
import com.umeng.analytics.MobclickAgent;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by cgy on 2018/10/11  9:15
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected ActivityComponent mActivityComponent;

    private Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getApplicationComponent().inject(this);
        initTheme();
        super.onCreate(savedInstanceState);
        if (initContentView() != 0) {
            setContentView(initContentView());
        }
        setTranslucentStatus(isApplyStatusBarTranslucency());
        setStatusBarColor(isApplyStatusBarColor());
        initInjector();
        initUiAndListener();
        AppManager.getAppManager().addActivity(this);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        unbinder = ButterKnife.bind(this);
    }

    protected ApplicationComponent getApplicationComponent() {
        return ((MyApplication) getApplication()).getApplicationComponent();
    }

    protected ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    private void initTheme() {
        int theme;
        try {
            theme = getPackageManager().getActivityInfo(getComponentName(), 0).theme;

        } catch (PackageManager.NameNotFoundException e) {
            return;
        }
        if (theme != R.style.AppThemeLaunch) {
            theme = ThemeUtil.themeArr[SettingPrefUtil.getThemeIndex(this)][SettingPrefUtil.getNightModel(this) ? 1 : 0];
        }
        setTheme(theme);
    }

    /**
     * 设置view
     * @return
     */
    public abstract int initContentView();

    /**
     * 注入Injector
     */
    public abstract void initInjector();


    /**
     * init UI && Listener
     */
    public abstract void initUiAndListener();


    /**
     * is applyStatusBarTranslucency
     */
    protected abstract boolean isApplyStatusBarTranslucency();

    /**
     * set status bar translucency
     */
    protected void setTranslucentStatus(boolean on) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window win = getWindow();
            WindowManager.LayoutParams winParams = win.getAttributes();
            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            if (on) {
                winParams.flags |= bits;
            } else {
                winParams.flags &= ~bits;
            }
            win.setAttributes(winParams);
        }
    }

    protected abstract boolean isApplyStatusBarColor();

    /**
     * use SystemBarTintManager
     */
    public void setStatusBarColor(boolean on) {
        if (on) {
            StatusBarUtil.setColor(this, ResourceUtil.getThemeColor(this), 0);
        }
    }

    public void reload() {
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
    }

    public int getStatusBarHeight() {
        return ResourceUtil.getStatusBarHeight(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().finishActivity(this);
        if (unbinder != null) {
            unbinder.unbind();
        }
    }
}
