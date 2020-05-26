package com.vma.speechrobot;

import com.example.common.CommonApplication;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.cookie.CookieJarImpl;
import com.zhy.http.okhttp.cookie.store.PersistentCookieStore;
//import com.facebook.stetho.Stetho;
//import com.facebook.stetho.okhttp3.StethoInterceptor;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class SpeechRobotApplication extends CommonApplication {

  public static OkHttpClient okHttpClient;
  @Override
  public void onCreate() {
    super.onCreate();
    EventBus.builder().addIndex(new MyEventBusIndex()).installDefaultEventBus();
    //  一般使用默认初始化配置足够使用
    //Stetho.initializeWithDefaults(this);
   // initOkHttp();

    CookieJarImpl cookieJar = new CookieJarImpl(new PersistentCookieStore(getApplicationContext()));
    OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .cookieJar(cookieJar)
            .connectTimeout(2, TimeUnit.SECONDS)
            .readTimeout(2, TimeUnit.SECONDS)
            .build();
    OkHttpUtils.initClient(okHttpClient);
    UMConfigure.init(this, "5bf2d7f5b465f52bd00003b4", "umeng", UMConfigure.DEVICE_TYPE_PHONE, "be7304bb2ee49cfe2f2d7f043283d0fc");
    PushAgent mPushAgent = PushAgent.getInstance(this);
    //注册推送服务，每次调用register方法都会回调该接口
    mPushAgent.register(new IUmengRegisterCallback() {
      @Override
      public void onSuccess(String deviceToken) {
        //注册成功会返回device token
      }
      @Override
      public void onFailure(String s, String s1) {
      }
    });
    UMConfigure.setLogEnabled(false);

  }

 /* private void initOkHttp() {
    okHttpClient =  new OkHttpClient()
            .newBuilder()
            .addNetworkInterceptor(new StethoInterceptor()) // 这里添加一个拦截器即可
            .build();
  }*/




}
