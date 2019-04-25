package com.cgy.hupu.module.content;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;

import com.alibaba.fastjson.JSON;
import com.cgy.hupu.Constants;
import com.cgy.hupu.R;
import com.cgy.hupu.bean.ImagePreview;
import com.cgy.hupu.components.jockeyjs.JockeyHandler;
import com.cgy.hupu.module.BaseFragment;
import com.cgy.hupu.module.browser.BrowserActivity;
import com.cgy.hupu.module.imagepreview.ImagePreviewActivity;
import com.cgy.hupu.module.login.LoginActivity;
import com.cgy.hupu.module.post.PostActivity;
import com.cgy.hupu.module.report.ReportActivity;
import com.cgy.hupu.module.thread.list.ThreadListActivity;
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
        ContentFragment fragment = new ContentFragment();
        Bundle bundle = new Bundle();
        bundle.putString("fid", fid);
        bundle.putString("tid", tid);
        bundle.putString("pid", pid);
        bundle.putInt("page", page);
        fragment.setArguments(bundle);
        return fragment;
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
        ThreadListActivity.startActivity(getActivity(), fid);
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
        webView.onJSEvent("showImg", new JockeyHandler() {
            @Override
            protected void doPerform(Map<Object, Object> payload) {
                ImagePreview preview = JSON.parseObject(JSON.toJSONString(payload), ImagePreview.class);
                ImagePreviewActivity.startActivity(getActivity(), preview.imgs.get(preview.index),
                        preview.imgs);
            }
        });
        webView.onJSEvent("showUrl", new JockeyHandler() {
            @Override
            protected void doPerform(Map<Object, Object> payload) {
                mContentPresenter.handlerUrl(((String)payload.get("url")));
            }
        });
        webView.onJSEvent("showUser", new JockeyHandler() {
            @Override
            protected void doPerform(Map<Object, Object> payload) {
                UserProfileActivity.startActivity(getActivity(), ((String) payload.get("uid")));
            }
        });
        webView.onJSEvent("showMenu", new JockeyHandler() {
            @Override
            protected void doPerform(Map<Object, Object> payload) {
                int area = Integer.valueOf((String)payload.get("area"));
                int index = Integer.valueOf((String) payload.get("index"));
                String type = (String) payload.get("type");
                switch (type) {
                    case "light":
                        mContentPresenter.addLight(area, index);
                        break;
                    case "rulight":
                        mContentPresenter.addRuLight(area, index);
                        break;
                    case "reply":
                        mContentPresenter.onReply(area, index);
                        break;
                    case "report":
                        mContentPresenter.onReport(area, index);
                        break;
                }
            }
        });
    }

    @Override
    public void openBrowser(String url) {
        mContentPresenter.handlerUrl(url);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        mContentPresenter.detachView();
        if (webView != null) {
            webView.destroy();
        }
    }
}
