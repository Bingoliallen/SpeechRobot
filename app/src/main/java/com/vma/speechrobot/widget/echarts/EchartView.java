package com.vma.speechrobot.widget.echarts;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.github.abel533.echarts.json.GsonOption;

public class EchartView extends WebView {

  private static final String TAG = "EchartView";

  private GsonOption gsonOption;

  public EchartView(Context context) {
    this(context, null);
  }

  public EchartView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public EchartView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  private void init() {
    WebSettings webSettings = getSettings();
    webSettings.setJavaScriptEnabled(true);
    webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
    webSettings.setSupportZoom(true);
    webSettings.setDisplayZoomControls(true);
    setWebViewClient(new WebViewClient() {
      @Override
      public void onPageFinished(WebView view, String url) {
        view.loadUrl("javascript:loadEcharts('" + gsonOption.toString() + "');");
        super.onPageFinished(view, url);
      }

      @TargetApi(Build.VERSION_CODES.LOLLIPOP)
      @Override
      public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        view.loadUrl(request.getUrl().toString());
        return true;
      }

      @Override
      public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;
      }
    });
  }

  /**
   * 刷新图表
   * java调用js的loadEcharts方法刷新echart
   * 不能在第一时间就用此方法来显示图表，因为第一时间html的标签还未加载完成，不能获取到标签值
   */
  public void refreshEchartsWithOption(GsonOption option) {
    if (option == null) {
      return;
    }
    gsonOption = option;
    loadUrl("file:///android_asset/echart.html");
  }
}
