package com.cgy.hupu.module.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.MenuItem;

import com.cgy.hupu.components.UserStorage;
import com.cgy.hupu.db.User;
import com.cgy.hupu.db.UserDao;
import com.cgy.hupu.injector.PerActivity;
import com.cgy.hupu.utils.UpdateAgent;
import com.squareup.otto.Bus;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscription;

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
    public MainPresenter(){

    }
    @Override
    public void onNightModelClick() {

    }

    @Override
    public void onNotificationClick() {

    }

    @Override
    public void onCoverClick() {

    }

    @Override
    public void onNavigationClick(MenuItem item) {

    }

    @Override
    public void showAccountMenu() {

    }

    @Override
    public void onAccountItemClick(int position, List<User> users, String[] items) {

    }

    @Override
    public void exist() {

    }

    @Override
    public boolean isLogin() {
        return false;
    }

    @Override
    public void attachView(@NonNull MainContract.View view) {

    }

    @Override
    public void detachView() {

    }
}
