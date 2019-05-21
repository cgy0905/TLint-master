package com.cgy.hupu.net.game;

import com.cgy.hupu.bean.LoginData;
import com.cgy.hupu.bean.PmData;
import com.cgy.hupu.bean.SearchData;
import com.cgy.hupu.bean.ThreadListData;
import com.cgy.hupu.bean.UserData;

import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by cgy on 2018/10/11  9:56
 */
public interface GameService {

    @FormUrlEncoded
    @POST("user/loginUsernameEmail")
    Observable<LoginData> login(
            @FieldMap Map<String, String> params, @Query("client") String client);

    @FormUrlEncoded
    @POST("user/page")
    Observable<UserData> getUserInfo(@FieldMap  Map<String, String> params, @Query("client") String client);

    @FormUrlEncoded
    @POST("pm/list")
    Observable<PmData> queryPmList(@FieldMap Map<String, String> params, @Query("client") String client);

    @GET("search/list")
    Observable<SearchData> search(@QueryMap Map<String, String> params, @Query("client") String client);

    @GET("collect/getThreadsCollectList")
    Observable<ThreadListData> getCollectList(@Query("sign") String sign, @QueryMap Map<String, String> params);
}
