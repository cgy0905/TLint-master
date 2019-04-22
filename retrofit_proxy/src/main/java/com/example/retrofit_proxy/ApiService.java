package com.example.retrofit_proxy;

/**
 * Created by cgy on 2018/10/31.
 */
public interface ApiService {
    @GET("/appservice/cartAndBrand.action")
    OkHttpCall getCartAndBrand(@Field("v") String v);
}
