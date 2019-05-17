package com.cgy.hupu.module.thread.list;

import com.cgy.hupu.db.Forum;
import com.cgy.hupu.db.Thread;
import com.cgy.hupu.module.BasePresenter;
import com.cgy.hupu.module.BaseView;

import java.util.List;

/**
 * @author cgy
 * @description
 * @date 2019/5/16 17:57
 */
public interface ThreadListContract {

    interface View extends BaseView {
        void showLoading();

        void showProgress();

        void showContent();

        void renderThreadInfo(Forum forum);

        void renderThreads(List<Thread> threads);

        void onLoadCompleted(boolean hasMore);

        void onRefreshCompleted();

        void attendStatus(boolean isAttention);

        void onError(String error);

        void onEmpty();

        void onScrollToTop();

        void onFloatingVisibility(int visibility);

        void showPostThreadUi(String fid);

        void showLoginUi();

        void showToast(String msg);
    }

    interface Presenter extends BasePresenter<View> {

        void onThreadReceive(String type);

        void onStartSearch(String key, int page);

        void onAttentionClick();

        void onPostClick();

        void onRefresh();

        void onReload();

        void onLoadMore();
    }
}
