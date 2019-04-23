package com.cgy.hupu.module.content;

import com.cgy.hupu.module.BasePresenter;
import com.cgy.hupu.module.BaseView;

/**
 * Created by cgy on 2019/4/22.
 */
public interface ContentPagerContract {

    interface View extends BaseView {

        void showLoading();

        void hideLoading();

        void onError();

        void sendMessageToJS(String handlerName, Object object);

        void loadUrl(String url);

        void showReplyUi(String fid, String tid, String pid, String title);

        void showReportUi(String tid, String pid);

        void showBrowserUi(String url);

        void showContentUi(String tid, String pid, int page);

        void showThreadListUi(String fid);

        void showUserProfileUi(String uid);

        void showLoginUi();

        void onClose();
    }

    interface Presenter extends BasePresenter<View> {

        void onThreadInfoReceive(String tid, String fid, String pid, int page);

        void onReply(int area, int index);

        void onReport(int area, int index);

        void addLight(int area, int index);

        void addRuLight(int area, int index);

        void handlerUrl(String url);

        void onReLoad();

        ContentPagerPresenter.HupuBridge getJavaScriptInterface();
    }
}
