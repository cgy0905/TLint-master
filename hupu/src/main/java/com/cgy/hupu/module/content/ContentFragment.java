package com.cgy.hupu.module.content;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;

import com.cgy.hupu.Constants;
import com.cgy.hupu.R;
import com.cgy.hupu.module.BaseFragment;
import com.cgy.hupu.module.browser.BrowserActivity;
import com.cgy.hupu.module.login.LoginActivity;
import com.cgy.hupu.module.post.PostActivity;
import com.cgy.hupu.module.report.ReportActivity;
import com.cgy.hupu.module.userprofile.UserProfileActivity;
import com.cgy.hupu.utils.SettingPrefUtil;
import com.cgy.hupu.widget.H5Callback;
import com.cgy.hupu.widget.JockeyJsWebView;

import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cgy on 2019/4/18.
 */
public class ContentFragment extends BaseFragment implements ContentPagerContract.View,
        H5Callback, JockeyJsWebView.OnScrollChangedCallback {

    public static ContentFragment newInstance(String fid, String tid, String pid, int page) {
        ContentFragment mFragment = new ContentFragment();
        Bundle bundle = new Bundle();
        bundle.putString("fid", fid);
        bundle.putString("tid", tid);
        bundle.putString("pid", pid);
        bundle.putInt("page", page);
        mFragment.setArguments(bundle);
        return mFragment;
    }

    @Override
    public void initInjector() {
        getComponent(ContentComponent.class).inject(this);
    }

    @BindView(R.id.webView)
    JockeyJsWebView webView;

    @Inject
    ContentPagerPresenter mContentPresenter;

    private String tid;
    private String fid;
    private String pid;
    private int page;

    @Override
    public int initContentView() {
        return R.layout.fragment_content;
    }

    @Override
    public void getBundle(Bundle bundle) {
        tid = bundle.getString("tid");
        fid = bundle.getString("fid");
        pid = bundle.getString("pid");
        page = bundle.getInt("page");
    }

    @Override
    public void initUI(View view) {
        ButterKnife.bind(this, view);
        mContentPresenter.attachView(this);
        webView.setCallback(this);
        webView.initJockey();
        webView.setOnScrollChangedCallback(this);
        webView.addJavascriptInterface(mContentPresenter.getJavaScriptInterface(), "HuPuBridge");

    }

    @Override
    public void initData() {
        webView.loadUrl(SettingPrefUtil.getNightModel(getActivity()) ?
                "file:///android_asset/hupu_thread_night.html"
                : "file://android_asset/hupu_thread.html");
    }

    @Override
    public void onReloadClicked() {
        mContentPresenter.onReLoad();
    }

    @Override
    public void showLoading() {
        showProgress(true);
    }

    @Override
    public void hideLoading() {
        showContent(true);
    }

    @Override
    public void onError() {
        setEmptyText("数据加载失败");
        showError(true);
    }

    @Override
    public void sendMessageToJS(String handlerName, Object object) {
        webView.sendMessageToJS(handlerName, object);
    }

    @Override
    public void loadUrl(String url) {
        webView.loadUrl(url);
    }

    @Override
    public void showReplyUi(String fid, String tid, String pid, String title) {
        PostActivity.startActivity(getActivity(), Constants.TYPE_REPLY, fid, tid, pid, title);
    }

    @Override
    public void showReportUi(String tid, String pid) {
        ReportActivity.startActivity(getActivity(), tid, pid);
    }

    @Override
    public void showBrowserUi(String url) {
        BrowserActivity.startActivity(getActivity(), url);
    }

    @Override
    public void showContentUi(String tid, String pid, int page) {
        ContentActivity.startActivity(getActivity(), "", fid, pid, page);
    }

    @Override
    public void showThreadListUi(String fid) {

    }

    @Override
    public void showUserProfileUi(String uid) {
        UserProfileActivity.startActivity(getActivity(), uid);
    }

    @Override
    public void showLoginUi() {
        LoginActivity.startActivity(getActivity());
    }

    @Override
    public void onClose() {
        getActivity().finish();
    }

    @Override
    public void doPerform(Map<Object, Object> map) {

    }

    @Override
    public void onPageFinished(WebView webView, String str) {
        mContentPresenter.onThreadInfoReceive(tid, fid, pid, page);
    }

    @Override
    public void onPageStarted(WebView webView, String str, Bitmap bitmap) {

    }

    @Override
    public void onReceivedError(WebView webView, WebResourceRequest request, WebResourceError error) {

    }

    @Override
    public void setJockeyEvents() {

    }

    @Override
    public void openBrowser(String url) {

    }

    @Override
    public void onScroll(int dx, int dy) {
        if (Math.abs(dy) > 4) {
            ContentActivity activity = (ContentActivity) getActivity();
            if (activity != null) {
                activity.setFloatingMenuVisibility(dy < 0);
            }
        }
    }
}
