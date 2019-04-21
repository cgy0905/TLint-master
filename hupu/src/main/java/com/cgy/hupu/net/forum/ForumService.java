package com.cgy.hupu.net.forum;

import com.cgy.hupu.bean.BaseData;
import com.cgy.hupu.bean.MessageData;
import com.cgy.hupu.bean.ThreadListData;

import java.util.Map;

import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by cgy on 2018/10/10  15:57
 */
public interface ForumService {

    @GET("user/getUserMessageList")
    Observable<MessageData> getMessageList(@Query("sign") String sign, @QueryMap Map<String, String> params);

    @GET("recommend/getThreadsList")
    Observable<ThreadListData> getRecommendThreadList(@Query("sign") String sign, @QueryMap Map<String, String> params);

    @GET("forums/getForumsInfoList")
    Observable<ThreadListData> getThreadsList(@Query("sign") String sign, @QueryMap Map<String, String> params);

    @POST("user/delUserMessage")
    @FormUrlEncoded
    Observable<BaseData> delMessage(@Field("sign") String sign, @FieldMap Map<String, String> params);
}
