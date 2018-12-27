package com.cgy.hupu.net.game;

import android.text.TextUtils;

import com.cgy.hupu.bean.PmData;
import com.cgy.hupu.components.retrofit.FastJsonConverterFactory;
import com.cgy.hupu.components.retrofit.RequestHelper;

import java.util.Map;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by cgy on 2018/10/11  9:50
 */
public class GameApi {

    static final String BASE_URL = "http://games.mobileapi.hupu.com/1/7.0.8/";

    private GameService mGameService;
    private RequestHelper mRequestHelper;

    public GameApi(RequestHelper requestHelper, OkHttpClient okHttpClient) {
        this.mRequestHelper = requestHelper;

        Retrofit retrofit = new Retrofit.Builder().addConverterFactory(FastJsonConverterFactory.create())
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        mGameService = retrofit.create(GameService.class);
    }

    public Observable<PmData> queryPmList(String lastTime) {
        Map<String, String> params = mRequestHelper.getHttpRequestMap();
        if (!TextUtils.isEmpty(lastTime)) {
            params.put("last_time", lastTime);
        }
        String sign = mRequestHelper.getRequestSign(params);
        params.put("sign", sign);
        return mGameService.queryPmList(params, mRequestHelper.getDeviceId())
                .subscribeOn(Schedulers.io());
    }
}
