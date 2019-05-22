package com.cgy.hupu.module.imagepreview;

import com.cgy.hupu.module.BasePresenter;
import com.cgy.hupu.module.BaseView;

/**
 * @author cgy
 * @desctiption
 * @date 2019/5/22 14:31
 */
public interface ImagePreviewContract {

    interface View extends BaseView {

    }

    interface Presenter extends BasePresenter<View> {
        void saveImage(String url);

        void copyImagePath(String url);
    }
}
