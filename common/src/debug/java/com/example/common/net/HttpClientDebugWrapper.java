package com.example.common.net;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by xxdr on 2018/4/5.
 */

public class HttpClientDebugWrapper extends HttpClientWrapper {

  private HttpClientDebugWrapper() {
    super();
  }

  public static HttpClientDebugWrapper getDefault() {
    return new HttpClientDebugWrapper();
  }

  @Override
  protected OkHttpClient.Builder createOkHttpClientBuilder() {
    OkHttpClient.Builder builder = super.createOkHttpClientBuilder();
    addHttpLoggingInterceptor(builder);
    addStethoInterceptor(builder);
    return builder;
  }

  /**
   * 在 Debug 模式下加入 HttpLoggingInterceptor，打印请求日志
   * 已经在混淆文件中自动移除代码
   */
  private static OkHttpClient.Builder addHttpLoggingInterceptor(OkHttpClient.Builder builder) {
    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
    interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
    return builder.addInterceptor(interceptor);
  }

  /**
   * 在 Debug 模式下加入 Stetho 免代理抓包
   * 已经在混淆文件中自动移除代码
   */
  private static OkHttpClient.Builder addStethoInterceptor(OkHttpClient.Builder builder) {
    //builder.addInterceptor(new StethoInterceptor());
    return builder.addNetworkInterceptor(new StethoInterceptor());
  }
}
