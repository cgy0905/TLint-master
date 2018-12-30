package com.cgy.hupu.module;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cgy.hupu.R;
import com.cgy.hupu.injector.HasComponent;
import com.cgy.hupu.utils.ResourceUtil;
import com.cgy.hupu.widget.ProgressBarCircularIndeterminate;
import com.cgy.hupu.widget.ProgressFragment;

/**
 * Created by cgy on 2018/10/11  16:15
 */
public abstract class BaseFragment extends ProgressFragment {

    private TextView tvError, tvEmpty, tvLoading;
    private Button btnReload;

    public abstract void initInjector();

    public abstract int initContentView();

    /**
     * 得到Activity传过来的值
     * @param bundle
     */
    public abstract void getBundle(Bundle bundle);

    /**
     * 初始化控件
     */
    public abstract void initUI(View view);

    /**
     * 在监听器之前把数据准备好
     */
    public abstract void initData();

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initInjector();
        getBundle(getArguments());
        initUI(view);
        initData();
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateContentView(LayoutInflater inflater) {
        return inflater.inflate(initContentView(), null);
    }

    @Override
    public View onCreateContentErrorView(LayoutInflater inflater) {
        View errorView = inflater.inflate(R.layout.error_view_layout, null);
        tvError = (TextView) errorView.findViewById(R.id.tv_error);
        errorView.findViewById(R.id.btn_reload).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onReloadClicked();
            }
        });
        return errorView;
    }

    @Override
    public View onCreateContentEmptyView(LayoutInflater inflater) {
        View emptyView = inflater.inflate(R.layout.empty_view_layout, null);
        tvEmpty = (TextView) emptyView.findViewById(R.id.tv_empty);
        btnReload = (Button) emptyView.findViewById(R.id.btn_reload);
        emptyView.findViewById(R.id.btn_reload).setOnClickListener(v -> onReloadClicked());
        return emptyView;
    }

    @Override
    public View onCreateProgressView(LayoutInflater inflater) {
        View loadingView = inflater.inflate(R.layout.loading_view_layout, null);
        tvLoading = (TextView) loadingView.findViewById(R.id.tv_loading);
        ProgressBarCircularIndeterminate progressBar = (ProgressBarCircularIndeterminate) loadingView.findViewById(R.id.progress_view);
        progressBar.setBackgroundColor(ResourceUtil.getThemeColor(getActivity()));
        return loadingView;
    }

    public void setErrorText(String text) {
        tvError.setText(text);
    }

    public void setErrorText(int textResId) {
        setErrorText(getString(textResId));
    }

    public void setEmptyText(String text) {
        tvEmpty.setText(text);
    }

    public void setEmptyButtonVisible(int visible) {
        btnReload.setVisibility(visible);
    }

    public void setEmptyText(int textResId) {
        setEmptyText(getString(textResId));
    }

    public void setLoadingText(String text) {
        tvLoading.setText(text);
    }

    public void setLoadingText(int textResId) {
        setLoadingText(getString(textResId));
    }

    //Override this to reload
    private void onReloadClicked() {

    }

    @SuppressWarnings("unchecked")
    protected <C> C getComponent(Class<C> componentType) {
        return componentType.cast(((HasComponent<C>) getActivity()).getComponent());
    }
}
