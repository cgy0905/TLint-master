package com.cgy.hupu.module.post;

import android.support.annotation.NonNull;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Created by cgy on 2019/4/29 15:47 .
 */
public class PostPresenter implements PostContract.Presenter{

    @Inject
    public PostPresenter() {

    }

    @Override
    public void checkPermission(int type, String fid, String tid) {

    }

    @Override
    public void parse(ArrayList<String> paths) {

    }

    @Override
    public void comment(String tid, String fid, String pid, String content) {

    }

    @Override
    public void post(String fid, String content, String title) {

    }

    @Override
    public void attachView(@NonNull PostContract.View view) {

    }

    @Override
    public void detachView() {

    }
}
