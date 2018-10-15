package com.cgy.hupu.module;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * Created by cgy on 2018/10/15  15:28
 */
public abstract class BaseLazyLoadFragment extends BaseFragment{

    private boolean isFirstLoad = true;


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initInjector();
        getBundle(getArguments());
        initUI(view);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getUserVisibleHint()) {
            onVisible();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            onVisible();
        }
    }

    private void onVisible() {
       if (isFirstLoad && isPrepare) {
           initData();
           isFirstLoad = false;
       }
    }
}
