package com.cgy.hupu.injector.module;

import android.content.Context;

import com.cgy.hupu.components.okhttp.CookieInterceptor;
import com.cgy.hupu.components.okhttp.HttpLoggingInterceptor;
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
}
