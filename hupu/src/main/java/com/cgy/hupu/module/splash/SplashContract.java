package com.cgy.hupu.module.splash;

import com.cgy.hupu.module.BasePresenter;
import com.cgy.hupu.module.BaseView;

/**
 * Created by cgy on 2018/10/17  9:48
 */
public interface SplashContract {

    interface View extends BaseView {
        void showMainUi();
    }

    interface Presenter extends BasePresenter<View> {
        void initUmeng();

        void initHuPuSign();
    }
}
