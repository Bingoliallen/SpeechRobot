package com.example.common;

import android.app.Application;
import com.example.common.net.HttpClientDebugWrapper;

/**
 * Created by xxdr on 2018/4/5.
 */

public class CommonDebugAppProfile extends BaseAppProfile {

  public static void init(Application application) {
    BaseAppProfile.init(application);
    BaseAppProfile.app_client = HttpClientDebugWrapper.getDefault();
  }
}
