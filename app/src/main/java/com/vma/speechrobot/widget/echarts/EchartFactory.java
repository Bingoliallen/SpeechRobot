package com.vma.speechrobot.widget.echarts;

import android.content.Context;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import com.github.abel533.echarts.json.GsonOption;

public class EchartFactory {


  public static WebView getInstance(Context context) {
    WebView webView = new WebView(context);
    webView.setOverScrollMode(View.OVER_SCROLL_NEVER);
    webView.setHorizontalScrollBarEnabled(false);
    webView.setVerticalScrollBarEnabled(false);
    WebSettings webSettings = webView.getSettings();
    webSettings.setJavaScriptEnabled(true);
    webSettings.setDomStorageEnabled(true);
    webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
    webSettings.setSupportZoom(true);
    webSettings.setDisplayZoomControls(true);
    return webView;
  }

  public static void loadUrl(WebView webView) {
    webView.loadUrl("file:///android_asset/echart.html");
  }

  /**
   * 刷新图表
   * java调用js的loadEcharts方法刷新echart
   * 不能在第一时间就用此方法来显示图表，因为第一时间html的标签还未加载完成，不能获取到标签值
   */
  public static void refreshEchartsWithOption(WebView webView, GsonOption option) {
    if (option == null) {
      return;
    }
    String optionString = option.toString();
    String call = "javascript:loadEcharts('" + optionString + "');";
    webView.loadUrl(call);
  }
}