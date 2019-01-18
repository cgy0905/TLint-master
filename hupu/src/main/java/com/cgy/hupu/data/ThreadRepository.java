package com.cgy.hupu.data;

import android.text.TextUtils;

import com.cgy.hupu.Constants;
import com.cgy.hupu.bean.ThreadListData;
import com.cgy.hupu.db.Thread;
import com.cgy.hupu.db.ThreadDao;
import com.cgy.hupu.net.forum.ForumApi;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

/**
 * Created by cgy on 2018/12/31 下午 4:19.
 */
public class ThreadRepository {

    private ThreadDao mThreadDao;
    private ForumApi mForumApi;

    @Inject
    public ThreadRepository(ThreadDao mThreadDao, ForumApi mForumApi) {
        this.mThreadDao = mThreadDao;
        this.mForumApi = mForumApi;
    }

    public Observable<List<Thread>> getThreadListObservable(final int type, PublishSubject<List<Thread>> mSubject) {
        Observable<List<Thread>> firstObservable = Observable.fromCallable((Func0<List<Thread>>) () -> mThreadDao.queryBuilder().where(ThreadDao.Properties.Type.eq(type)).list());
        return firstObservable.concatWith(mSubject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<ThreadListData> getRecommendThreadList(final String lastTid, String lastTamp,
                                                             final PublishSubject<List<Thread>> mSubject) {
        return mForumApi.getRecommendThreadList(lastTid, lastTamp)
                .doOnNext(threadListData -> {
                    if (threadListData != null && threadListData.result != null) {
                        ThreadRepository.this.cacheThreadList(0, TextUtils.isEmpty(lastTid), threadListData.result.data);
                    }
                    if (mSubject != null) {
                        mSubject.onNext(mThreadDao.queryBuilder()
                                .where(ThreadDao.Properties.Type.eq(Constants.TYPE_RECOMMEND))
                                .list());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<ThreadListData> getThreadsList(final String fid, final String lastTid,
                                                     String lastTamp, String type, final PublishSubject<List<Thread>> mSubject) {
        return mForumApi.getThreadsList(fid, lastTid, lastTamp, type)
                .doOnNext(new Action1<ThreadListData>() {
                    @Override
                    public void call(ThreadListData threadListData) {
                        if (threadListData != null && threadListData.result != null) {
                            cacheThreadList(Integer.valueOf(fid), TextUtils.isEmpty(lastTid),
                                    threadListData.result.data);
                        }
                        if (mSubject != null) {
                            mSubject.onNext(mThreadDao.queryBuilder().where(ThreadDao.Properties.Type.eq(Integer.valueOf(fid)))
                                    .list());
                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    private void cacheThreadList(int type, boolean clear, List<Thread> threads) {
        if (clear) {
            mThreadDao.queryBuilder()
                    .where(ThreadDao.Properties.Type.eq(type))
                    .buildDelete()
                    .executeDeleteWithoutDetachingEntities();
        }
        for (Thread thread: threads) {
            if (mThreadDao.queryBuilder()
                    .where(ThreadDao.Properties.Tid.eq(thread.getTid()), ThreadDao.Properties.Type.eq(type))
                    .count() == 0) {
                thread.setType(type);
                mThreadDao.insert(thread);
            }
        }
    }
}
