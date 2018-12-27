package com.cgy.hupu.module.main;

import com.cgy.hupu.injector.PerActivity;
import com.cgy.hupu.injector.component.ApplicationComponent;
import com.cgy.hupu.injector.module.ActivityModule;

import dagger.Component;

/**
 * Created by cgy on 2018/12/17 .
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {
        ActivityModule.class, MainModule.class
})
public interface MainComponent {

    void inject(MainActivity activity);

}
