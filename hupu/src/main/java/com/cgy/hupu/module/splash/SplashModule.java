package com.cgy.hupu.module.splash;

import dagger.Module;

/**
 * Created by cgy on 2018/10/17  9:41
 */

@Module
class SplashModule {

    private SplashActivity activity;

    public SplashModule(SplashActivity activity) {
        this.activity = activity;
    }
}
