package com.cgy.hupu.injector.component;

import android.app.NotificationManager;
import android.content.Context;

import com.cgy.hupu.MyApplication;
import com.cgy.hupu.components.UserStorage;
import com.cgy.hupu.components.okhttp.OkHttpHelper;
import com.cgy.hupu.db.UserDao;
import com.cgy.hupu.injector.module.ApiModule;
import com.cgy.hupu.injector.module.ApplicationModule;
import com.cgy.hupu.injector.module.DBModule;
import com.cgy.hupu.module.BaseActivity;
import com.cgy.hupu.net.forum.ForumApi;
import com.cgy.hupu.net.game.GameApi;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by cgy on 2018/10/10  16:09
 */

@Singleton
@Component(modules = {ApplicationModule.class, ApiModule.class, DBModule.class})
public interface ApplicationComponent {

    Context getContext();

    Bus getBus();

    ForumApi getForumApi();

    GameApi getGameApi();

    UserDao getUserDao();

    OkHttpHelper getOkHttpHelper();

    UserStorage getUserStorage();

    NotificationManager getNotificationManager();

    void inject(MyApplication myApplication);

    void inject(BaseActivity baseActivity);

}
