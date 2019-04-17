package com.cgy.hupu.module.messagelist;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cgy.hupu.R;
import com.cgy.hupu.bean.Message;
import com.cgy.hupu.module.BaseFragment;
import com.cgy.hupu.module.content.ContentActivity;
import com.cgy.hupu.widget.LoadMoreRecyclerView;
import com.yalantis.phoenix.PullToRefreshView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by cgy on 2019/4/17.
 */
public class MessageListFragment extends BaseFragment implements MessageListContract.View ,
        PullToRefreshView.OnRefreshListener, LoadMoreRecyclerView.LoadMoreListener {

    @BindView(R.id.recyclerView)
    LoadMoreRecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    PullToRefreshView refreshLayout;
    Unbinder unbinder;

    @Inject
    MessageListPresenter mPresenter;
    @Inject
    MessageListAdapter mAdapter;

    @Inject
    Activity mActivity;

    @Override
    public void initInjector() {
        getComponent(MessageComponent.class).inject(this);
    }

    @Override
    public int initContentView() {
        return R.layout.fragment_message_list;
    }

    @Override
    public void getBundle(Bundle bundle) {

    }

    @Override
    public void initUI(View view) {
        ButterKnife.bind(this, view);
        mPresenter.attachView(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity.getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLoadMoreListener(this);
    }

    @Override
    public void initData() {
        mPresenter.onMessageListReceive();
    }

    @Override
    public void showLoading() {
        showProgress(true);
    }

    @Override
    public void hideLoading() {
        showContent(true);
    }

    @Override
    public void renderMessageList(List<Message> messages) {
        mAdapter.bind(messages);
    }

    @Override
    public void onRefreshCompleted() {
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void onLoadCompleted(boolean hasMore) {
        recyclerView.onLoadCompleted(hasMore);
    }

    @Override
    public void onError() {
        showError(true);
    }

    @Override
    public void onEmpty() {
        setEmptyText("暂无论坛消息");
        showEmpty(true);
    }

    @Override
    public void showContentUi(String tid, String pid, int page) {
        ContentActivity.startActivity(mActivity, "", tid, pid, page);
    }

    @Override
    public void removeMessage(Message message) {
        mAdapter.remove(message);
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
        unbinder.unbind();
    }

    @Override
    public void onLoadMore() {
        mPresenter.onLoadMore();
    }

    @Override
    public void onRefresh() {
        mPresenter.onRefresh();
    }

    @Override
    public void onReloadClicked() {
        mPresenter.onLoadMore();
    }
}
