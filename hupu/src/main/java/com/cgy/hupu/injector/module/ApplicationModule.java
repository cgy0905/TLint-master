package com.cgy.hupu.injector.module;

import android.app.NotificationManager;
import android.content.Context;
import android.view.LayoutInflater;

import com.cgy.hupu.components.UserStorage;
import com.cgy.hupu.components.okhttp.CookieInterceptor;
import com.cgy.hupu.components.okhttp.HttpLoggingInterceptor;
import com.cgy.hupu.components.okhttp.OkHttpHelper;
import com.cgy.hupu.components.retrofit.RequestHelper;
import com.squareup.otto.Bus;

import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

/**
 * Created by cgy on 2018/10/10  16:11
 */

@Module
public class ApplicationModule {

    private final Context context;

    public ApplicationModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    public Context provideApplicationContext(){
        return context.getApplicationContext();
    }

    @Provides
    @Singleton
    public Bus provideBusEvent() {
        return new Bus();
    }

    @Provides
    @Singleton
    @Named("api")
    OkHttpClient provideApiOkHttpClient(CookieInterceptor cookieInterceptor) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS);
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(logging);
        builder.addInterceptor(cookieInterceptor);
        return builder.build();
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(@Named("api") OkHttpClient okHttpClient) {
        OkHttpClient.Builder builder = okHttpClient.newBuilder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true);
        builder.interceptors().clear();
        return builder.build();
    }

    @Provides
    @Singleton
    LayoutInflater provideLayoutInflater(Context context) {
        return (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Provides
    @Singleton
    NotificationManager provideNotificationManager(Context context) {
        return (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    @Provides
    @Singleton
    CookieInterceptor provideCookieInterceptor(UserStorage userStorage) {
        return new CookieInterceptor(userStorage);
    }

    @Provides
    @Singleton
    UserStorage provideUserStorage(Context context) {
        return new UserStorage(context);
    }

    @Provides
    @Singleton
    RequestHelper provideRequestHelper(Context context, UserStorage userStorage) {
        return new RequestHelper(context, userStorage);
    }

    @Provides
    @Singleton
    OkHttpHelper provideOkHttpHelper(OkHttpClient okHttpClient) {
        return new OkHttpHelper(okHttpClient);
    }
}
