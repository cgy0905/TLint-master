package com.cgy.hupu.module.report;

import com.cgy.hupu.module.BasePresenter;
import com.cgy.hupu.module.BaseView;

/**
 * Created by cgy on 2019/4/25.
 */
public interface ReportContract {

    interface View extends BaseView {

        void showLoading();

        void hideLoading();

        void reportSuccess();
    }

    interface Presenter extends BasePresenter<View> {
        void submitReports(String tid, String pid, String type, String content);
    }
}
