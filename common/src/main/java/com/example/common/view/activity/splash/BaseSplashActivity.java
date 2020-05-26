package com.example.common.view.activity.splash;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by xxdr on 2018/3/13.
 */

public abstract class BaseSplashActivity extends AppCompatActivity {

  public static final int SHOW_SECONDS = 2;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    new Handler().postDelayed(new Runnable() {
      @Override
      public void run() {
        onFinish();
        finish();
      }
    }, SHOW_SECONDS * 1000);
  }

  protected abstract void onFinish();

  /**
   * SplashActivity 采用这个主题
   */
  // 在 styles.xml 中添加，并在 AndroidManifest.xml 中采用
  // <style name="BaseSplashTheme" parent="AppTheme">
  //     <item name="android:windowBackground">@drawable/common_base_splash</item>
  //     <item name="windowActionBar">false</item>
  //     <item name="windowNoTitle">true</item>
  //     <item name="android:windowFullscreen">true</item>
  // </style>
}
