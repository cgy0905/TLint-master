package com.cgy.hupu.module.login;

import com.cgy.hupu.module.BasePresenter;
import com.cgy.hupu.module.BaseView;

/**
 * Created by cgy on 2019/1/17 上午 11:31.
 */
public interface LoginContract {

    interface View extends BaseView {
        void showLoading();

        void hideLoading();

        void showUserNameError(String error);

        void showPasswordError(String error);

        void loginSuccess();
    }

    interface Presenter extends BasePresenter<View> {
        void login(String userName, String password);
    }
}
