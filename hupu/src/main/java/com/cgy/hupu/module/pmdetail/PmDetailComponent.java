package com.cgy.hupu.module.pmdetail;

import com.cgy.hupu.injector.PerActivity;
import com.cgy.hupu.injector.component.ApplicationComponent;
import com.cgy.hupu.injector.module.ActivityModule;

import dagger.Component;

/**
 * @author cgy
 * @desctiption
 * @date 2019/5/22 9:48
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {
        ActivityModule.class, PmDetailModule.class
})
public interface PmDetailComponent {

    void inject(PmDetailFragment fragment);
}
