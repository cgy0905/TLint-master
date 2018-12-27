package com.cgy.hupu.net.game;

import com.cgy.hupu.bean.PmData;

import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by cgy on 2018/10/11  9:56
 */
public interface GameService {

    @FormUrlEncoded
    @POST("pm/list")
    Observable<PmData> queryPmList(@FieldMap Map<String, String> params, @Query("client") String client);
}
