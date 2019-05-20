package com.cgy.hupu.module.userprofile;

import com.cgy.hupu.injector.PerActivity;
import com.cgy.hupu.injector.component.ApplicationComponent;
import com.cgy.hupu.injector.module.ActivityModule;

import dagger.Component;

/**
 * @author cgy
 * @desctiption
 * @date 2019/5/20 13:52
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {
        ActivityModule.class, UserProfileModule.class
})
public interface UserProfileComponent {

    void inject(UserProfileActivity activity);
}
