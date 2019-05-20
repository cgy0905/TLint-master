package com.cgy.hupu.module.setting;

import com.cgy.hupu.injector.PerActivity;
import com.cgy.hupu.injector.component.ApplicationComponent;
import com.cgy.hupu.injector.module.ActivityModule;

import dagger.Component;

/**
 * @author cgy
 * @desctiption
 * @date 2019/5/20 15:22
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class})
public interface SettingComponent {

    void inject(SettingFragment fragment);

    void inject(ColorsDialogFragment fragment);
}
