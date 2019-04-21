package com.cgy.hupu.module.splash;

import android.text.TextUtils;
import android.widget.FrameLayout;

import com.cgy.hupu.R;
import com.cgy.hupu.module.BaseActivity;
import com.cgy.hupu.module.main.MainActivity;
import com.cgy.hupu.module.messagelist.MessageActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cgy on 2018/10/17 .
 */
public class SplashActivity extends BaseActivity implements SplashContract.View{

    public static final String ACTION_NOTIFICATION_MESSAGE = "com.cgy.hupu.ACTION_NOTIFICATION_MESSAGE";

    @BindView(R.id.fl_splash)
    FrameLayout flSplash;

    @Inject
    SplashPresenter splashPresenter;

    @Override
    public int initContentView() {
        return R.layout.activity_splash;
    }

    @Override
    public void initInjector() {
        DaggerSplashComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .splashModule(new SplashModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void initUiAndListener() {
        ButterKnife.bind(this);
        splashPresenter.attachView(this);
        splashPresenter.initUmeng();
        splashPresenter.initHuPuSign();
    }

    @Override
    protected boolean isApplyStatusBarTranslucency() {
        return true;
    }

    @Override
    protected boolean isApplyStatusBarColor() {
        return false;
    }


    @Override
    public void showMainUi() {
        MainActivity.startActivity(SplashActivity.this);
        String action = getIntent().getAction();
        if (TextUtils.equals(action, ACTION_NOTIFICATION_MESSAGE)) {
            MessageActivity.startActivity(SplashActivity.this);
        }
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        splashPresenter.detachView();
    }
}
