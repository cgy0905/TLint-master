package com.cgy.hupu.module.imagepreview;

import com.cgy.hupu.injector.PerActivity;
import com.cgy.hupu.injector.component.ApplicationComponent;
import com.cgy.hupu.injector.module.ActivityModule;

import dagger.Component;

/**
 * @author cgy
 * @desctiption
 * @date 2019/5/22 14:34
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class})
public interface ImagePreviewComponent {

    void inject(ImagePreviewActivity activity);
}
