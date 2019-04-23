package com.cgy.hupu.widget;

import android.graphics.Bitmap;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;

import java.util.Map;

/**
 * Created by cgy on 2019/4/22.
 */
public interface H5Callback {
    void doPerform(Map<Object, Object> map);

    void onPageFinished(WebView webView, String str);

    void onPageStarted(WebView webView, String str, Bitmap bitmap);

    void onReceivedError(WebView webView, WebResourceRequest request, WebResourceError error);

    void setJockeyEvents();

    void openBrowser(String url);
}
