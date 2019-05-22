package com.cgy.hupu.module.pmdetail;

import com.cgy.hupu.bean.PmDetail;
import com.cgy.hupu.module.BasePresenter;
import com.cgy.hupu.module.BaseView;

import java.util.List;

/**
 * @author cgy
 * @desctiption
 * @date 2019/5/22 10:02
 */
public interface PmDetailContract {

    interface View extends BaseView {
        void showLoading();

        void hideLoading();

        void isBlock(boolean isBlock);

        void rendPmDetailList(List<PmDetail> pmDetails);

        void scrollTo(int position);

        void onRefreshCompleted();

        void onError();

        void onEmpty();

        void cleanEditText();
    }

    interface Presenter extends BasePresenter<View> {

        void onPmDetailReceive();

        void onLoadMore();

        void onReload();

        void send(String content);

        void clear();

        void block();

    }
}
