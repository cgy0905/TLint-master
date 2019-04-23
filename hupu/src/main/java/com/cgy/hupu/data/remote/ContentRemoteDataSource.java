package com.cgy.hupu.data.remote;

import com.cgy.hupu.R;
import com.cgy.hupu.bean.ThreadLightReplyData;
import com.cgy.hupu.bean.ThreadReplyData;
import com.cgy.hupu.data.ContentDataSource;
import com.cgy.hupu.db.ThreadInfo;
import com.cgy.hupu.db.ThreadReply;
import com.cgy.hupu.net.forum.ForumApi;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by cgy on 2019/4/23.
 */
public class ContentRemoteDataSource implements ContentDataSource {

    private final ForumApi mForumApi;

    @Inject
    public ContentRemoteDataSource(ForumApi forumApi) {
        this.mForumApi = forumApi;
    }

    @Override
    public Observable<ThreadInfo> getThreadInfo(String fid, String tid) {
        return mForumApi.getThreadInfo(tid, fid, 1, "")
                .onErrorReturn(throwable -> null)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<List<ThreadReply>> getReplies(String fid, String tid, int page) {
        return mForumApi.getThreadReplyList(tid, fid, page)
                .map(threadReplyData -> {
                    if (threadReplyData != null && threadReplyData.status == 200) {
                        return threadReplyData.result.list;
                    }
                    return null;
                })
                .onErrorReturn(throwable -> null)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<List<ThreadReply>> getLightReplies(String fid, String tid) {
        return mForumApi.getThreadLightReplyList(tid, fid)
                .map(threadLightReplyData -> {
                    if (threadLightReplyData != null && threadLightReplyData.status == 200) {
                        return threadLightReplyData.list;
                    }
                    return null;
                })
                .onErrorReturn(throwable -> null)
                .subscribeOn(Schedulers.io());
    }
}
