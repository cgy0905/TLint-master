package com.cgy.hupu.injector.module;

import android.content.Context;

import com.cgy.hupu.components.UserStorage;
import com.cgy.hupu.components.retrofit.RequestHelper;
import com.cgy.hupu.net.forum.ForumApi;
import com.cgy.hupu.net.game.GameApi;
import com.cgy.hupu.net.login.CookieApi;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

/**
 * Created by cgy on 2018/10/11  10:40
 */

@Module
public class ApiModule {

    @Provides
    @Singleton
    public ForumApi provideHuPuApi(UserStorage userStorage, @Named("api")OkHttpClient okHttpClient,
                                   RequestHelper requestHelper, Context context) {
        return new ForumApi(requestHelper, userStorage, okHttpClient, context);
    }

    @Provides
    @Singleton
    public GameApi provideGameApi(RequestHelper requestHelper, @Named("api") OkHttpClient okHttpClient) {
        return new GameApi(requestHelper, okHttpClient);
    }

    @Provides
    @Singleton
    public CookieApi provideCookieApi(@Named("api") OkHttpClient okHttpClient) {
        return new CookieApi(okHttpClient);
    }

}
