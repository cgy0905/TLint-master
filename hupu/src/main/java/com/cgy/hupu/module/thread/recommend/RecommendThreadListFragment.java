package com.cgy.hupu.module.thread.recommend;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.cgy.hupu.R;
import com.cgy.hupu.db.Thread;
import com.cgy.hupu.module.BaseFragment;
import com.cgy.hupu.module.main.MainComponent;
import com.cgy.hupu.module.thread.ThreadListAdapter;
import com.cgy.hupu.widget.LoadMoreRecyclerView;
import com.yalantis.phoenix.PullToRefreshView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by cgy on 2018/12/18 下午 4:56.
 */
public class RecommendThreadListFragment extends BaseFragment implements RecommendThreadListContract.View, PullToRefreshView.OnRefreshListener, LoadMoreRecyclerView.LoadMoreListener {

    @Inject
    ThreadListAdapter mAdapter;
    @Inject
    Activity mActivity;
    @Inject
    ThreadRecommendPresenter mRecommendPresenter;

    @BindView(R.id.recyclerView)
    LoadMoreRecyclerView mRecyclerView;
    @BindView(R.id.refreshLayout)
    PullToRefreshView mRefreshLayout;
    Unbinder unbinder;

    @Override
    public void initInjector() {
        getComponent(MainComponent.class).inject(this);
    }

    @Override
    public int initContentView() {
        return R.layout.base_phonix_list_layout;
    }

    @Override
    public void getBundle(Bundle bundle) {

    }

    @Override
    public void initUI(View view) {
        ButterKnife.bind(this, view);
        mRecommendPresenter.attachView(this);
        mRefreshLayout.setOnRefreshListener(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity.getApplicationContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLoadMoreListener(this);
    }

    @Override
    public void initData() {
        mRecommendPresenter.onThreadReceive();
    }

    @Override
    public void showLoading() {
        mRefreshLayout.setRefreshing(true);
    }

    @Override
    public void showContent() {
        showContent(true);
    }

    @Override
    public void renderThreads(List<Thread> threads) {
        mAdapter.bind(threads);
        mRecyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onError(String error) {
        setErrorText(error);
        showError(true);
    }

    @Override
    public void onEmpty() {
        setEmptyText("没有推荐帖子");
        showEmpty(true);
    }

    @Override
    public void onLoadCompleted(boolean hasMore) {
        mRecyclerView.onLoadCompleted(hasMore);
    }

    @Override
    public void onRefreshCompleted() {
        mRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        mRecommendPresenter.onRefresh();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        mRecommendPresenter.detachView();
    }

    @Override
    public void onReloadClicked() {
        mRecommendPresenter.onReload();
    }

    @Override
    public void onLoadMore() {

    }

}
