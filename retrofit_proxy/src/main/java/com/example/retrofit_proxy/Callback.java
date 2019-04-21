package com.example.retrofit_proxy;

/**
 * Created by cgy on 2018/10/31.
 */
public interface Callback {

    void onResponse(String response);

    void onFailure(Throwable t);
}
