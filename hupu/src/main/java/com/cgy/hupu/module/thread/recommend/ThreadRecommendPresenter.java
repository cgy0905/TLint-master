package com.cgy.hupu.module.thread.recommend;

import android.support.annotation.NonNull;

import com.cgy.hupu.Constants;
import com.cgy.hupu.bean.ThreadListResult;
import com.cgy.hupu.data.ThreadRepository;
import com.cgy.hupu.db.Thread;
import com.cgy.hupu.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Subscription;
import rx.subjects.PublishSubject;

/**
 * Created by cgy on 2018/12/31 下午 4:13.
 */
public class ThreadRecommendPresenter implements RecommendThreadListContract.Presenter{

    private ThreadRepository mThreadRepository;

    private PublishSubject<List<Thread>> mThreadSubject;
    private boolean isFirst = true;
    private List<Thread> threads = new ArrayList<>();

    private Subscription mSubscription;

    private RecommendThreadListContract.View mRecommendView;
    private String lastTid = "";
    private String lastTamp = "";
    private boolean hasNextPage = true;

    @Inject
    public ThreadRecommendPresenter(ThreadRepository mThreadRepository) {
        this.mThreadRepository = mThreadRepository;
        mThreadSubject = PublishSubject.create();
    }
    @Override
    public void onThreadReceive() {
        mRecommendView.showLoading();
        mThreadRepository.getThreadListObservable(Constants.TYPE_RECOMMEND, mThreadSubject)
                .subscribe(threads -> {
                    ThreadRecommendPresenter.this.threads = threads;
                    if (threads.isEmpty()) {
                        if (!isFirst) {
                            mRecommendView.onError("数据加载失败");
                        }
                        isFirst = false;
                    } else {
                        mRecommendView.showContent();
                        if (!threads.isEmpty()) {
                            lastTid = threads.get(threads.size()  - 1).getTid();
                        }
                        mRecommendView.renderThreads(threads);
                    }
                });
        loadRecommendList();
    }

    private void loadRecommendList() {
        mSubscription = mThreadRepository.getRecommendThreadList(lastTid, lastTamp, mThreadSubject)
                .subscribe(threadListData -> {
                    if (threadListData != null && threadListData.result != null) {
                        ThreadListResult data = threadListData.result;
                        lastTamp = data.stamp;
                        hasNextPage = data.nextPage;
                    }
                    mRecommendView.onRefreshCompleted();
                    mRecommendView.onLoadCompleted(hasNextPage);
                }, throwable -> {
                    if (threads.isEmpty()) {
                        mRecommendView.onError("数据加载失败，请重试");
                    } else {
                        mRecommendView.onRefreshCompleted();
                        mRecommendView.onLoadCompleted(hasNextPage);
                        ToastUtil.showToast("数据加载失败，请重试");
                    }
                });
    }

    @Override
    public void onRefresh() {
        lastTamp = "";
        lastTid = "";
        loadRecommendList();
    }

    @Override
    public void onReload() {
        onThreadReceive();
    }

    @Override
    public void onLoadMore() {
        if (!hasNextPage) {
            ToastUtil.showToast("没有更多了~");
            mRecommendView.onLoadCompleted(false);
            return;
        }
        loadRecommendList();
    }

    @Override
    public void attachView(@NonNull RecommendThreadListContract.View view) {
        mRecommendView  = view;
    }

    @Override
    public void detachView() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
        mRecommendView = null;
    }
}
