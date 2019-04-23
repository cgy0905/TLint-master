package com.cgy.hupu.net.forum;

import com.cgy.hupu.bean.BaseData;
import com.cgy.hupu.bean.CollectData;
import com.cgy.hupu.bean.MessageData;
import com.cgy.hupu.bean.ThreadLightReplyData;
import com.cgy.hupu.bean.ThreadListData;
import com.cgy.hupu.bean.ThreadReplyData;
import com.cgy.hupu.bean.ThreadSchemaInfo;
import com.cgy.hupu.db.ThreadInfo;
import com.cgy.hupu.db.ThreadReply;

import java.util.List;
import java.util.Map;

import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
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

    @GET("threads/getThreadsSchemaInfo")
    Observable<ThreadSchemaInfo> getThreadSchemaInfo(@Query("sign") String sign, @QueryMap Map<String, String> params);

    @FormUrlEncoded
    @POST("threads/threadCollectAdd")
    Observable<CollectData> addCollect(@Field("sign") String sign, @FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("threads/threadCollectRemove")
    Observable<CollectData> delCollect(@Field("sign") String sign, @FieldMap Map<String, String> params);

    @GET("threads/getThreadInfo")
    @Headers("Referer:http://bbs.mobileapi.hupu.com/1/7.0.8/threads/getThreadDetailInfoH5")
    Observable<ThreadInfo> getThreadInfo(@QueryMap Map<String, String> params);

    @GET("threads/getThreadPostList")
    @Headers("Referer:http://bbs.mobileapi.hupu.com/1/7.0.8/threads/getThreadDetailInfoH5")
    Observable<ThreadReplyData> getThreadReplyList(@QueryMap Map<String, String> params);

    @GET("threads/getThreadLightReplyList")
    @Headers("Referer:http://bbs.mobileapi.hupu.com/1/7.0.8/threads/getThreadDetailInfoH5")
    Observable<ThreadLightReplyData> getThreadLightReplyList(@QueryMap Map<String, String> params);

    @POST("threads/replyLight")
    @FormUrlEncoded
    @Headers("Referer:http://bbs.mobileapi.hupu.com/1/7.0.8/threads/getThreadDetailInfoH5")
    Observable<BaseData> addLight(@FieldMap Map<String, String> params);

    @POST("threads/replyUnlight")
    @FormUrlEncoded
    @Headers("Referer:http://bbs.mobileapi.hupu.com/1/7.0.8/threads/getThreadDetailInfoH5")
    Observable<BaseData> addRuLight(@FieldMap Map<String, String> params);
}
