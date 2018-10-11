package com.cgy.hupu.injector.module;

import android.app.Activity;
import android.support.v4.app.ActivityCompat;

import com.cgy.hupu.injector.PerActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by cgy on 2018/10/11  9:47
 */
@Module
public class ActivityModule {

    private final Activity activity;

    public ActivityModule(Activity activity){
        this.activity = activity;
    }

    @Provides
    @PerActivity
    public Activity provideActivity() {
        return activity;
    }
}
