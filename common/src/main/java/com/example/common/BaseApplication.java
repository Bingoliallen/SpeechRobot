package com.example.common;


import android.app.Activity;
import android.app.Application;
import android.support.multidex.MultiDexApplication;
import android.support.v7.app.AppCompatDelegate;
import java.util.ArrayList;


/**
 * Created by xxdr on 2017/11/4.
 */

public abstract class BaseApplication extends MultiDexApplication {

  private final static ArrayList<Activity> activities = new ArrayList<>();

  static {
    AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
  }

  public void addActivity(Activity activity) {
    activities.add(activity);
  }

  public void removeActivity(Activity activity) {
    activities.remove(activity);
  }

  /**
   * 销毁所有的 activity
   */
  @Override
  public void onTerminate() {
    super.onTerminate();

    for (Activity activity : activities) {
      activity.finish();
    }

    System.exit(0);
  }
}
