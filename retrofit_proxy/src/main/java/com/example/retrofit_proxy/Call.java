package com.example.retrofit_proxy;


import java.io.IOException;


/**
 * Created by cgy on 2018/10/31.
 */
public interface Call extends Cloneable{
    /**
     * 同步请求
     * @return
     * @throws IOException
     */
    String execute() throws IOException;

    /**
     * 异步请求
     * @param callback
     */
    void enqueue(Callback callback);


}
