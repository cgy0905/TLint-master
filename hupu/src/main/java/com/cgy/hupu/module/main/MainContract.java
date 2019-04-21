package com.cgy.hupu.module.main;

import android.support.v4.app.Fragment;
import android.view.MenuItem;

import com.cgy.hupu.db.User;
import com.cgy.hupu.module.BasePresenter;
import com.cgy.hupu.module.BaseView;

import java.util.List;

/**
 * Created by cgy on 2018/12/18 下午 5:00.
 */
public interface MainContract {
    interface View extends BaseView {
        void setTitle(CharSequence title);

        void renderUserInfo(User user);

        void renderAccountList(List<User> users, String[] items);

        void renderNotification(int count);

        void reload();

        void closeDrawers();

        void showFragment(Fragment fragment);

        void showMessageUi();

        void showUserProfileUi(String uid);

        void showLoginUi();

        void showAccountUi();

        void showSettingUi();

        void showFeedBackUi();

        void showAboutUi();
    }

    interface Presenter extends BasePresenter<View> {
        void onNightModelClick();

        void onNotificationClick();

        void onCoverClick();

        void onNavigationClick(MenuItem item);

        void showAccountMenu();

        void onAccountItemClick(int position, List<User> users, final String[] items);

        void exist();

        boolean isLogin();
    }
}
