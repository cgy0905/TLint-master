package com.cgy.hupu.module.userprofile;

import com.cgy.hupu.bean.UserResult;
import com.cgy.hupu.module.BasePresenter;
import com.cgy.hupu.module.BaseView;

/**
 * @author cgy
 * @desctiption
 * @date 2019/5/20 13:56
 */
public interface UserProfileContract {

    interface View extends BaseView {
        void renderUserData(UserResult userResult);

        void showLoading();

        void hideLoading();

        void showError();
    }

    interface Presenter extends BasePresenter<View> {
        void receiveUserInfo();
    }
}
