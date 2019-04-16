package com.cgy.hupu.module.account;

import com.cgy.hupu.injector.PerActivity;
import com.cgy.hupu.injector.component.ApplicationComponent;
import com.cgy.hupu.injector.module.ActivityModule;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {
        ActivityModule.class, AccountModule.class
})
public interface AccountComponent {
    void inject(AccountActivity mAccountActivity);
}
