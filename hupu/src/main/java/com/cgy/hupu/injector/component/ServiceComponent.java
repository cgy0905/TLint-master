package com.cgy.hupu.injector.component;


import android.app.Service;

import com.cgy.hupu.injector.PerService;
import com.cgy.hupu.injector.module.ServiceModule;
import com.cgy.hupu.service.MessageService;
import com.cgy.hupu.service.OfflineService;

import dagger.Component;

/**
 * Created by cgy on 2018/10/10  16:07
 */
@PerService
@Component(dependencies = ApplicationComponent.class, modules = {ServiceModule.class})
public interface ServiceComponent {

    Service getServiceContext();

    void inject(MessageService messageService);

    void inject(OfflineService offlineService);
}
