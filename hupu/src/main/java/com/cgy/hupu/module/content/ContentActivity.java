package com.cgy.hupu.module.content;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
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
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cgy on 2019/4/17.
 */
public class ContentActivity extends BaseSwipeBackActivity
            implements ContentContract.View, ViewPager.OnPageChangeListener, PagePicker.OnJumpListener {

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
    @Inject
    ContentPresenter mPresenter;

    private String fid;
    private String tid;
    private int page;
    private String pid;

    private PagePicker mPagePicker;
    private MyAdapter mAdapter;
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
        ResourceUtil.setFabMenuColor(this, floatingMenu);
        ResourceUtil.setFabBtnColor(this, floatingComment);
        ResourceUtil.setFabBtnColor(this, floatingCollect);
        ResourceUtil.setFabBtnColor(this, floatingShare);
        ResourceUtil.setFabBtnColor(this, floatingReport);
    }

    private void initPicker() {
        mPagePicker = new PagePicker(this);
        mPagePicker.setOnJumpListener(this);
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
        rlProgress.setVisibility(View.VISIBLE);
        viewpager.setVisibility(View.GONE);
        rlError.setVisibility(View.GONE);
    }

    @Override
    public void hideLoading() {
        rlProgress.setVisibility(View.GONE);
        viewpager.setVisibility(View.VISIBLE);
        rlError.setVisibility(View.GONE);
    }

    @Override
    public void renderContent(int page, int totalPage) {
        mAdapter = new MyAdapter(getSupportFragmentManager(), totalPage);
        viewpager.setAdapter(mAdapter);
        viewpager.setCurrentItem(page - 1);
        onUpdatePage(page, totalPage);
    }

    @Override
    public void onUpdatePage(int page, int totalPage) {
        mPagePicker.setMin(1);
        mPagePicker.setMax(totalPage);
        mPagePicker.setValue(page);
        tvPageNum.setText(page + "/" + totalPage);
        if (page == 1) {
            tvPre.setTextColor(getResources().getColor(R.color.base_text_gray));
            tvPre.setClickable(false);
        } else {
            tvPre.setTextColor(getResources().getColor(R.color.blue));
            tvPre.setClickable(true);
        }
    }

    @Override
    public void setCurrentItem(int index) {
        viewpager.setCurrentItem(index);
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
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public class MyAdapter extends FragmentPagerAdapter {

        private int totalPage;

        public MyAdapter(FragmentManager fm, int totalPage) {
            super(fm);
            this.totalPage = totalPage;
            notifyDataSetChanged();
        }

        @Override
        public Fragment getItem(int position) {
            return ContentFragment.newInstance(fid, tid, pid, position + 1);
        }

        @Override
        public int getCount() {
            return totalPage;
        }
    }

    @Override
    public void onJump(int page) {

    }
}
