package com.cgy.hupu.module.main;

import com.cgy.hupu.injector.PerActivity;
import com.cgy.hupu.injector.component.ApplicationComponent;
import com.cgy.hupu.injector.module.ActivityModule;
import com.cgy.hupu.module.forum.ForumListFragment;
import com.cgy.hupu.module.thread.recommend.RecommendThreadListFragment;
import com.cgy.hupu.module.thread.collect.CollectThreadListFragment;

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

    void inject(CollectThreadListFragment fragment);

    void inject(RecommendThreadListFragment fragment);

    void inject(ForumListFragment fragment);

}
