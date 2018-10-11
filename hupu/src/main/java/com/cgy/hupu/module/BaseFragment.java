package com.cgy.hupu.module;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by cgy on 2018/10/11  16:15
 */
public abstract class BaseFragment extends Fragment {

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


}
