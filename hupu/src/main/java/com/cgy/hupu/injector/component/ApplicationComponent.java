package com.cgy.hupu.injector.component;

import com.cgy.hupu.injector.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by cgy on 2018/10/10  16:09
 */

@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {
}
