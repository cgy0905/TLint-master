package com.cgy.hupu.module.account;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.cgy.hupu.R;
import com.cgy.hupu.db.User;
import com.cgy.hupu.injector.component.DaggerApplicationComponent;
import com.cgy.hupu.module.BaseActivity;
import com.cgy.hupu.module.BaseSwipeBackActivity;
import com.cgy.hupu.module.login.LoginActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AccountActivity extends BaseSwipeBackActivity implements AccountContract.View, AccountAdapter.OnItemClickListener {

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, AccountActivity.class);
        context.startActivity(intent);
    }
    @Inject
    AccountPresenter mPresenter;
    @Inject
    AccountAdapter mAdapter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    public int initContentView() {
        return R.layout.activity_account;
    }

    @Override
    public void initInjector() {
        DaggerAccountComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .accountModule(new AccountModule())
                .build()
                .inject(this);
    }

    @Override
    public void initUiAndListener() {
        ButterKnife.bind(this);
        mPresenter.attachView(this);
        initToolBar(toolbar);
        setTitle("账号管理");
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
    }

    @Override
    protected boolean isApplyStatusBarTranslucency() {
        return true;
    }

    @Override
    protected boolean isApplyStatusBarColor() {
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_account, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add) {
            LoginActivity.startActivity(this);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void renderUserList(List<User> users) {
        mAdapter.bind(users);
    }

    @Override
    public void onAccountItemDelClicked(User user) {
        mPresenter.onAccountDelClick(user);
    }

    @Override
    public void onAccountItemClicked(User user) {
        mPresenter.onAccountClick(user);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }
}
