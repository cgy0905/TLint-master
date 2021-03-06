package com.cgy.hupu.module.main;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.MenuItem;

import com.cgy.hupu.AppManager;
import com.cgy.hupu.Constants;
import com.cgy.hupu.R;
import com.cgy.hupu.components.UserStorage;
import com.cgy.hupu.db.User;
import com.cgy.hupu.db.UserDao;
import com.cgy.hupu.injector.PerActivity;
import com.cgy.hupu.module.browser.BrowserFragment;
import com.cgy.hupu.module.forum.ForumListFragment;
import com.cgy.hupu.module.thread.collect.CollectThreadListFragment;
import com.cgy.hupu.module.thread.recommend.RecommendThreadListFragment;
import com.cgy.hupu.otto.AccountChangeEvent;
import com.cgy.hupu.otto.ChangeThemeEvent;
import com.cgy.hupu.otto.LoginSuccessEvent;
import com.cgy.hupu.otto.MessageReadEvent;
import com.cgy.hupu.utils.SettingPrefUtil;
import com.cgy.hupu.utils.ToastUtil;
import com.cgy.hupu.utils.UpdateAgent;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by cgy on 2018/12/18 下午 5:16.
 */
@PerActivity
public class MainPresenter implements MainContract.Presenter {

    private UserStorage mUserStorage;
    private UserDao mUserDao;
    private Bus mBus;
    private Observable<Integer> mNotificationObservable;
    private UpdateAgent mUpdateAgent;
    private Context mContext;

    private Subscription mSubscription;
    private MainContract.View mMainView;

    private int count = 0;

    @Inject
    public MainPresenter(UserStorage mUserStorage, UserDao mUserDao, Bus mBus,
                         Observable<Integer> mNotificationObservable, UpdateAgent mUpdateAgent, Context mContext) {
        this.mUserStorage = mUserStorage;
        this.mUserDao = mUserDao;
        this.mBus = mBus;
        this.mNotificationObservable = mNotificationObservable;
        this.mUpdateAgent = mUpdateAgent;
        this.mContext = mContext;
    }

    @Override
    public void attachView(@NonNull MainContract.View view) {
        mMainView = view;
        initUserInfo();
        initNotification();
    }


    private void initUserInfo() {
        mMainView.renderUserInfo(isLogin() ? mUserStorage.getUser() : null);
    }

    private void initNotification() {
        if (isLogin() && !TextUtils.isEmpty(SettingPrefUtil.getHuPuSign(mContext))) {
            mSubscription = mNotificationObservable.subscribe(integer -> {
                if (integer == null) {
                    ToastUtil.showToast("登录信息失效，请重新登录");
                    mUserDao.queryBuilder().where(UserDao.Properties.Uid.eq(mUserStorage.getUid()))
                            .buildDelete()
                            .executeDeleteWithoutDetachingEntities();
                    mUserStorage.logout();
                    mMainView.showLoginUi();
                } else {
                    count = integer;
                    mMainView.renderNotification(integer);
                }
            }, throwable -> {

            });
        }
    }

    private void toLogin() {
        mMainView.showLoginUi();
        ToastUtil.showToast("请先登录");
    }

    @Override
    public void onNightModelClick() {
        SettingPrefUtil.setNightModel(mContext, !SettingPrefUtil.getNightModel(mContext));
    }

    @Override
    public void onNotificationClick() {
        if (isLogin()) {
            mMainView.showLoginUi();
        } else {
            toLogin();
        }
    }

    @Override
    public void onCoverClick() {
        if (isLogin()) {
            mMainView.showUserProfileUi(mUserStorage.getUid());
        } else {
            toLogin();
        }
        mMainView.closeDrawers();
    }

    @Override
    public void onNavigationClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_recommend:
            case R.id.nav_collect:
            case R.id.nav_topic:
            case R.id.nav_nba:
            case R.id.nav_my:
            case R.id.nav_cba:
            case R.id.nav_gambia:
            case R.id.nav_equipment:
            case R.id.nav_fitness:
            case R.id.nav_football:
            case R.id.nav_intel_football:
            case R.id.nav_sport:
                Fragment mFragment = null;
                int id = item.getItemId();
                if (id == R.id.nav_collect) {
                    if (isLogin()) {
                        mFragment = new CollectThreadListFragment();
                    } else {
                        toLogin();
                    }
                } else if (id == R.id.nav_topic) {
                    if (isLogin()) {
                        mFragment = BrowserFragment.newInstance(mUserStorage.getUser().getThreadUrl(), "我的帖子");
                    } else {
                        toLogin();
                    }
                } else if (id == R.id.nav_recommend) {
                    mFragment = new RecommendThreadListFragment();
                } else {
                    if (isLogin() || id != R.id.nav_my) {
                        mFragment = ForumListFragment.newInstance(Constants.mNavMap.get(id));
                    } else {
                        toLogin();
                    }
                }
                if (mFragment != null) {
                    item.setChecked(true);
                    mMainView.setTitle(item.getTitle());
                    mMainView.showFragment(mFragment);
                }
                break;
            case R.id.nav_setting:
                mMainView.showSettingUi();
                break;
            case R.id.nav_feedback:
                mMainView.showFeedBackUi();
                break;
            case R.id.nav_about:
                mMainView.showAboutUi();
                break;
        }
        mMainView.closeDrawers();
    }

    @Override
    public void showAccountMenu() {
        Observable.create((Observable.OnSubscribe<List<User>>) subscriber -> {
            final List<User> userList = mUserDao.queryBuilder().list();
            for (User bean : userList) {
                if (bean.getUid().equals(mUserStorage.getUid())) {
                    userList.remove(bean);
                    break;
                }
            }
            subscriber.onNext(userList);
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(users -> {
                    final String[] items = new String[users.size() + 1];
                    for (int i = 0; i < users.size(); i++) {
                        items[i] = users.get(i).getUserName();
                        items[items.length - 1] = "账号管理";
                        mMainView.renderAccountList(users, items);
                    }
                });

    }

    @Override
    public void onAccountItemClick(int position, List<User> users, String[] items) {
        if (position == items.length - 1) {
            //账号管理
            mMainView.showAccountUi();
        } else {
            mUserStorage.login(users.get(position));
            initUserInfo();
        }
        mMainView.closeDrawers();
    }

    @Override
    public void exist() {
        if (isCanExit()) {
            AppManager.getAppManager().AppExit(mContext);
        }
    }

    private long mExitTime = 0;

    private boolean isCanExit() {
        if (System.currentTimeMillis() - mExitTime > 2000) {
            ToastUtil.showToast("再按一次退出程序");
            mExitTime = System.currentTimeMillis();
            return false;
        }
        return true;
    }

    @Override
    public boolean isLogin() {
        return mUserStorage.isLogin();
    }

    @Override
    public void detachView() {
        mBus.unregister(this);
        count = 0;
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
        mMainView = null;
    }

    @Subscribe
    public void onChangeThemeEvent(ChangeThemeEvent event) {
        mMainView.reload();
    }

    @Subscribe
    public void onLoginSuccessEvent(LoginSuccessEvent event) {
        initUserInfo();
    }

    @Subscribe
    public void onAccountChangeEvent(AccountChangeEvent event) {
        initUserInfo();
    }

    @Subscribe
    public void onMessageReadEvent(MessageReadEvent event) {
        if (count >= 1) {
            count--;
        }
        mMainView.renderNotification(count);
    }
}
