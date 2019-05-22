package com.cgy.hupu.module.report;

import android.support.annotation.NonNull;

import com.cgy.hupu.bean.BaseData;
import com.cgy.hupu.injector.PerActivity;
import com.cgy.hupu.net.forum.ForumApi;
import com.cgy.hupu.utils.ToastUtil;

import javax.inject.Inject;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by cgy on 2019/4/25.
 */
@PerActivity
public class ReportPresenter  implements ReportContract.Presenter{

    private ForumApi mForumApi;

    @Inject
    public ReportPresenter(ForumApi forumApi) {
        mForumApi = forumApi;
    }

    private Subscription mSubscription;
    private  ReportContract.View mReportView;

    @Override
    public void submitReports(String tid, String pid, String type, String content) {
        mReportView.showLoading();
        mSubscription = mForumApi.submitReports(tid, pid, type, content)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    mReportView.hideLoading();
                    if (result.status == 200) {
                        mReportView.reportSuccess();
                        ToastUtil.showToast("举报成功~");
                    } else {
                        ToastUtil.showToast("举报失败，请检查网络后重试");
                    }
                }, throwable -> {
                    mReportView.hideLoading();
                    ToastUtil.showToast("举报失败，请检查网络后重试");
                });

    }

    @Override
    public void attachView(@NonNull ReportContract.View view) {
        mReportView = view;
    }

    @Override
    public void detachView() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
        mReportView = null;
    }
}
