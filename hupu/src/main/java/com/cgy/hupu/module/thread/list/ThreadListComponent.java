package com.cgy.hupu.module.thread.list;

import com.cgy.hupu.injector.PerActivity;
import com.cgy.hupu.injector.component.ApplicationComponent;
import com.cgy.hupu.injector.module.ActivityModule;

import dagger.Component;

/**
 * @author cgy
 * @description 帖子列表的component
 * @date 2019/5/16 17:49
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {
        ActivityModule.class, ThreadListModule.class
})
public interface ThreadListComponent {

    void inject(ThreadListFragment threadListFragment);
}
