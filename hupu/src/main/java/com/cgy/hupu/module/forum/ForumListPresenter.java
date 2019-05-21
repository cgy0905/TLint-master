package com.cgy.hupu.module.forum;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.cgy.hupu.data.ForumRepository;
import com.cgy.hupu.db.Forum;
import com.cgy.hupu.injector.PerActivity;
import com.cgy.hupu.otto.DelForumAttentionEvent;
import com.cgy.hupu.service.OfflineService;
import com.cgy.hupu.utils.ToastUtil;
import com.squareup.otto.Bus;

import java.util.ArrayList;

import javax.inject.Inject;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * @author cgy
 * @desctiption
 * @date 2019/5/21 10:57
 */
@PerActivity
public class ForumListPresenter implements ForumListContract.Presenter{

    private final ForumRepository mForumRepository;
    private final Context mContext;
    private final Bus mBus;

    private ForumListContract.View mForumListView;
    private Subscription mSubscription;

    @Inject
    public ForumListPresenter(ForumRepository forumRepository, Context context, Bus bus) {
        this.mForumRepository = forumRepository;
        this.mContext = context;
        this.mBus = bus;
    }

    @Override
    public void onForumListReceive(String forumId) {
        mForumListView.showLoading();
        mSubscription = mForumRepository.getForumList(forumId).observeOn(AndroidSchedulers.mainThread())
                .subscribe(forums -> {
                    if (forums == null || forums.isEmpty()) {
                        mForumListView.onError();
                    } else {
                        mForumListView.hideLoading();
                        mForumListView.renderForumList(forums);
                    }
                }, throwable -> throwable.printStackTrace());
    }

    @Override
    public void onForumAttentionDelClick(Forum forum) {
        mForumRepository.removeForum(forum.getFid())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(aBoolean -> {
            if (aBoolean != null && aBoolean) {
                ToastUtil.showToast("取消关注成功");
                mBus.post(new DelForumAttentionEvent(forum.getFid()));
                mForumListView.removeForum(forum);
            }
        }, throwable -> ToastUtil.showToast("取消关注失败，请重试"));
    }

    @Override
    public void onForumOfflineClick(Forum forum) {
        Intent intent = new Intent(mContext, OfflineService.class);
        ArrayList<Forum> forums = new ArrayList<>();
        forums.add(forum);
        intent.putExtra(OfflineService.EXTRA_FORUMS, forums);
        intent.setAction(OfflineService.START_DOWNLOAD);
        mContext.startService(intent);
    }

    @Override
    public void onForumClick(Forum forum) {
        mForumListView.showThreadUi(forum.getFid());
    }

    @Override
    public void attachView(@NonNull ForumListContract.View view) {
        mForumListView = view;
    }

    @Override
    public void detachView() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
        mForumListView = null;
    }
}
