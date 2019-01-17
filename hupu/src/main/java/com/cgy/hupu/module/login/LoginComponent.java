package com.cgy.hupu.module.login;

import com.cgy.hupu.injector.PerActivity;
import com.cgy.hupu.injector.component.ApplicationComponent;
import com.cgy.hupu.injector.module.ActivityModule;

import dagger.Component;

/**
 * Created by cgy on 2019/1/17 上午 11:11.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class})
public interface LoginComponent {
    void inject(LoginActivity activity);
}
