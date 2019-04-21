package com.example.dynamic_proxy;

import org.json.JSONObject;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by cgy on 2018/10/30.
 */
public class MyInvocationHandler implements InvocationHandler {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private String baseUrl;
    private OkHttpClient okHttpClient;

    public MyInvocationHandler(String baseUrl, OkHttpClient okHttpClient) {
        this.baseUrl = baseUrl;
        this.okHttpClient = okHttpClient;

    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        /*POST+Json*/
//        JSONObject json = new JSONObject();
//        String url = (String) args[0];
//        Map<String, String> map = (Map<String, String>) args[1];
//        Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
//        while (it.hasNext()) {
//            Map.Entry<String, String> entry = it.next();
//            json.put(entry.getKey(), entry.getValue());
//        }

        Request request = new Request.Builder()
                .url(baseUrl)
                .get()
                .build();
        return okHttpClient.newCall(request);
    }
}
