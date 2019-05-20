package com.cgy.hupu.module.browser;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.cgy.hupu.R;
import com.cgy.hupu.module.BaseFragment;
import com.cgy.hupu.widget.HuPuWebView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by cgy on 2018/12/30 下午 3:40.
 */
public class BrowserFragment extends BaseFragment {


    public static BrowserFragment newInstance(String url, String title) {
        return newInstance(url, title, false);
    }

    public static BrowserFragment newInstance(String url, String title, boolean external) {
        BrowserFragment mFragment = new BrowserFragment();
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        bundle.putString("title", title);
        bundle.putBoolean("external", external);
        mFragment.setArguments(bundle);
        return mFragment;
    }

    @BindView(R.id.webView)
    HuPuWebView webView;
    @BindView(R.id.progress)
    ProgressBar progress;
    Unbinder unbinder;

    private String url;
    private String title;
    private boolean external;

    @Override
    public void initInjector() {

    }

    @Override
    public int initContentView() {
        return R.layout.fragment_browser;
    }

    @Override
    public void getBundle(Bundle bundle) {
        url = bundle.getString("url");
        title = bundle.getString("title");
        external = bundle.getBoolean("external");
    }

    @Override
    public void initUI(View view) {
        unbinder = ButterKnife.bind(this, view);
        showContent(true);
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onReceivedTitle(WebView view, String title) {
                if (!TextUtils.isEmpty(BrowserFragment.this.title)) {
                    getActivity().setTitle(BrowserFragment.this.title);
                }
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                progress.setProgress(newProgress);
                if (newProgress == 100) {
                    progress.setVisibility(View.GONE);
                } else {
                    progress.setVisibility(View.VISIBLE);
                }
            }
        });
        if (external) {
            webView.setWebViewClient(new WebViewClient(){
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    if (url != null) view.loadUrl(url);
                    return true;
                }
            });
        }
    }

    @Override
    public void initData() {
        webView.loadUrl(url);
    }

    public void reload() {
        webView.reload();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
