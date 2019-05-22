package com.cgy.hupu.module.splash;

import android.content.Context;
import android.support.annotation.NonNull;

import com.alibaba.fastjson.JSON;
import com.cgy.hupu.Constants;
import com.cgy.hupu.bean.UpdateInfo;
import com.cgy.hupu.components.okhttp.OkHttpHelper;
import com.cgy.hupu.injector.PerActivity;
import com.cgy.hupu.utils.ChannelUtil;
import com.cgy.hupu.utils.SettingPrefUtil;
import com.umeng.analytics.MobclickAgent;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by cgy on 2018/10/17  10:28
 */
@PerActivity
public class SplashPresenter implements SplashContract.Presenter {

    private SplashContract.View splashView;
    private Subscription subscription;

    private Context context;
    private OkHttpHelper okHttpHelper;

    @Inject
    public SplashPresenter(Context context, OkHttpHelper okHttpHelper) {
        this.context = context;
        this.okHttpHelper = okHttpHelper;
    }
    @Override
    public void initUmeng() {
        MobclickAgent.UMAnalyticsConfig config = new MobclickAgent.UMAnalyticsConfig(context, "5bc6a918f1f55639d200009f",
                ChannelUtil.getChannel(context));
        MobclickAgent.startWithConfigure(config);
    }

    @Override
    public void initHuPuSign() {
        subscription = Observable.create((Observable.OnSubscribe<UpdateInfo>) subscriber -> {
            try {
                String result = okHttpHelper.getStringFromServer(Constants.UPDATE_URL);
                subscriber.onNext(JSON.parseObject(result, UpdateInfo.class));
            } catch (Exception e) {
                e.printStackTrace();
            }
            subscriber.onCompleted();
        }).timeout(5, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(updateInfo -> {
                    if (updateInfo != null) {
                        if (updateInfo.extra != null) {
                            SettingPrefUtil.setNeedExam(context, updateInfo.extra.needExam == 1);
                        }
                        SettingPrefUtil.setHuPuSign(context, updateInfo.hupuSign);
                    }
                    splashView.showMainUi();
                }, throwable -> {
                    throwable.printStackTrace();
                    splashView.showMainUi();
                });
    }

    @Override
    public void attachView(@NonNull SplashContract.View view) {
        splashView = view;
    }

    @Override
    public void detachView() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
        splashView = null;
    }
}
