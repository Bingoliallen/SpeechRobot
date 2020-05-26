package com.vma.speechrobot.view.activity;

import com.example.common.view.activity.splash.BaseSplashActivity;

public class SplashActivity extends BaseSplashActivity {

  @Override
  protected void onFinish() {
    MainActivity.launch(this);
  }
}
