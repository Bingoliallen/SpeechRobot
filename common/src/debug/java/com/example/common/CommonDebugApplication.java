package com.example.common;

import com.facebook.stetho.Stetho;
import com.squareup.leakcanary.LeakCanary;


/**
 * Created by xxdr on 2018/4/5.
 */

public class CommonDebugApplication extends BaseApplication {

  @Override
  public void onCreate() {
    super.onCreate();
    CommonDebugAppProfile.init(this);
    initStetho();
    LeakCanary.install(this);
  }

  private void initStetho() {
//        Stetho.initialize(
//                Stetho.newInitializerBuilder(this)
//                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
//                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
//                        .build());
    Stetho.initializeWithDefaults(this);
  }
}
