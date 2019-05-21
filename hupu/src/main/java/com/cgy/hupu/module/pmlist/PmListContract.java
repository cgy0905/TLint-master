package com.cgy.hupu.module.pmlist;

import com.cgy.hupu.bean.Pm;
import com.cgy.hupu.module.BasePresenter;
import com.cgy.hupu.module.BaseView;

import java.util.List;

/**
 * @author cgy
 * @desctiption
 * @date 2019/5/21 17:01
 */
public interface PmListContract {

    interface View extends BaseView {
        void showLoading();

        void hideLoading();

        void renderPmList(List<Pm> pms);

        void onRefreshCompleted();

        void onLoadCompleted(boolean hasMore);

        void onError();

        void onEmpty();

        void showPmDetailUi(String uid, String name);
    }

    interface Presenter extends BasePresenter<View> {
        void onPmListReceive();

        void onRefresh();

        void onReload();

        void onLoadMore();

        void onPmListClick(Pm pm);
    }
}
