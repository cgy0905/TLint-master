package com.cgy.hupu.injector.module;

import android.app.Service;

import com.cgy.hupu.injector.PerService;

import dagger.Module;
import dagger.Provides;

/**
 * Created by cgy on 2018/10/10  16:27
 */

@Module
public class ServiceModule {
    private Service service;

    public ServiceModule(Service service) {
        this.service = service;
    }

    @Provides
    @PerService
    public Service provideContext() {
        return service;
    }
}
