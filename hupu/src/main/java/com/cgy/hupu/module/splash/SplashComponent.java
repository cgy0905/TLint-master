package com.cgy.hupu.module.splash;

import com.cgy.hupu.injector.PerActivity;
import com.cgy.hupu.injector.component.ApplicationComponent;
import com.cgy.hupu.injector.module.ActivityModule;

import dagger.Component;

/**
 * Created by cgy on 2018/10/17  9:39
 */

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {
        ActivityModule.class, SplashModule.class
})
public interface SplashComponent {

    void inject(SplashActivity activity);
}
