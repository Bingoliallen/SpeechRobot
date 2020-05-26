package com.example.common;

import android.app.Application;
import com.example.common.net.HttpClientWrapper;

/**
 * Created by xxdr on 2017/11/4.
 */

public class CommonAppProfile extends BaseAppProfile {

  public static void init(Application application) {
    BaseAppProfile.init(application);
    CommonAppProfile.app_client = HttpClientWrapper.getDefault();
  }
}
