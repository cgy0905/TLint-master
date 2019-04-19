package com.cgy.hupu.module.content;

import android.os.Bundle;
import android.view.View;

import com.cgy.hupu.module.BaseFragment;

/**
 * Created by cgy on 2019/4/18.
 */
public class ContentFragment extends BaseFragment {

    public static ContentFragment newInstance(String fid, String tid, String pid, int page) {
        ContentFragment mFragment = new ContentFragment();
        Bundle bundle = new Bundle();
        bundle.putString("fid", fid);
        bundle.putString("tid", tid);
        bundle.putString("pid", pid);
        bundle.putInt("page", page);
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
