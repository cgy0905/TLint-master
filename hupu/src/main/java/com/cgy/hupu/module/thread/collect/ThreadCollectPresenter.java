package com.cgy.hupu.module.thread.collect;

import android.support.annotation.NonNull;

import com.cgy.hupu.bean.ThreadListData;
import com.cgy.hupu.bean.ThreadListResult;
import com.cgy.hupu.db.Thread;
import com.cgy.hupu.injector.PerActivity;
import com.cgy.hupu.net.game.GameApi;
import com.cgy.hupu.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * @author cgy
 * @desctiption 收藏帖子的Presenter
 * @date 2019/5/21 10:13
 */
@PerActivity
public class ThreadCollectPresenter implements CollectThreadListContract.Presenter {

    private GameApi mGameApi;

    private CollectThreadListContract.View mSpecialView;
    private Subscription mSubscription;
    private List<Thread> threads = new ArrayList<>();
    private int page = 1;
    private boolean hasNextPage = true;

    @Inject
    public ThreadCollectPresenter(GameApi gameApi) {
        mGameApi = gameApi;
    }
    @Override
    public void onThreadReceive() {
        mSpecialView.showLoading();
        loadCollectList(page);
    }

    private void loadCollectList(int page) {
        this.page = page;
        mSubscription = mGameApi.getCollectList(page).map(result -> {
            if (page == 1) {
                threads.clear();
            }
            if (result != null && result.result != null) {
                ThreadListResult data = result.result;
                hasNextPage = data.nextDataExists == 1;
                threads.addAll(data.data);
                return threads;
            }
            return null;
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(threads -> {
                    if (threads != null) {
                        if (threads.isEmpty()) {
                            mSpecialView.onEmpty();
                        } else {
                            mSpecialView.onLoadCompleted(hasNextPage);
                            mSpecialView.onRefreshCompleted();
                            mSpecialView.hideLoading();
                            mSpecialView.renderThreads(threads);
                        }
                    } else {
                        loadThreadError();
                    }
                }, throwable -> loadThreadError());
    }

    private void loadThreadError() {
        if (threads.isEmpty()) {
            mSpecialView.onError("数据加载失败");
        } else {
            mSpecialView.hideLoading();
            mSpecialView.onLoadCompleted(true);
            mSpecialView.onRefreshCompleted();
            ToastUtil.showToast("数据加载失败");
        }
    }

    @Override
    public void onRefresh() {
        page = 1;
        loadCollectList(page);
    }

    @Override
    public void onReload() {
        mSpecialView.showLoading();
        loadCollectList(page);
    }

    @Override
    public void onLoadMore() {
        if (!hasNextPage) {
            ToastUtil.showToast("没有更多了~");
            mSpecialView.onLoadCompleted(false);
            return;
        }
        loadCollectList(++page);
    }

    @Override
    public void attachView(@NonNull CollectThreadListContract.View view) {
        mSpecialView = view;
    }

    @Override
    public void detachView() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
        mSpecialView = null;
    }
}
