package com.cgy.hupu.module.forum;

import android.os.Bundle;
import android.view.View;

import com.cgy.hupu.module.BaseFragment;

/**
 * Created by cgy on 2018/12/30 下午 3:53.
 */
public class ForumListFragment extends BaseFragment {

    public static ForumListFragment newInstance(String forumId) {
        ForumListFragment mFragment = new ForumListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("forumId", forumId);
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
