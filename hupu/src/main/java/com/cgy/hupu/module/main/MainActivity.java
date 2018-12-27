package com.cgy.hupu.module.main;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.cgy.hupu.Constants;
import com.cgy.hupu.R;
import com.cgy.hupu.db.User;
import com.cgy.hupu.injector.HasComponent;
import com.cgy.hupu.module.BaseActivity;
import com.cgy.hupu.module.account.AccountActivity;
import com.cgy.hupu.module.browser.BrowserActivity;
import com.cgy.hupu.module.login.LoginActivity;
import com.cgy.hupu.module.messagelist.MessageActivity;
import com.cgy.hupu.module.post.PostActivity;
import com.cgy.hupu.module.setting.SettingActivity;
import com.cgy.hupu.module.thread.RecommendThreadListFragment;
import com.cgy.hupu.module.userprofile.UserProfileActivity;
import com.cgy.hupu.utils.ResourceUtil;
import com.cgy.hupu.utils.SettingPrefUtil;
import com.cgy.hupu.utils.StatusBarUtil;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by cgy on 2018/10/17  10:43
 */
public class MainActivity extends BaseActivity implements View.OnClickListener, MainContract.View, HasComponent<MainComponent> {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.navigation_view)
    NavigationView navigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    ImageView ivTheme;
    SimpleDraweeView ivIcon;
    TextView tvName;

    @Inject
    MainPresenter mPresenter;

    private MainComponent mMainComponent;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int initContentView() {
        return R.layout.activity_main;
    }

    @Override
    public void initInjector() {
        mMainComponent = DaggerMainComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule()).mainModule(new MainModule(this))
                .build();
        mMainComponent.inject(this);
    }

    @Override
    public void initUiAndListener() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("帖子推荐");
        ivIcon = navigationView.getHeaderView(0).findViewById(R.id.iv_icon);
        tvName = navigationView.getHeaderView(0).findViewById(R.id.tv_name);
        navigationView.getHeaderView(0).findViewById(R.id.iv_cover).setOnClickListener(this);
        navigationView.getHeaderView(0).findViewById(R.id.ll_account).setOnClickListener(this);
        ivTheme = navigationView.getHeaderView(0).findViewById(R.id.iv_theme);
        ivTheme.setOnClickListener(this);
        ivTheme.setImageResource(SettingPrefUtil.getNightModel(this) ? R.drawable.ic_wb_sunny_white_24dp
                : R.drawable.ic_brightness_3_white_24dp);
        //创建返回键,并实现打开/关闭监听
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerToggle.syncState();
        drawerLayout.setDrawerListener(drawerToggle);
        StatusBarUtil.setColorForDrawerLayout(this, drawerLayout, ResourceUtil.getThemeColor(this), 0);
        setupDrawerContent();
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, new RecommendThreadListFragment())
                .commit();
        mPresenter.attachView(this);
    }

    private void setupDrawerContent() {
        navigationView.setNavigationItemSelectedListener(
                item -> {
                    mPresenter.onNavigationClick(item);
                    return true;
                }
        );
    }

    private int count = 0;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem menuItem = menu.findItem(R.id.action_notification);
        menuItem.setIcon(buildCounterDrawable(count, R.drawable.ic_menu_notification));
        return true;
    }

    private Drawable buildCounterDrawable(int count, int backgroundImageId) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.notification_count_layout, null);
        view.setBackgroundResource(backgroundImageId);
        TextView tvCount = view.findViewById(R.id.tv_count);
        if (count == 0) {
            tvCount.setVisibility(View.GONE);
        } else {
            tvCount.setVisibility(View.VISIBLE);
            tvCount.setText(String.valueOf(count));
        }

        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());

        view.setDrawingCacheEnabled(true);
        view.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);

        return new BitmapDrawable(getResources(), bitmap);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.action_notification:
                mPresenter.onNotificationClick();
                break;
        }
        return super.onOptionsItemSelected(item);
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_cover:
                mPresenter.onCoverClick();
                break;
            case R.id.ll_account:
                mPresenter.showAccountMenu();
                break;
            case R.id.iv_theme:
                mPresenter.onNightModelClick();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @Override
    public void renderUserInfo(User user) {
        if (user != null) {
            if (!TextUtils.isEmpty(user.getIcon())) {
                ivIcon.setImageURI(Uri.parse(user.getIcon()));
            }
            tvName.setText(user.getUserName());
        } else {
            ivIcon.setImageURI("");
            tvName.setText(null);
        }
    }

    @Override
    public void renderAccountList(List<User> users, String[] items) {
        new MaterialDialog.Builder(this).items(items).itemsCallback((dialog, itemView, which, text)
                -> mPresenter.onAccountItemClick(which, users, items)).show();
    }

    @Override
    public void renderNotification(int count) {
        this.count = count;
        invalidateOptionsMenu();
    }

    @Override
    public void closeDrawers() {
        drawerLayout.closeDrawers();
    }

    @Override
    public void showFragment(Fragment fragment) {
        getFragmentManager().beginTransaction().replace(R.id.fl_content, fragment).commit();
    }

    @Override
    public void showMessageUi() {
        MessageActivity.startActivity(this);
    }

    @Override
    public void showUserProfileUi(String uid) {
        UserProfileActivity.startActivity(this, uid);
    }

    @Override
    public void showLoginUi() {
        LoginActivity.startActivity(this);
    }

    @Override
    public void showAccountUi() {
        AccountActivity.startActivity(this);
    }

    @Override
    public void showSettingUi() {
        SettingActivity.startActivity(this);
    }

    @Override
    public void showFeedBackUi() {
        PostActivity.startActivity(MainActivity.this, Constants.TYPE_FEEDBACK, "", "2869008", "",
                "TLint For Android");
    }

    @Override
    public void showAboutUi() {
        BrowserActivity.startActivity(this, "http://www.pursll.com/TLint");
    }

    @Override
    public MainComponent getComponent() {
        return mMainComponent;
    }






}
