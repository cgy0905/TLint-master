package com.cgy.hupu.module.browser;

import android.os.Bundle;
import android.view.View;

import com.cgy.hupu.module.BaseFragment;

/**
 * Created by cgy on 2018/12/30 下午 3:40.
 */
public class BrowserFragment extends BaseFragment {

    public static BrowserFragment newInstance(String url, String title) {
        return newInstance(url, title, false);
    }

    public static BrowserFragment newInstance(String url, String title, boolean external) {
        BrowserFragment mFragment = new BrowserFragment();
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        bundle.putString("title", title);
        bundle.putBoolean("external", external);
        mFragment.setArguments(bundle);
        return mFragment;
    }

    @Override
    public void initInjector() {

    }

    @Override
    public int initContentView() {
        return 0;
    }

    @Override
    public void getBundle(Bundle bundle) {

    }

    @Override
    public void initUI(View view) {

    }

    @Override
    public void initData() {

    }
}
