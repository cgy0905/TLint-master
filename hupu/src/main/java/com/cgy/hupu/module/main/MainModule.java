package com.cgy.hupu.module.main;

import android.text.TextUtils;

import com.cgy.hupu.bean.Pm;
import com.cgy.hupu.components.okhttp.OkHttpHelper;
import com.cgy.hupu.injector.PerActivity;
import com.cgy.hupu.net.forum.ForumApi;
import com.cgy.hupu.net.game.GameApi;
import com.cgy.hupu.utils.UpdateAgent;

import dagger.Module;
import dagger.Provides;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by cgy on 2018/12/17 .
 */
@Module
public class MainModule {

    private MainActivity mActivity;

    public MainModule(MainActivity mActivity) {
        this.mActivity = mActivity;
    }
    @Provides
    @PerActivity
    Observable<Integer> provideNotificationObservable(GameApi mGameApi, ForumApi mForumApi) {
        return Observable.zip(mGameApi.queryPmList(""), mForumApi.getMessageList("", 1),
                (pmData, messageData) -> {
                    int size = 0;
                    if (pmData != null) {
                        if (pmData.is_login == 0) {
                            return null;
                        }
                        for (Pm pm: pmData.result.data) {
                            if (!TextUtils.isEmpty(pm.unread) && pm.unread.equals("1")) {
                                size++;
                            }
                        }
                    }
                    if (messageData != null && messageData.status == 200) {
                        size += messageData.result.list.size();
                    }
                    return size;
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
    @Provides
    @PerActivity
    UpdateAgent provideUpdateAgent(OkHttpHelper mOkHttpHepler) {
        return new UpdateAgent(mOkHttpHepler, mActivity);
    }
}
