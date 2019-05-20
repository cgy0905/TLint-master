package com.cgy.hupu.module.userprofile;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.cgy.hupu.R;
import com.cgy.hupu.bean.UserResult;
import com.cgy.hupu.module.BaseActivity;
import com.cgy.hupu.module.BasePresenter;
import com.cgy.hupu.module.BaseSwipeBackActivity;
import com.cgy.hupu.module.browser.BrowserFragment;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 个人中心页面
 */
public class UserProfileActivity extends BaseSwipeBackActivity implements UserProfileContract.View , SwipeRefreshLayout.OnRefreshListener {

    public static void startActivity(Context context, String uid) {
        Intent intent = new Intent(context, UserProfileActivity.class);
        intent.putExtra("uid", uid);
        context.startActivity(intent);
    }

    @BindView(R.id.iv_cover)
    ImageView ivCover;
    @BindView(R.id.iv_photo)
    SimpleDraweeView ivPhoto;
    @BindView(R.id.tv_register_name)
    TextView tvRegisterName;
    @BindView(R.id.iv_gender)
    ImageView ivGender;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.appbar_layout)
    AppBarLayout appbarLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.main_content)
    CoordinatorLayout mainContent;

    @Inject
    UserProfilePresenter mPresenter;

    private MaterialDialog mDialog;

    @Override
    public int initContentView() {
        return R.layout.activity_user_profile;
    }

    @Override
    public void initInjector() {
        String uid = getIntent().getStringExtra("uid");
        DaggerUserProfileComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .userProfileModule(new UserProfileModule(uid))
                .build()
                .inject(this);
    }

    @Override
    public void initUiAndListener() {
        ButterKnife.bind(this);
        mPresenter.attachView(this);
        initToolBar(toolbar);
        collapsingToolbar.setTitleEnabled(false);
        mDialog = new MaterialDialog.Builder(this).title("提示")
                .content("正在加载")
                .cancelable(false)
                .progress(true, 0)
                .build();
        mPresenter.receiveUserInfo();

    }

    @Override
    protected boolean isApplyStatusBarTranslucency() {
        return true;
    }

    @Override
    protected boolean isApplyStatusBarColor() {
        return false;
    }

    @Override
    public void renderUserData(UserResult userResult) {
        if (userResult != null) {
            setupViewPager(userResult);
            if (!TextUtils.isEmpty(userResult.header)) {
                ivPhoto.setImageURI(Uri.parse(userResult.header));
            }
            ivGender.setImageResource(userResult.gender == 0 ? R.drawable.list_male : R.drawable.list_female);
            tvRegisterName.setText(userResult.reg_time_str);
            setTitle(userResult.nickname);
        }
    }

    private void setupViewPager(UserResult userResult) {
        MyAdapter adapter = new MyAdapter(getSupportFragmentManager());
        adapter.addFragment(BrowserFragment.newInstance(userResult.bbs_msg_url, ""),
                String.format("发帖(%s)", userResult.bbs_msg_count));
        adapter.addFragment(BrowserFragment.newInstance(userResult.bbs_post_url, ""),
                String.format("回帖(%s)", userResult.bbs_post_count));
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    public static class MyAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }

    @Override
    public void showLoading() {
        if (!mDialog.isShowing() && !isFinishing()) {
            mDialog.show();
        }
    }

    @Override
    public void hideLoading() {
        if (mDialog.isShowing() && !isFinishing()) {
            mDialog.dismiss();
        }
    }

    @Override
    public void showError() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @Override
    public void onRefresh() {

    }
}
