package com.cgy.hupu.module.login;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.cgy.hupu.Constants;
import com.cgy.hupu.bean.LoginData;
import com.cgy.hupu.bean.LoginResult;
import com.cgy.hupu.bean.UserData;
import com.cgy.hupu.bean.UserResult;
import com.cgy.hupu.components.UserStorage;
import com.cgy.hupu.db.User;
import com.cgy.hupu.db.UserDao;
import com.cgy.hupu.injector.PerActivity;
import com.cgy.hupu.net.game.GameApi;
import com.cgy.hupu.otto.LoginSuccessEvent;
import com.cgy.hupu.utils.SecurityUtil;
import com.cgy.hupu.utils.ToastUtil;
import com.squareup.otto.Bus;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;

/**
 * Created by cgy on 2019/1/17 上午 11:38.
 */
@PerActivity
public class LoginPresenter implements LoginContract.Presenter{

    private GameApi mGameApi;
    private Bus mBus;
    private UserDao mUserDao;
    private UserStorage mUserStorage;

    private LoginContract.View mLoginView;
    private User user = new User();
    private Subscription mSubscription;

    @Inject
    public LoginPresenter(GameApi gameApi, Bus bus, UserDao userDao, UserStorage userStorage) {
        mGameApi = gameApi;
        mBus = bus;
        mUserDao = userDao;
        mUserStorage = userStorage;
    }

    @Override
    public void login(String userName, String password) {
        if (TextUtils.isEmpty(userName)) {
            mLoginView.showUserNameError("请输入用户名");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            mLoginView.showPasswordError("请输入密码");
            return;
        }
        mLoginView.showLoading();
        mSubscription = mGameApi.login(userName, SecurityUtil.getMD5(password))
                .flatMap((Func1<LoginData, Observable<UserData>>) loginData -> {
                    if (loginData != null && loginData.is_login == 1) {
                        LoginResult data = loginData.result;
                        String cookie = "";

                        try {
                            cookie = URLDecoder.decode(Constants.Cookie, "UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        String uid = cookie.split("\\|")[0];
                        user.setUid(uid);
                        user.setToken(data.token);
                        user.setCookie(cookie);
                        user.setUserName(data.username);
                        return mGameApi.getUserInfo(uid);
                    }
                    return null;
                })
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(userData -> {
            if (userData != null && userData.result != null) {
                UserResult data = userData.result;
                user.setIcon(data.header);
                user.setThreadUrl(data.bbs_msg_url);
                user.setPostUrl(data.bbs_post_url);
                user.setRegisterTime(data.reg_time_str);
                user.setSchool(data.school);
                user.setSex(data.gender);
                user.setLocation(data.location_str);
                mUserStorage.login(user);
                insertOrUpdateUser(user);
                ToastUtil.showToast("登录成功");
                mBus.post(new LoginSuccessEvent());
                mLoginView.loginSuccess();
            } else {
                mLoginView.hideLoading();
                ToastUtil.showToast("登录失败，请检查您的网络");
            }
        }, throwable -> {
            throwable.printStackTrace();
            mLoginView.hideLoading();
            ToastUtil.showToast("登录失败，请检查您的网络");
        });
    }

    private void insertOrUpdateUser(User user) {
        List<User> users = mUserDao.queryBuilder().where(UserDao.Properties.Uid.eq(user.getUid())).list();
        if (!users.isEmpty()) {
            user.setId(users.get(0).getId());
        }
        mUserDao.insertOrReplace(user);
    }

    @Override
    public void attachView(@NonNull LoginContract.View view) {
        mLoginView = view;
    }

    @Override
    public void detachView() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
        mLoginView = null;
    }
}
