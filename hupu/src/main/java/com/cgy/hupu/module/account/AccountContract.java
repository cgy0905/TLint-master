package com.cgy.hupu.module.account;

import com.cgy.hupu.db.User;
import com.cgy.hupu.module.BasePresenter;
import com.cgy.hupu.module.BaseView;

import java.util.List;

public interface AccountContract {
    interface View extends BaseView {
        void renderUserList(List<User> users);
    }

    interface Presenter extends BasePresenter<View> {
        void onAccountDelClick(User user);

        void onAccountClick(User user);
    }
}
