package com.example.dynamic_proxy;

import java.lang.reflect.Proxy;

import okhttp3.OkHttpClient;

/**
 * Created by cgy on 2018/10/30.
 */
public class MyRetrofit {

    private static MyRetrofit myRetrofit;
    private MyInvocationHandler myInvocationHandler;

    public static MyRetrofit getInstance() {
        if (myRetrofit == null) {
            myRetrofit = new MyRetrofit();
        }
        return myRetrofit;
    }

    public MyRetrofit init(String baseUrl, OkHttpClient okHttpClient) {
        myInvocationHandler = new MyInvocationHandler(baseUrl, okHttpClient);
        return this;
    }

    public <T> T create(Class<T> service) {
        return (T) Proxy.newProxyInstance(service.getClassLoader(),
                new Class[]{service},
                myInvocationHandler);
    }


}
