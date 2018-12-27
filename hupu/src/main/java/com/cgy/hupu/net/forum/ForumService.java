package com.cgy.hupu.net.forum;

import com.cgy.hupu.bean.MessageData;

import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by cgy on 2018/10/10  15:57
 */
public interface ForumService {

    @GET("user/getUserMessageList")
    Observable<MessageData> getMessageList(@Query("sign") String sign, @QueryMap Map<String, String> params);
}
