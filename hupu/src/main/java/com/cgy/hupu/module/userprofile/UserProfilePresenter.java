package com.cgy.hupu.module.userprofile;

import android.support.annotation.NonNull;

import com.cgy.hupu.injector.PerActivity;
import com.cgy.hupu.net.game.GameApi;

import javax.inject.Inject;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * @author cgy
 * @desctiption
 * @date 2019/5/20 13:58
 */
@PerActivity
public class UserProfilePresenter implements UserProfileContract.Presenter{

    private GameApi mGameApi;
    private String uid;

    private Subscription mSubscription;
    private UserProfileContract.View mUserProfileView;

    @Inject
    public UserProfilePresenter(GameApi gameApi, String uid) {
        mGameApi = gameApi;
        this.uid = uid;
    }

    @Override
    public void receiveUserInfo() {
        mUserProfileView.showLoading();
        mSubscription = mGameApi.getUserInfo(uid).map(userData -> {
            if (userData != null) {
                return userData.result;
            }
            return null;
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(userResult -> {
                    mUserProfileView.hideLoading();
                    if (userResult != null) {
                        mUserProfileView.renderUserData(userResult);
                    } else {
                        mUserProfileView.showError();
                    }
                }, throwable -> {
                    mUserProfileView.hideLoading();
                    mUserProfileView.showError();
                });
    }

    @Override
    public void attachView(@NonNull UserProfileContract.View view) {
        mUserProfileView = view;
    }

    @Override
    public void detachView() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
        mUserProfileView = null;
    }
}
