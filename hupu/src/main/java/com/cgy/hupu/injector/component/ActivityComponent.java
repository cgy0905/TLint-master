package com.cgy.hupu.injector.component;

import android.app.Activity;

import com.cgy.hupu.injector.PerActivity;

import dagger.Component;

/**
 * Created by cgy on 2018/12/17 2:24.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityComponent.class)
public interface ActivityComponent {

    Activity getActivity();

}
