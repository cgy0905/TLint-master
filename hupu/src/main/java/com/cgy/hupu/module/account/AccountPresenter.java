package com.cgy.hupu.module.account;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.afollestad.materialdialogs.MaterialDialog;
import com.cgy.hupu.components.UserStorage;
import com.cgy.hupu.db.User;
import com.cgy.hupu.db.UserDao;
import com.cgy.hupu.injector.PerActivity;
import com.cgy.hupu.otto.AccountChangeEvent;
import com.squareup.otto.Bus;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

@PerActivity
public class AccountPresenter implements AccountContract.Presenter{

    private UserDao mUserDao;
    private Activity mActivity;
    private UserStorage mUserStorage;
    private Bus mBus;

    private AccountContract.View mAccountView;
    private Subscription mSubscription;

    @Inject
    public AccountPresenter(UserDao mUserDao, Activity mActivity, UserStorage mUserStorage, Bus mBus) {
        this.mUserDao = mUserDao;
        this.mActivity = mActivity;
        this.mUserStorage = mUserStorage;
        this.mBus = mBus;
    }

    private void loadUserList() {
        mSubscription = Observable.create((Observable.OnSubscribe<List<User>>) subscriber -> subscriber.onNext(mUserDao.queryBuilder().list())).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(users -> mAccountView.renderUserList(users));
    }
    @Override
    public void onAccountDelClick(User user) {
        new MaterialDialog.Builder(mActivity).title("提示")
                .content("确认删除账号?")
                .positiveText("确定")
                .negativeText(" 取消")
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        mUserDao.delete(user);
                        if (String.valueOf(user.getUid()).equals(mUserStorage.getUid())) {
                            mUserStorage.logout();
                        }
                        mBus.post(new AccountChangeEvent());
                        loadUserList();
                    }
                })
                .show();
    }

    @Override
    public void onAccountClick(User user) {
        if (!String.valueOf(user.getUid()).equals(mUserStorage.getUid())) {
            mUserStorage.login(user);
            mBus.post(new AccountChangeEvent());
            mActivity.finish();
        }
    }

    @Override
    public void attachView(@NonNull AccountContract.View view) {
        mAccountView = view;
        loadUserList();
    }

    @Override
    public void detachView() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
        mAccountView = null;
    }
}
