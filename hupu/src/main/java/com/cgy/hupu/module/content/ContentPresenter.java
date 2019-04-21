package com.cgy.hupu.module.content;

import android.content.Context;
import android.support.annotation.NonNull;

import com.cgy.hupu.components.UserStorage;
import com.cgy.hupu.injector.PerActivity;
import com.cgy.hupu.net.forum.ForumApi;
import com.squareup.otto.Bus;

import javax.inject.Inject;

import rx.Subscription;

/**
 * Created by cgy on 2019/4/19.
 */
@PerActivity
public class ContentPresenter implements ContentContract.Presenter{
    private ForumApi mForumApi;
    private Context mContext;
    private UserStorage mUserStorage;
    private Bus mBus;

    private ContentContract.View mContentView;
    private Subscription mSubscription;
    private String tid;
    private
    @Inject
    public ContentPresenter(){

    }

    @Override
    public void onThreadInfoReceive(String tid, String fid, String pid, int page) {

    }

    @Override
    public void onReload() {

    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onPageNext() {

    }

    @Override
    public void onPagePre() {

    }

    @Override
    public void onPageSelected(int page) {

    }

    @Override
    public void onShareClick() {

    }

    @Override
    public void onReportClick() {

    }

    @Override
    public void onCollectClick() {

    }

    @Override
    public void updatePage(int page) {

    }

    @Override
    public void attachView(@NonNull ContentContract.View view) {

    }

    @Override
    public void detachView() {

    }
}
