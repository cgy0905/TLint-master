package com.cgy.hupu.data.local;

import com.cgy.hupu.data.ContentDataSource;
import com.cgy.hupu.db.ThreadInfo;
import com.cgy.hupu.db.ThreadInfoDao;
import com.cgy.hupu.db.ThreadReply;
import com.cgy.hupu.db.ThreadReplyDao;
import com.cgy.hupu.utils.HtmlUtil;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * Created by cgy on 2019/4/23.
 */
public class ContentLocalDataSource implements ContentDataSource {

    private final ThreadInfoDao mThreadInfoDao;
    private final ThreadReplyDao mThreadReplyDao;

    @Inject
    public ContentLocalDataSource(ThreadInfoDao threadInfoDao, ThreadReplyDao threadReplyDao) {
        mThreadInfoDao = threadInfoDao;
        mThreadReplyDao = threadReplyDao;
    }

    @Override
    public Observable<ThreadInfo> getThreadInfo(String fid, String tid) {
        return Observable.create((Observable.OnSubscribe<ThreadInfo>) subscriber -> {
            List<ThreadInfo> threadInfos = mThreadInfoDao.queryBuilder().where(ThreadInfoDao.Properties.Tid.eq(tid)).list();
            if (!threadInfos.isEmpty()) {
                ThreadInfo threadInfo = threadInfos.get(0);
                String content = threadInfo.getContent();
                threadInfo.setContent(HtmlUtil.transImgToLocal(content));
                subscriber.onNext(threadInfo);
            } else {
                subscriber.onNext(null);
            }
            subscriber.onCompleted();
        }).subscribeOn(Schedulers.io());
    }
    public void saveThreadInfo(ThreadInfo threadInfo) {
        mThreadInfoDao.queryBuilder()
                .where(ThreadInfoDao.Properties.Tid.eq(threadInfo.getTid()))
                .buildDelete()
                .executeDeleteWithoutDetachingEntities();
        mThreadInfoDao.insert(threadInfo);
    }
    @Override
    public Observable<List<ThreadReply>> getReplies(String fid, String tid, int page) {
        return Observable.create((Observable.OnSubscribe<List<ThreadReply>>) subscriber -> {
            List<ThreadReply> replies = mThreadReplyDao.queryBuilder()
                    .where(ThreadReplyDao.Properties.Tid.eq(tid),
                            ThreadReplyDao.Properties.IsLight.eq(false),
                            ThreadReplyDao.Properties.PageIndex.eq(page))
                    .orderAsc(ThreadReplyDao.Properties.Floor)
                    .list();
            if (replies.isEmpty() && page > 1) {
                subscriber.onNext(null);
            } else {
                subscriber.onNext(replies);
            }
            subscriber.onCompleted();
        }).subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<List<ThreadReply>> getLightReplies(String fid, String tid) {
        return Observable.create((Observable.OnSubscribe<List<ThreadReply>>) subscriber -> {
            List<ThreadReply> replies  = mThreadReplyDao.queryBuilder()
                    .where(ThreadReplyDao.Properties.Tid.eq(tid),
                            ThreadReplyDao.Properties.IsLight.eq(true))
                    .orderDesc(ThreadReplyDao.Properties.Light_count)
                    .list();
            subscriber.onNext(replies);
            subscriber.onCompleted();
        }).subscribeOn(Schedulers.io());
    }

    public void saveThreadReply(ThreadReply reply) {
        mThreadReplyDao.queryBuilder()
                .where(ThreadReplyDao.Properties.Pid.eq(reply.getPid()),
                        ThreadReplyDao.Properties.IsLight.eq(reply.getIsLight()))
                .buildDelete()
                .executeDeleteWithoutDetachingEntities();
        mThreadReplyDao.insert(reply);
    }
}
