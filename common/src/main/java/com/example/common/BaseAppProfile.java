package com.example.common;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import com.example.common.net.HttpClientWrapper;
import com.example.common.utils.DensityUtil;
import com.example.common.utils.init.CrashUtil;
import com.example.common.utils.init.T;
import com.tencent.bugly.crashreport.CrashReport;

/**
 * Created by xxdr on 2018/4/5.
 */

public abstract class BaseAppProfile {

  public static HttpClientWrapper app_client;

  private static BaseApplication sApplication;
  //private static DisplayMetrics sDisplayMetrics;
  private static String sPackageName;
  private static String sAppVersionName;
  private static int sAppVersionCode;

  public static void init(Application application) {
    sApplication = (BaseApplication) application;

    //WindowManager wm = (WindowManager) application.getSystemService(Context.WINDOW_SERVICE);
    //sDisplayMetrics = new DisplayMetrics();
    //wm.getDefaultDisplay().getMetrics(sDisplayMetrics);
    DensityUtil.setDensity(application);

    sPackageName = application.getPackageName();

    try {
      PackageManager pm = application.getPackageManager();
      PackageInfo pi = pm.getPackageInfo(application.getPackageName(), 0);
      sAppVersionName = pi.versionName;
      sAppVersionCode = pi.versionCode;
    } catch (PackageManager.NameNotFoundException e) {
      e.printStackTrace();
    }

    T.init(application);
    if (BuildConfig.IS_DEBUG) {
      CrashUtil.getInstance().init(application);
    }
    CrashReport.initCrashReport(application, "979abef51c", false);
  }

  public static BaseApplication getApplication() {
    return sApplication;
  }

  //public static DisplayMetrics getDisplayMetrics() {
  //  return sDisplayMetrics;
  //}

  public static String getPackageName() {
    return sPackageName;
  }

  public static String getAppVersionName() {
    return sAppVersionName;
  }

  public static int getAppVersionCode() {
    return sAppVersionCode;
  }
}
