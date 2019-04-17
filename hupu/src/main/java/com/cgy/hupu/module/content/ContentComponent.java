package com.cgy.hupu.module.content;

import com.cgy.hupu.injector.PerActivity;
import com.cgy.hupu.injector.component.ActivityComponent;
import com.cgy.hupu.injector.component.ApplicationComponent;
import com.cgy.hupu.injector.module.ActivityModule;

import dagger.Component;

/**
 * Created by cgy on 2019/4/17.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {
        ActivityModule.class, ContentModule.class
})
public interface ContentComponent extends ActivityComponent {

    void inject(ContentActivity activity);

    void inject();
}
