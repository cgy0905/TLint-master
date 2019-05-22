package com.cgy.hupu.module.gallery;


import com.cgy.hupu.bean.Folder;
import com.cgy.hupu.bean.Image;
import com.cgy.hupu.module.BasePresenter;
import com.cgy.hupu.module.BaseView;

import java.util.List;

/**
 * @author cgy
 * @desctiption
 * @date 2019/5/22 16:38
 */
public interface GalleryContract {
    interface View extends BaseView {
        void renderFolders(List<Folder> folders);

        void renderImages(List<Image> images);
    }

    interface Presenter extends BasePresenter<View> {
        void onImageAndFolderReceive();
    }
}
