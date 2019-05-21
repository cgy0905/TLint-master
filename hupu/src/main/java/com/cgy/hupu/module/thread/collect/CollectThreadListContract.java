package com.cgy.hupu.module.thread.collect;

import com.cgy.hupu.db.Thread;
import com.cgy.hupu.module.BasePresenter;
import com.cgy.hupu.module.BaseView;

import java.util.List;

/**
 * @author cgy
 * @desctiption
 * @date 2019/5/21 10:03
 */
public interface CollectThreadListContract {
    interface View extends BaseView {

        void showLoading();

        void hideLoading();

        void renderThreads(List<Thread> threads);

        void onError(String error);

        void onEmpty();

        void onLoadCompleted(boolean hasMore);

        void onRefreshCompleted();
    }

    interface Presenter extends BasePresenter<View> {
        void onThreadReceive();

        void onRefresh();

        void onReload();

        void onLoadMore();
    }
}
