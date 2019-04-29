package com.cgy.hupu.module.post;

import com.cgy.hupu.bean.BaseError;
import com.cgy.hupu.bean.Exam;
import com.cgy.hupu.module.BasePresenter;
import com.cgy.hupu.module.BaseView;

import java.util.ArrayList;

/**
 * Created by cgy on 2019/4/29 15:31 .
 */
public interface PostContract {

    interface View extends BaseView {

        void renderError(BaseError error);

        void renderExam(Exam exam);

        void showLoading();

        void hideLoading();

        void postSuccess();

    }

    interface Presenter extends BasePresenter<View> {
        void checkPermission(int type, String fid, String tid);

        void parse(ArrayList<String> paths);

        void comment(String tid, String fid, String pid, String content);

        void post(String fid, String content, String title);

    }}
