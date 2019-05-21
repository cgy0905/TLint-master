package com.cgy.hupu.module.forum;

import com.cgy.hupu.db.Forum;
import com.cgy.hupu.module.BasePresenter;
import com.cgy.hupu.module.BaseView;

import java.util.List;

/**
 * @author cgy
 * @desctiption
 * @date 2019/5/21 10:49
 */
public interface ForumListContract {
    interface View extends BaseView {
        void showLoading();

        void hideLoading();

        void onError();

        void renderForumList(List<Forum> forumList);

        void showThreadUi(String fid);

        void removeForum(Forum forum);
    }

    interface Presenter extends BasePresenter<View> {

        void onForumListReceive(String forumId);

        void onForumAttentionDelClick(Forum forum);

        void onForumOfflineClick(Forum forum);

        void onForumClick(Forum forum);
    }
}
