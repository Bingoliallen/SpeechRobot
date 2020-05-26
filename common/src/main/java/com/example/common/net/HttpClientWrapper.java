package com.example.common.net;

import android.util.ArrayMap;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by xxdr on 2017/7/13.
 */

public class HttpClientWrapper {

  public Retrofit retrofit;

  private ArrayMap<Class, Object> mApis;

  public static HttpClientWrapper getDefault() {
    return new HttpClientWrapper();
  }

  /**
   * 生成一个默认的 Retrofit.Builder
   */
  public Retrofit.Builder createRetrofitBuilder(String host) {
    return new Retrofit.Builder()
        .baseUrl(host)
        .client(createOkHttpClientBuilder().build())
        .addConverterFactory(new NullOnEmptyConverterFactory())
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create());
  }

  public <T> T getApi(Class<T> apiClass) {
    if (mApis == null) {
      mApis = new ArrayMap<>();
    }
    if (mApis.get(apiClass) == null) {
      mApis.put(apiClass, retrofit.create(apiClass));
    }
    return (T) mApis.get(apiClass);
  }

  protected HttpClientWrapper() {
    //clientBuilder.addInterceptor(new SignInterceptor());
    retrofit = createRetrofitBuilder(HttpConfig.HOST).build();
  }

  /**
   * 生成一个默认的 OkHttpClient.Builder
   */
  protected OkHttpClient.Builder createOkHttpClientBuilder() {
    OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
    clientBuilder.connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(20, TimeUnit.SECONDS)
        .writeTimeout(20, TimeUnit.SECONDS);
    return clientBuilder;
  }
}
