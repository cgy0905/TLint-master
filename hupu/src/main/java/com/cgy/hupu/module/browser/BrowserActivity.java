package com.cgy.hupu.module.browser;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.cgy.hupu.R;
import com.cgy.hupu.module.BaseSwipeBackActivity;
import com.cgy.hupu.utils.StringUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BrowserActivity extends BaseSwipeBackActivity {

    public static void startActivity(Context context, String url, boolean external) {
        Intent intent = new Intent(context, BrowserActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("external", external);
        context.startActivity(intent);
    }

    public static void startActivity(Context context, String url) {
        startActivity(context, url, true);
    }

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private String url;
    private BrowserFragment mFragment;

    @Override
    public int initContentView() {
        return R.layout.base_content_toolbar_layout;
    }

    @Override
    public void initInjector() {

    }

    @Override
    public void initUiAndListener() {
        ButterKnife.bind(this);
        initToolBar(toolbar);
        url = getIntent().getStringExtra("url");
        mFragment = BrowserFragment.newInstance(url, "", getIntent().getBooleanExtra("external", true));
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, mFragment).commit();
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
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.removeGroup(R.id.browser);
        getMenuInflater().inflate(R.menu.menu_browser, menu);

        String shareContent = String.format("%s %s", getTitle() + "", url + "");
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareContent);

        MenuItem shareItem = menu.findItem(R.id.share);
        ShareActionProvider shareProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);
        shareProvider.setShareHistoryFileName("channe_share.xml");
        shareProvider.setShareIntent(shareIntent);

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.refresh) {
            if (mFragment != null) {
                mFragment.reload();
            }
        } else if (item.getItemId() == R.id.copy) {
            StringUtil.copy(this, url);
        } else if (item.getItemId() == R.id.to_browser) {
            try {

            } catch (Exception e) {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse(url);
                intent.setData(content_url);
                startActivity(intent);
            }

        }
        return super.onOptionsItemSelected(item);
    }
}
