package com.cgy.hupu.module.content;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cgy.hupu.R;
import com.cgy.hupu.module.BaseSwipeBackActivity;
import com.cgy.hupu.utils.ResourceUtil;
import com.cgy.hupu.widget.PagePicker;
import com.cgy.hupu.widget.ProgressBarCircularIndeterminate;
import com.cgy.hupu.widget.VerticalViewPager;
import com.github.clans.fab.FloatingActionMenu;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cgy on 2019/4/17.
 */
public class ContentActivity extends BaseSwipeBackActivity
            implements ContentContract.View, ViewPager.OnPageChangeListener {

    @BindView(R.id.viewpager)
    VerticalViewPager viewpager;
    @BindView(R.id.progress_view)
    ProgressBarCircularIndeterminate progressBar;
    @BindView(R.id.tv_loading)
    TextView tvLoading;
    @BindView(R.id.progress_container)
    LinearLayout progressContainer;
    @BindView(R.id.rl_progress)
    RelativeLayout rlProgress;
    @BindView(R.id.tv_error)
    TextView tvError;
    @BindView(R.id.btn_reload)
    Button btnReload;
    @BindView(R.id.rl_error)
    RelativeLayout rlError;
    @BindView(R.id.floating_comment)
    FloatingActionButton floatingComment;
    @BindView(R.id.floating_report)
    FloatingActionButton floatingReport;
    @BindView(R.id.floating_collect)
    FloatingActionButton floatingCollect;
    @BindView(R.id.floating_share)
    FloatingActionButton floatingShare;
    @BindView(R.id.floating_menu)
    FloatingActionMenu floatingMenu;
    @BindView(R.id.tv_pre)
    TextView tvPre;
    @BindView(R.id.tv_pageNum)
    TextView tvPageNum;
    @BindView(R.id.tv_next)
    TextView tvNext;
    @BindView(R.id.rl_page)
    RelativeLayout rlPage;
    @BindView(R.id.frameLayout)
    FrameLayout frameLayout;

    public static void startActivity(Context context, String fid, String tid, String pid, int page) {
        Intent intent = new Intent(context, ContentActivity.class);
        intent.putExtra("fid", fid);
        intent.putExtra("tid", tid);
        intent.putExtra("pid", pid);
        intent.putExtra("fid", page);
        context.startActivity(intent);
    }

    private String fid;
    private String tid;
    private int page;
    private String pid;

    private PagePicker mPagePicker;

    private ContentComponent mContentComponent;

    @Override
    public int initContentView() {
        return R.layout.activity_content;
    }

    @Override
    public void initInjector() {
        mContentComponent = DaggerContentComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .contentModule(new ContentModule())
                .build();
        mContentComponent.inject(this);
    }

    @Override
    public void initUiAndListener() {
        ButterKnife.bind(this);

        fid = getIntent().getStringExtra("fid");
        tid = getIntent().getStringExtra("tid");
        page = getIntent().getIntExtra("page", 1);
        pid = getIntent().getStringExtra("pid");
        initPicker();
        initFloatingButton();
        viewpager.setOnPageChangeListener(this);
        progressBar.setBackgroundColor(ResourceUtil.getThemeColor(this));
    }

    private void initFloatingButton() {

    }

    private void initPicker() {

    }

    @Override
    protected boolean isApplyStatusBarTranslucency() {
        return false;
    }

    @Override
    protected boolean isApplyStatusBarColor() {
        return false;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void renderContent(int page, int totalPage) {

    }

    @Override
    public void onUpdatePage(int page, int totalPage) {

    }

    @Override
    public void setCurrentItem(int index) {

    }

    @Override
    public void isCollected(boolean isCollected) {

    }

    @Override
    public void onError(String error) {

    }

    @Override
    public void onToggleFloatingMenu() {

    }

    @Override
    public void showLoginUi() {

    }

    @Override
    public void showReportUi() {

    }

    @Override
    public void showPostUi(String title) {

    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {

    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
}
