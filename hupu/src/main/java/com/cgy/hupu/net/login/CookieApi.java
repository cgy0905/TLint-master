package com.cgy.hupu.net.login;

import com.cgy.hupu.components.retrofit.FastJsonConverterFactory;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * Created by cgy on 2018/10/11  9:59
 */
public class CookieApi {
    private CookieService cookieService;

    private static final String BASE_URL = "http://passport.hupu.com/pc/login/";

    public CookieApi(OkHttpClient okHttpClient) {
        Retrofit retrofit  = new Retrofit.Builder().addConverterFactory(FastJsonConverterFactory.create())
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        cookieService = retrofit.create(CookieService.class);
    }
}
