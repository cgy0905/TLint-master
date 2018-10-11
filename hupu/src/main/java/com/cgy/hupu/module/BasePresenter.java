package com.cgy.hupu.module;

import android.support.annotation.NonNull;

/**
 * Created by cgy on 2018/10/11  9:15
 */
public interface BasePresenter <T extends BaseView>{

    void attachView(@NonNull T view);

    void detachView();
}
