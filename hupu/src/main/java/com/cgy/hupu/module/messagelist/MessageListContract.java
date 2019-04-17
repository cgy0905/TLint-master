package com.cgy.hupu.module.messagelist;

import com.cgy.hupu.bean.Message;
import com.cgy.hupu.module.BasePresenter;
import com.cgy.hupu.module.BaseView;

import java.util.List;

/**
 * Created by cgy on 2019/4/17.
 */
public class MessageListContract {
    interface View extends BaseView {
        void showLoading();

        void hideLoading();

        void renderMessageList(List<Message> messages);

        void onRefreshCompleted();

        void onLoadCompleted(boolean hasMore);

        void onError();

        void onEmpty();

        void showContentUi(String tid, String pid, int page);

        void removeMessage(Message message);
    }

    interface Presenter extends BasePresenter<View> {
        void onMessageListReceive();

        void onRefresh();

        void onReload();

        void onLoadMore();

        void onMessageClick(Message message);
    }
}
