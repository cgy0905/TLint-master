package com.cgy.hupu.module.report;

import android.support.annotation.NonNull;

import javax.inject.Inject;

/**
 * Created by cgy on 2019/4/25.
 */
public class ReportPresenter  implements ReportContract.Presenter{

    @Inject
    public ReportPresenter() {

    }

    @Override
    public void submitReports(String tid, String pid, String type, String content) {

    }

    @Override
    public void attachView(@NonNull ReportContract.View view) {

    }

    @Override
    public void detachView() {

    }
}
