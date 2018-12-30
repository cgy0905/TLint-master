package com.cgy.hupu.module.thread.recommend;

import com.cgy.hupu.module.BasePresenter;
import com.cgy.hupu.module.BaseView;

import java.util.List;

/**
 * Created by cgy on 2018/12/30 下午 5:04.
 */
public interface RecommendThreadListContract {

    interface View extends BaseView {
        void showLoading();

        void showContent();

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
