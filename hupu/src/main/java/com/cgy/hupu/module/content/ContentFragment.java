package com.cgy.hupu.module.content;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;

import com.cgy.hupu.R;
import com.cgy.hupu.module.BaseFragment;
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

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onError() {

    }

    @Override
    public void sendMessageToJS(String handlerName, Object object) {

    }

    @Override
    public void loadUrl(String url) {

    }

    @Override
    public void showReplyUi(String fid, String tid, String pid, String title) {

    }

    @Override
    public void showReportUi(String tid, String pid) {

    }

    @Override
    public void showBrowserUi(String url) {

    }

    @Override
    public void showThreadListUi(String fid) {

    }

    @Override
    public void showUserProfileUi(String uid) {

    }

    @Override
    public void showLoginUi() {

    }

    @Override
    public void onClose() {

    }

    @Override
    public void doPerform(Map<Object, Object> map) {

    }

    @Override
    public void onPageFinished(WebView webView, String str) {

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

    }
}
