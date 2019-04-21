package com.cgy.hupu.module.splash;

import dagger.Module;

/**
 * Created by cgy on 2018/10/17  9:41
 */

@Module
public class SplashModule {

    private SplashActivity mActivity;

    public SplashModule(SplashActivity activity) {
        this.mActivity = activity;
    }
}
