package com.example.common;

/**
 * Created by xxdr on 2018/4/5.
 */

public class CommonApplication extends BaseApplication {

  @Override
  public void onCreate() {
    super.onCreate();
    CommonAppProfile.init(this);
  }
}
