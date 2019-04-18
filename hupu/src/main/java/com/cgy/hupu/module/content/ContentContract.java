package com.cgy.hupu.module.content;

import com.cgy.hupu.module.BasePresenter;
import com.cgy.hupu.module.BaseView;

/**
 * Created by cgy on 2019/4/18.
 */
public interface ContentContract {

    interface View extends BaseView {
        void showLoading();

        void hideLoading();

        void renderContent(int page, int totalPage);

        void onUpdatePage(int page, int totalPage);

        void setCurrentItem(int index);

        void isCollected(boolean isCollected);

        void onError(String error);

        void onToggleFloatingMenu();

        void showLoginUi();

        void showReportUi();

        void showPostUi(String title);
    }

    interface Presenter extends BasePresenter<View> {
        void onThreadInfoReceive(String tid, String fid, String pid, int page);

        void onReload();

        void onRefresh();

        void onPageNext();

        void onPagePre();

        void onPageSelected(int page);

        void onShareClick();

        void onReportClick();

        void onCollectClick();

        void updatePage(int page);
    }
}
