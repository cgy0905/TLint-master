package com.cgy.hupu.module.messagelist;

import com.cgy.hupu.injector.PerActivity;
import com.cgy.hupu.injector.component.ApplicationComponent;
import com.cgy.hupu.injector.module.ActivityModule;
import com.cgy.hupu.module.pmlist.PmListFragment;

import dagger.Component;

/**
 * Created by cgy on 2019/4/17.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class})
public interface MessageComponent {

    void inject(MessageListFragment fragment);

    void inject(PmListFragment fragment);
}
