package com.cgy.hupu.data;

import android.text.TextUtils;

import com.cgy.hupu.bean.ThreadReplyQuote;
import com.cgy.hupu.data.local.ContentLocalDataSource;
import com.cgy.hupu.data.remote.ContentRemoteDataSource;
import com.cgy.hupu.db.ThreadInfo;
import com.cgy.hupu.db.ThreadReply;
import com.cgy.hupu.utils.HtmlUtil;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by cgy on 2019/4/23.
 */
public class ContentRepository implements ContentDataSource{

    private final ContentRemoteDataSource mContentRemoteDataSource;
    private final ContentLocalDataSource mContentLocalDataSource;

    @Inject
    public ContentRepository(ContentRemoteDataSource contentRemoteDataSource, ContentLocalDataSource contentLocalDataSource) {
        mContentRemoteDataSource = contentRemoteDataSource;
        mContentLocalDataSource = contentLocalDataSource;
    }

    @Override
    public Observable<ThreadInfo> getThreadInfo(String fid, String tid) {
        Observable<ThreadInfo> remote = mContentRemoteDataSource.getThreadInfo(fid, tid);
        Observable<ThreadInfo> local = mContentLocalDataSource.getThreadInfo(fid, tid);

        Observable<ThreadInfo> remoteWithLocalUpdate = remote.doOnNext(new Action1<ThreadInfo>() {
            @Override
            public void call(ThreadInfo threadInfo) {
                if (threadInfo != null && threadInfo.getError() == null) {
                    threadInfo.setForumName(threadInfo.getForum().getName());
                    mContentLocalDataSource.saveThreadInfo(threadInfo);
                }
            }
        });
        return Observable.concat(remoteWithLocalUpdate, local).first(new Func1<ThreadInfo, Boolean>() {
            @Override
            public Boolean call(ThreadInfo threadInfo) {
                return threadInfo != null;
            }
        });
    }

    @Override
    public Observable<List<ThreadReply>> getReplies(String fid, String tid, int page) {
        Observable<List<ThreadReply>> remote = mContentRemoteDataSource.getReplies(fid, tid, page);
        Observable<List<ThreadReply>> local = mContentLocalDataSource.getReplies(fid, tid, page);

        Observable<List<ThreadReply>> remoteWithLocalUpdate =
                remote.doOnNext(threadReplies -> {
                    if (threadReplies != null) {
                        for (ThreadReply reply: threadReplies) {
                            reply.setTid(tid);
                            reply.setIsLight(false);
                            reply.setPageIndex(page);
                            saveReply(reply);
                        }
                    }
                });
        return Observable.concat(remoteWithLocalUpdate, local)
                .first(threadReplies -> threadReplies != null);
    }

    @Override
    public Observable<List<ThreadReply>> getLightReplies(String fid, String tid) {
        Observable<List<ThreadReply>> remote = mContentRemoteDataSource.getLightReplies(fid, tid);
        Observable<List<ThreadReply>> local = mContentLocalDataSource.getLightReplies(fid, tid);

        Observable<List<ThreadReply>> remoteWithLocalUpdate =
                remote.doOnNext(threadReplies -> {
                    if (threadReplies != null) {
                        for (ThreadReply reply : threadReplies) {
                            reply.setTid(tid);
                            reply.setIsLight(true);
                            saveReply(reply);
                        }
                    }
                });
        return Observable.concat(remoteWithLocalUpdate, local)
                .first(threadReplies -> threadReplies != null);
    }

    private void saveReply(ThreadReply reply) {
        if (!reply.getQuote().isEmpty()) {
            ThreadReplyQuote quote = reply.getQuote().get(0);
            reply.setQuoteHeader(quote.header.get(0));
            if (!TextUtils.isEmpty(quote.togglecontent)){
                reply.setQuoteToggle(quote.togglecontent);
            }
            reply.setQuoteContent(HtmlUtil.transImgToLocal(quote.content));
        }
        mContentLocalDataSource.saveThreadReply(reply);
    }

}
