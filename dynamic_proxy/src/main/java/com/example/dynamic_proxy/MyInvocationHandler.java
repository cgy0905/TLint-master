package com.example.dynamic_proxy;

import android.util.Log;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by cgy on 2018/10/29 0029.
 */
public class MyInvocationHandler implements InvocationHandler {

    private static final String url = "http://www.baidu.com";
    private Object target;

    public MyInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {


        Log.d("MyInvocationHandler", "获取数据前");
        Object result = method.invoke(target, args);
        if (method.getName().equals("getBaiduPage")) {
            OkHttpClient okHttpClient = new OkHttpClient();
            final Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .build();
            Call call = okHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.d("MyInvocationHandler", "onFailure: " + e.getLocalizedMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    Log.d("MyInvocationHandler", "onResponse :" + response.body().string());


                }
            });
        }
        Log.d("MyInvocationHandler", "获取数据后");

        return result;
    }
}
