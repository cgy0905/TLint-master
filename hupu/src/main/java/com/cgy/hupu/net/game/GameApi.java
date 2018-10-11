package com.cgy.hupu.net.game;

import com.cgy.hupu.components.retrofit.FastJsonConverterFactory;
import com.cgy.hupu.components.retrofit.RequestHelper;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * Created by cgy on 2018/10/11  9:50
 */
public class GameApi {

    static final String BASE_URL = "http://games.mobileapi.hupu.com/1/7.0.8/";

    private GameService gameService;
    private RequestHelper requestHelper;

    public GameApi(RequestHelper requestHelper, OkHttpClient okHttpClient) {
        this.requestHelper = requestHelper;

        Retrofit retrofit = new Retrofit.Builder().addConverterFactory(FastJsonConverterFactory.create())
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        gameService = retrofit.create(GameService.class);
    }
}
