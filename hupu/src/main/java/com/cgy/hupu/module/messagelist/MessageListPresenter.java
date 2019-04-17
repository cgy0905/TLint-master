package com.cgy.hupu.module.messagelist;

import android.support.annotation.NonNull;

import com.cgy.hupu.bean.Message;
import com.cgy.hupu.bean.MessageData;
import com.cgy.hupu.net.forum.ForumApi;
import com.cgy.hupu.utils.ToastUtil;
import com.squareup.otto.Bus;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by cgy on 2019/4/17.
 */
public class MessageListPresenter implements MessageListContract.Presenter{

    private ForumApi mForumApi;
    private Bus mBus;

    private Subscription mSubscription;
    private MessageListContract.View mMessageListView;
    private String lastTid = "";
    private int page = 1;

    private List<Message> messages = new ArrayList<>();


    public MessageListPresenter(ForumApi forumApi, Bus bus) {
        this.mForumApi = forumApi;
        this.mBus = bus;
    }
    @Override
    public void onMessageListReceive() {
        mMessageListView.showLoading();
        loadMessageList(true);

    }

    private void loadMessageList(boolean clear) {
        mSubscription = mForumApi.getMessageList(lastTid, page)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(messageData -> {
                    if (clear) {
                        messages.clear();
                    }
                })
                .map(messageData -> {
                    if (messageData != null && messageData.status == 200) {
                        return addMessages(messageData.result.list);
                    }
                    return null;
                })
                .subscribe(messages -> {
                    if (messages != null) {
                        if (messages.isEmpty()) {
                            mMessageListView.onEmpty();
                        } else {
                            mMessageListView.hideLoading();
                            mMessageListView.onRefreshCompleted();
                            mMessageListView.onLoadCompleted(true);
                            mMessageListView.renderMessageList(messages);
                        }
                    } else {
                        loadError();
                    }
                }, throwable -> loadError());
    }

    private List<Message> addMessages(List<Message> threadList) {
        for (Message thread: threadList) {
            if (!messages.contains(thread)) {
                messages.add(thread);
            }
        }
        if (!messages.isEmpty()) {
            lastTid = messages.get(messages.size() - 1).tid;
        }
        return messages;
    }

    private boolean contains(Message message) {
        boolean issContain = false;
        for (Message message1: messages) {
            if (message.tid.equals(message1.tid)) {
                issContain = true;
                break;
            }
        }
        return issContain;
    }

    private void loadError() {
        if (messages.isEmpty()) {
            mMessageListView.onError();
        } else {
            ToastUtil.showToast("数据加载失败，请重试");
            mMessageListView.hideLoading();
            mMessageListView.onRefreshCompleted();
            mMessageListView.onLoadCompleted(true);
        }
    }

    @Override
    public void onRefresh() {
        lastTid = "";
        page = 1;
        onMessageListReceive();
    }

    @Override
    public void onReload() {

    }

    @Override
    public void onLoadMore() {
        page++;
        loadMessageList(false);
    }

    @Override
    public void onMessageClick(Message message) {
        mMessageListView.showContentUi(message.tid, message.pid, Integer.valueOf(message.page));
        mForumApi.delMessage(message.id)
    }

    @Override
    public void attachView(@NonNull MessageListContract.View view) {

    }

    @Override
    public void detachView() {

    }
}
