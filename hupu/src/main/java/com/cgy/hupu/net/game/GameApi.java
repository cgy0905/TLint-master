package com.cgy.hupu.net.game;

import android.text.TextUtils;

import com.cgy.hupu.bean.LoginData;
import com.cgy.hupu.bean.PmData;
import com.cgy.hupu.bean.SearchData;
import com.cgy.hupu.bean.ThreadListData;
import com.cgy.hupu.bean.UserData;
import com.cgy.hupu.components.retrofit.FastJsonConverterFactory;
import com.cgy.hupu.components.retrofit.RequestHelper;

import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.Subscription;
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

    /**
     * 登录
     * @param username  用户名
     * @param password  密码
     * @return
     */
    public Observable<LoginData> login(String username, String password) {
        HashMap<String, String> params = new HashMap<>();
        params.put("client", mRequestHelper.getDeviceId());
        params.put("username", username);
        params.put("password", password);
        String sign = mRequestHelper.getRequestSign(params);
        params.put("sign", sign);
        return mGameService.login(params, mRequestHelper.getDeviceId()).subscribeOn(Schedulers.io());
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

    /**
     * 获取用户相关信息
     * @param uid   用户id
     */
    public Observable<UserData> getUserInfo(String uid) {
        Map<String, String> params = mRequestHelper.getHttpRequestMap();
        params.put("puid", uid);
        String sign = mRequestHelper.getRequestSign(params);
        params.put("sign", sign);
        return mGameService.getUserInfo(params, mRequestHelper.getDeviceId());
    }

    /**
     * type暂时写死,只搜索论坛
     * @param key   搜索词
     * @param fid   论坛fid
     * @param page  页数
     * @return
     */
    public Observable<SearchData> search(String key, String fid, int page) {
        Map<String, String> params = mRequestHelper.getHttpRequestMap();
        params.put("keyword", key);
        params.put("type", "posts");
        params.put("fid", fid);
        params.put("page", String.valueOf(page));
        String sign = mRequestHelper.getRequestSign(params);
        params.put("sign", sign);
        return mGameService.search(params, mRequestHelper.getDeviceId()).subscribeOn(Schedulers.io());
    }

    /**
     * 获取收藏帖子
     * @param page  页数
     * @return
     */
    public Observable<ThreadListData> getCollectList(int page) {
        Map<String, String> params = mRequestHelper.getHttpRequestMap();
        params.put("page", String.valueOf(page));
        String sign = mRequestHelper.getRequestSign(params);
        return mGameService.getCollectList(sign, params).subscribeOn(Schedulers.io());
    }
}
