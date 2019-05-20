package com.cgy.hupu.widget;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.cgy.hupu.AppManager;
import com.cgy.hupu.Constants;
import com.cgy.hupu.MyApplication;
import com.cgy.hupu.components.UserStorage;
import com.cgy.hupu.components.retrofit.RequestHelper;
import com.cgy.hupu.module.browser.BrowserActivity;
import com.cgy.hupu.module.content.ContentActivity;
import com.cgy.hupu.module.imagepreview.ImagePreviewActivity;
import com.cgy.hupu.module.login.LoginActivity;
import com.cgy.hupu.module.post.PostActivity;
import com.cgy.hupu.module.report.ReportActivity;
import com.cgy.hupu.module.thread.list.ThreadListActivity;
import com.cgy.hupu.module.userprofile.UserProfileActivity;
import com.cgy.hupu.utils.StringUtil;
import com.cgy.hupu.utils.ToastUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

/**
 * @author cgy
 * @desctiption
 * @date 2019/5/17 17:49
 */
public class HuPuWebView extends WebView {

    private String basicUA;
    private Map<String, String> header;

    @Inject
    UserStorage mUserStorage;
    @Inject
    RequestHelper mRequestHelper;


    public HuPuWebView(Context context) {
        super(context);
        init();
    }

    public HuPuWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void setCallback(HuPuWebViewCallback callback) {
        this.callback = callback;
    }

    private void init() {
        ((MyApplication) getContext().getApplicationContext()).getApplicationComponent().inject(this);
        WebSettings settings = getSettings();
        settings.setSupportZoom(false); //支持缩放 默认为true 是下面api的前提
        settings.setBuiltInZoomControls(false);//设置内置的缩放控件 若为false 则该WebView不可缩放
        settings.setJavaScriptEnabled(true);//支持与js交互
        settings.setAllowFileAccess(true);//设置可以访问文件
        settings.setSupportMultipleWindows(false);//多窗口
        settings.setJavaScriptCanOpenWindowsAutomatically(true);    //支持通过js打开新窗口
        settings.setDomStorageEnabled(true);    // 开启 DOM storage API 功能
        //缓存模式如下：
        //LOAD_CACHE_ONLY: 不使用网络，只读取本地缓存数据
        //LOAD_DEFAULT: （默认）根据cache-control决定是否从网络上取数据。
        //LOAD_NO_CACHE: 不使用缓存，只从网络获取数据.
        //LOAD_CACHE_ELSE_NETWORK，只要本地有，无论是否过期，或者no-cache，都使用缓存中的数据。

        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//关闭WebView中缓存
        settings.setUseWideViewPort(true);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.ECLAIR_0_1) {
            settings.setAppCacheEnabled(true);//开启 Application Caches 功能
            settings.setLoadWithOverviewMode(true);//将图片调整到适合WebView的大小
        }
        String path = getContext().getFilesDir().getPath();
        settings.setGeolocationEnabled(true);//启用H5的地理定位服务
        settings.setGeolocationDatabasePath(path);
        this.basicUA = settings.getUserAgentString() + " kanqiu/7.05.6303/7059";
        setBackgroundColor(0);
        initWebViewClient();
        try {
            if (mUserStorage.isLogin()) {
                String token = mUserStorage.getToken();
                CookieManager cookieManager = CookieManager.getInstance();
                cookieManager.setCookie("http://bbs.mobileapi.hupu.com",
                        "u=" + URLEncoder.encode(mUserStorage.getCookie(), "utf-8"));
                cookieManager.setCookie("http://bbs.mobileapi.hupu.com",
                        "_gamesu=" + URLEncoder.encode(token, "utf-8"));
                cookieManager.setCookie("http://bbs.mobileapi.hupu.com", "_inKanqiuApp=1");
                cookieManager.setCookie("http://bbs.mobileapi.hupu.com", "_kanqiu=1");
                CookieSyncManager.getInstance().sync();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void initWebViewClient() {
        CookieManager.getInstance().setAcceptCookie(true);
        setWebViewClient(new HuPuWebClient());
    }

    private class HuPuWebClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Uri uri = Uri.parse(url);
            String scheme = uri.getScheme();
            if (url.startsWith("hupu") || url.startsWith("kanqiu")) {
                if (scheme != null) {
                    handleScheme(scheme, url);
                }
            } else if (scheme.equals("http") || scheme.equals("https")) {
                BrowserActivity.startActivity(getContext(), url);
            }
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if (callback != null) {
                callback.onFinish();
            }
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            if (callback != null) {
                callback.onError();
            }
        }
    }

    private void handleScheme(String scheme, String url) {
        if (scheme != null) {
            if (scheme.equalsIgnoreCase("kanqiu")) {
                handleKanQiu(url);
            } else if (scheme.equalsIgnoreCase("browser")
                    || scheme.equalsIgnoreCase("http")
                    || scheme.equalsIgnoreCase("https")) {
                BrowserActivity.startActivity(getContext(), url);
            } else if (scheme.equalsIgnoreCase("hupu")) {
                try {
                    JSONObject object = new JSONObject(Uri.decode(url.substring("hupu".length() + 3)));
                    String method = object.optString("method");
                    String successcb = object.optString("successcb");
                    handleHuPu(method, object.getJSONObject("data"), successcb);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    }

    private void handleKanQiu(String url) {
        if (url.contains("topic")) {
            Uri uri = Uri.parse(url);
            String tid = uri.getLastPathSegment();
            String page = uri.getQueryParameter("page");
            String pid = uri.getQueryParameter("pid");
            ContentActivity.startActivity(getContext(), "", tid, pid,
                    TextUtils.isEmpty(page) ? 1 : Integer.valueOf(page));
        } else if (url.contains("board")) {
            String boardId = url.substring(url.lastIndexOf("/") + 1);
            ThreadListActivity.startActivity(getContext(), boardId);
        } else if (url.contains("people")) {
            String uid = url.substring(url.lastIndexOf("/") + 1);
            UserProfileActivity.startActivity(getContext(), uid);
        }
    }

    private void handleHuPu(String method, JSONObject data, String successcb) throws Exception {
        switch (method) {
            case "bridgeReady":
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("hybridVer", "1.0");
                    jsonObject.put("supportAjax", true);
                    jsonObject.put("appVer", "7.0.5.6303");
                    jsonObject.put("appName", "com.hupu.games");
                    jsonObject.put("lowDevice", false);
                    jsonObject.put("scheme", "hupu");
                    jsonObject.put("did", mRequestHelper.getDeviceId());
                    jsonObject.put("platform", "Android");
                    jsonObject.put("device", Build.PRODUCT);
                    jsonObject.put("osVer", Build.VERSION.RELEASE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String js = "javascript:HupuBridge._handle_('"
                        + successcb
                        + "','"
                        + jsonObject.toString()
                        + "','null','null');";
                loadUrl(js);
                break;
            case "hupu.ui.updatebbspager":
                int page = data.getInt("page");
                int total = data.getInt("total");
                if (callback != null) {
                    callback.onUpdatePager(page, total);
                }
                break;
            case "hupu.ui.bbsreply":
                boolean open = data.getBoolean("open");
                JSONObject extra = data.getJSONObject("extra");
                String tid = extra.getString("tid");
                long pid = extra.getLong("pid");
                String username = extra.getString("username");
                String content = extra.getString("content");
                if (open) {
                    PostActivity.startActivity(getContext(), Constants.TYPE_REPLY, "", tid, String.valueOf(pid), content);
                }
                break;
            case "hupu.album.view":
                int index = data.getInt("index");
                JSONArray images = data.getJSONArray("images");
                ArrayList<String> extraPics = new ArrayList<>();
                for (int i = 0; i < images.length(); i++) {
                    JSONObject image = images.getJSONObject(i);
                    extraPics.add(image.getString("url"));
                }
                ImagePreviewActivity.startActivity(getContext(), extraPics.get(index), extraPics);
                break;
            case "hupu.ui.copy":
                String copy = data.getString("content");
                StringUtil.copy(getContext(), copy);
                break;
            case "hupu.ui.report":
                JSONObject reportExtra = data.getJSONObject("extra");
                String reportTid = reportExtra.getString("tid");
                long reportPid = reportExtra.getLong("pid");
                ReportActivity.startActivity(getContext(), reportTid, String.valueOf(reportPid));
                break;
            case "hupu.user.login":
                LoginActivity.startActivity(getContext());
                ToastUtil.showToast("请先登录");
                break;
            case "hupu.ui.pageclose":
                AppManager.getAppManager().finishActivity();
                break;
        }
    }

    private void setUA(int i) {
        if (this.basicUA != null) {
            getSettings().setUserAgentString(this.basicUA + "isp/" + i + " network/" + i);
        }
    }

    public void loadUrl(String url) {
        setUA(-1);
        if (header == null) {
            header = new HashMap<>();
            header.put("Accept-Encoding", "gzip");
        }
        super.loadUrl(url, header);
    }


    private HuPuWebViewCallback callback;

    public interface HuPuWebViewCallback {
        void onFinish();

        void onUpdatePager(int page, int total);

        void onError();
    }

    private JockeyJsWebView.OnScrollChangedCallback mOnScrollChangedCallback;

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

        if (mOnScrollChangedCallback != null) {
            mOnScrollChangedCallback.onScroll(l - oldl, t - oldt);
        }
    }

    public JockeyJsWebView.OnScrollChangedCallback getOnScrollChangedCallback() {
        return mOnScrollChangedCallback;
    }

    /**
     * Implement in the activity/fragment/view that you want to listen to the webview
     */
    public interface OnScrollChangedCallback {
        void onScroll(int dx, int dy);
    }
}
