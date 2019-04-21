package com.cgy.hupu.injector.component;

import android.app.Activity;

import com.cgy.hupu.injector.PerActivity;
import com.cgy.hupu.injector.module.ActivityModule;

import dagger.Component;

/**
 * Created by cgy on 2018/12/17 2:24.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    Activity getActivity();

}
