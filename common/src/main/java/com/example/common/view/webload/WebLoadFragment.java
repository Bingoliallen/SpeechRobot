package com.example.common.view.webload;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.DownloadListener;
import android.webkit.GeolocationPermissions;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import com.example.common.BaseAppProfile;
import com.example.common.R;
import com.example.common.consts.CommonSpaceConst;
import com.example.common.presenter.impl.BasePresenter;
import com.example.common.utils.init.T;
import com.example.common.utils.wrapper.IntentWrapper;
import com.example.common.view.activity.BaseActivity;
import com.example.common.view.fragment.BaseFragment;


/**
 * Created by hang on 2015/11/19.
 * 加载网页
 */
public class WebLoadFragment extends BaseFragment {

  public final static String PARAMS_TITLE = "params_title";
  public final static String PARAMS_URL = "params_url";
  public final static String PARAMS_DATA = "params_data";
  public final static String PARAMS_SHOW_PROGRESS = "params_show_progress";

  private ProgressBar mProgressBar;
  private WebView mWebView;
  private String mTitle;
  private String mUrl;
  private String mData;
  private boolean mShowProgress;

  public static synchronized WebLoadFragment getInstance(String url) {
    return getInstance("", url);
  }

  public static synchronized WebLoadFragment getInstance(String title, String url) {
    return getInstance(title, url, null);
  }

  public static synchronized WebLoadFragment getInstance(String title, String url, String data) {
    return getInstance(title, url, data, true);
  }

  public static synchronized WebLoadFragment getInstance(String title, String url, String data,
      boolean showProgress) {
    Bundle args = new Bundle();
    args.putString(PARAMS_TITLE, title);
    args.putString(PARAMS_URL, url);
    args.putString(PARAMS_DATA, data);
    args.putBoolean(PARAMS_SHOW_PROGRESS, showProgress);
    return getInstance(args);
  }

  public static synchronized WebLoadFragment getInstance(Bundle args) {
    WebLoadFragment instance = new WebLoadFragment();
    instance.setArguments(args);
    return instance;
  }

  @Override
  protected BasePresenter createPresenter() {
    return null;
  }

  @Override
  protected int getContentViewId() {
    return R.layout.common_fragment_web_view;
  }

  @Override
  protected void initView() {
    mShowProgress = getArguments().getBoolean(PARAMS_SHOW_PROGRESS);
    if (mShowProgress) {
      mProgressBar = (ProgressBar) findViewById(R.id.process_bar);
      mProgressBar.setMax(100);
    }
    // 手动添加 WebView，防止内存泄漏
    mWebView = new WebView(BaseAppProfile.getApplication());
    mWebView.setOverScrollMode(View.OVER_SCROLL_NEVER);
    mWebView.setHorizontalScrollBarEnabled(false);
    mWebView.setVerticalScrollBarEnabled(false);
    RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    lp.addRule(RelativeLayout.BELOW, R.id.process_bar);
    ((ViewGroup) mView).addView(mWebView, lp);
    setUpWebView();
  }

  @Override
  protected void initData() {
    super.initData();
    mTitle = getArguments().getString(PARAMS_TITLE);
    mUrl = getArguments().getString(PARAMS_URL);
    mData = getArguments().getString(PARAMS_DATA);

    if (getArguments() != null) {
      if (TextUtils.isEmpty(mUrl)) {
        mWebView.loadDataWithBaseURL(null, mData, "text/html", "utf-8", null);
      } else {
        mWebView.loadUrl(mUrl);
      }
    }
  }

  @Override
  public void onResume() {
    if (mWebView != null) {
      mWebView.onResume();
      mWebView.resumeTimers();
    }
    super.onResume();
  }

  @Override
  public void onPause() {
    if (mWebView != null) {
      //mWebView.stopLoading();
      mWebView.onPause();
      mWebView.pauseTimers();
    }
    super.onPause();
  }

  @Override
  public void onDestroy() {
    if (mWebView != null) {
      mWebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
      mWebView.clearHistory();
      ((ViewGroup) mWebView.getParent()).removeView(mWebView);
      mWebView.destroy();
      mWebView = null;
    }
    super.onDestroy();
  }

  /**
   * 网页返回
   */
  public void goBack() {
    if (mWebView.canGoBack()) {
      mWebView.goBack();
    } else {
      mWebView.stopLoading();
      mContext.finish();
    }
  }

  /**
   * 配置 WebView
   */
  private void setUpWebView() {
    setUpCache();
    if (Build.VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
      mWebView.getSettings().setLoadsImagesAutomatically(true);
    } else {
      mWebView.getSettings().setLoadsImagesAutomatically(false);
    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      // Android 5.0上WebView默认不允许加载Http与Https混合内容
      mWebView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
    }
    mWebView.getSettings().setJavaScriptEnabled(true);                      // 开启javascript
    mWebView.addJavascriptInterface(new JSClient(), "xxdr");                // JS交互
    mWebView.getSettings().setDefaultTextEncodingName("UTF-8");             // 设置编码
    mWebView.getSettings().setAllowFileAccess(true);                        // 支持文件流
    mWebView.getSettings().setSupportZoom(true);                            // 设置可以支持缩放
    mWebView.getSettings().setBuiltInZoomControls(true);                    // 设置出现缩放工具
    mWebView.getSettings().setDisplayZoomControls(false);                   // 隐藏缩放工具
    mWebView.getSettings().setUseWideViewPort(true);                        // 调整到适合WebView大小
    mWebView.getSettings().setLoadWithOverviewMode(true);                   // 调整到适合WebView大小
    mWebView.getSettings()
        .setDefaultZoom(
            WebSettings.ZoomDensity.FAR);                                   // 屏幕自适应网页,如果没有这个，在低分辨率的手机上显示可能会异常
    mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
    mWebView.getSettings().setGeolocationEnabled(true);                     // 启用地理定位
    mWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);                  // 启用硬件加速
    mWebView.requestFocus();

    mWebView.setWebViewClient(new MyWebViewClient());
    mWebView.setWebChromeClient(new MyWebChromeClient());
    mWebView.setDownloadListener(new MyDownloadListener());
  }

  /**
   * 设置一些缓存选项
   */
  private void setUpCache() {
    mWebView.getSettings().setDomStorageEnabled(true);                      // 开启DomStorage

    mWebView.getSettings().setDatabaseEnabled(true);                        // 启用数据库
    String dir = BaseAppProfile.getApplication().getDir("WebView_DB", Context.MODE_PRIVATE)
        .getPath();
    mWebView.getSettings().setDatabasePath(dir);

    mWebView.getSettings().setAppCacheEnabled(true);                        // 开启缓存机制
    String appCachePath = BaseAppProfile.getApplication()
        .getDir("WebView_Cache", Context.MODE_PRIVATE).getPath();
    mWebView.getSettings().setAppCachePath(appCachePath);
    mWebView.getSettings().setAppCacheMaxSize(5 * CommonSpaceConst.M);
  }

  private class MyWebViewClient extends WebViewClient {

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
      super.onPageStarted(view, url, favicon);
      if (mShowProgress) {
        mProgressBar.setVisibility(View.VISIBLE);
      }
      mWebView.getSettings().setBlockNetworkImage(true);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
      super.onPageFinished(view, url);
      if (mShowProgress) {
        mProgressBar.setVisibility(View.GONE);
      }
      mWebView.getSettings().setBlockNetworkImage(false);
      if (!mWebView.getSettings().getLoadsImagesAutomatically()) {
        mWebView.getSettings().setLoadsImagesAutomatically(true);
      }
    }

    @Override
    public void onReceivedError(WebView view, int errorCode, String description,
        String failingUrl) {
      super.onReceivedError(view, errorCode, description, failingUrl);
      if (mShowProgress) {
        mProgressBar.setVisibility(View.GONE);
      }
      T.showShort("加载失败，请稍候再试");
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
      if (url.startsWith("http:") || url.startsWith("https:")) {
        view.loadUrl(url);
        return false;
      }

      // Otherwise allow the OS to handle things like tel, mailto, etc.
      Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
      boolean result = IntentWrapper.startActivity(mContext, intent);
      return result;
    }

    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
      // 接受所有网站的证书
      handler.proceed();
    }
  }

  private class MyWebChromeClient extends WebChromeClient {

    @Override
    public void onReceivedTitle(WebView view, String title) {
      super.onReceivedTitle(view, title);
      if (TextUtils.isEmpty(mTitle)) {
        BaseActivity activity = (BaseActivity) mContext;
        if (activity.titleBar != null) {
          activity.titleBar.setTitleTextBold(true);
          if (title.length() > 10) {
            activity.titleBar.setTitleText(String.format("%s...", title.substring(0, 10)));
          } else {
            activity.titleBar.setTitleText(title);
          }
        }
      }
    }

    @Override
    public void onGeolocationPermissionsShowPrompt(String origin,
        GeolocationPermissions.Callback callback) {
      callback.invoke(origin, true, false);
      super.onGeolocationPermissionsShowPrompt(origin, callback);
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
      super.onProgressChanged(view, newProgress);
      if (mShowProgress) {
        mProgressBar.setProgress(newProgress);
      }
    }
  }

  private class MyDownloadListener implements DownloadListener {

    @Override
    public void onDownloadStart(String url, String userAgent, String contentDisposition,
        String mimetype, long contentLength) {
      Uri uri = Uri.parse(url);
      Intent intent = new Intent(Intent.ACTION_VIEW, uri);
      IntentWrapper.startActivity(mContext, intent);
    }
  }

  class JSClient {

    @SuppressLint("MissingPermission")
    @JavascriptInterface
    public String getIMEI() {
      TelephonyManager tm = (TelephonyManager) BaseAppProfile.getApplication()
          .getSystemService(Context.TELEPHONY_SERVICE);
      return tm.getDeviceId();
    }

    @JavascriptInterface
    public void payComplete() {
      WebLoadFragment.this.mContext.setResult(Activity.RESULT_OK);
      WebLoadFragment.this.mContext.finish();
    }
  }
}