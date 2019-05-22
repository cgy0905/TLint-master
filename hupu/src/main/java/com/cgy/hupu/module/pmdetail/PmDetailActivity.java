package com.cgy.hupu.module.pmdetail;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;

import com.cgy.hupu.R;
import com.cgy.hupu.injector.HasComponent;
import com.cgy.hupu.module.BaseSwipeBackActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author cgy
 * @desctiption
 * @date 2019/5/21 18:28
 */
public class PmDetailActivity extends BaseSwipeBackActivity implements HasComponent<PmDetailComponent> {

    public static void startActivity(Context context, String uid, String name) {
        Intent intent = new Intent(context, PmDetailActivity.class);
        intent.putExtra("uid", uid);
        intent.putExtra("name", name);
        context.startActivity(intent);
    }

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private PmDetailComponent mPmDetailComponent;
    private String uid;

    @Override
    public int initContentView() {
        return R.layout.base_content_toolbar_layout;
    }

    @Override
    public void initInjector() {
        uid = getIntent().getStringExtra("uid");
        mPmDetailComponent = DaggerPmDetailComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .pmDetailModule(new PmDetailModule(uid))
                .build();
    }

    @Override
    public void initUiAndListener() {
        ButterKnife.bind(this);
        initToolBar(toolbar);
        String name = getIntent().getStringExtra("name");

        setTitle(name);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, PmDetailFragment.newInstance(uid))
                .commit();
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
    public PmDetailComponent getComponent() {
        return mPmDetailComponent;
    }
    
}
