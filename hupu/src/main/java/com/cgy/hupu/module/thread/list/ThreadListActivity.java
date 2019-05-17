package com.cgy.hupu.module.thread.list;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.cgy.hupu.R;
import com.cgy.hupu.db.Thread;
import com.cgy.hupu.injector.HasComponent;
import com.cgy.hupu.module.BaseActivity;
import com.cgy.hupu.module.BaseSwipeBackActivity;

import butterknife.ButterKnife;

public class ThreadListActivity extends BaseSwipeBackActivity implements HasComponent<ThreadListComponent> {

    public static void startActivity(Context context, String fid) {
        Intent intent = new Intent(context, ThreadListActivity.class);
        intent.putExtra("fid", fid);
        context.startActivity(intent);

    }

    private ThreadListComponent mThreadListComponent;

    @Override
    public int initContentView() {
        return R.layout.base_content_empty;
    }

    @Override
    public void initInjector() {
        String fid = getIntent().getStringExtra("fid");
        mThreadListComponent = DaggerThreadListComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .threadListModule(new ThreadListModule(fid))
                .build();
    }

    @Override
    public void initUiAndListener() {
        ButterKnife.bind(this);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_content, new ThreadListFragment())
                .commit();
    }

    @Override
    protected boolean isApplyStatusBarTranslucency() {
        return false;
    }

    @Override
    protected boolean isApplyStatusBarColor() {
        return true;
    }

    @Override
    public ThreadListComponent getComponent() {
        return mThreadListComponent;
    }
}
