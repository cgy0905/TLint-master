package com.cgy.hupu.data;

import com.cgy.hupu.db.ThreadInfo;
import com.cgy.hupu.db.ThreadReply;

import java.util.List;

import rx.Observable;

/**
 * Created by cgy on 2019/4/23.
 * 帖子详情页数据源
 *
 */
public interface ContentDataSource {

    Observable<ThreadInfo> getThreadInfo(String fid, String tid);

    Observable<List<ThreadReply>> getReplies(String fid, String tid, int page);

    Observable<List<ThreadReply>> getLightReplies(String fid, String tid);
}
