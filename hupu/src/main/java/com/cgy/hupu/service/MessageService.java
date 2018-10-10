package com.cgy.hupu.service;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.cgy.hupu.Logger;
import com.cgy.hupu.MyApplication;
import com.cgy.hupu.components.UserStorage;
import com.cgy.hupu.injector.module.ServiceModule;
import com.cgy.hupu.net.ForumApi;

import javax.inject.Inject;

/**
 * Created by cgy on 2018/10/10  15:00
 */
public class MessageService extends Service {

    public static final String ACTION_GET = "com.cgy.hupu.ACTION_GET";
    public static final String ACTION_UPDATE = "com.cgy.hupu.ACTION_UPDATE";
    public static final String ACTION_CLOSE = "com.cgy.hupu.ACTION_CLOSE";

    @Inject
    UserStorage userStorage;

    @Inject
    ForumApi forumApi;

    private NotificationManager notificationManager;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.d("服务初始化");
//        DaggerServiceComponent.builder()
//                .serviceModule(new ServiceModule(this))
//                .applicationComponent(((MyApplication) getApplication()).getApplicationComponent())
//                .build()
//                .inject(this);
    }
}
