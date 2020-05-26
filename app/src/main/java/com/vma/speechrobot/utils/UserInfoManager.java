package com.vma.speechrobot.utils;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.example.common.BaseAppProfile;
import com.example.common.consts.CommonPreferenceConst;
import com.example.common.consts.CommonPreferenceConst.PREFERENCE_KEY;
import com.example.common.utils.wrapper.PreferenceWrapper;
import com.vma.speechrobot.bean.UserInfoBean;
import com.vma.speechrobot.view.activity.LoginActivity;

/**
 * Created by xxdr on 2018/4/29.
 */

public class UserInfoManager {

  private static String sMacKey;
  private static UserInfoBean sUserInfo;

  public static void save(UserInfoBean userInfo) {
    sMacKey = userInfo.mac_key;
    PreferenceWrapper
        .setString(BaseAppProfile.getApplication(), CommonPreferenceConst.MAIN_PREFERENCE_FILE,
            PREFERENCE_KEY.MAC_KEY, userInfo.mac_key);
    PreferenceWrapper
        .setString(BaseAppProfile.getApplication(), CommonPreferenceConst.MAIN_PREFERENCE_FILE,
            PREFERENCE_KEY.NICK_NAME, userInfo.name);
    PreferenceWrapper
        .setString(BaseAppProfile.getApplication(), CommonPreferenceConst.MAIN_PREFERENCE_FILE,
            PREFERENCE_KEY.AVATAR, userInfo.icon);
    PreferenceWrapper
            .setString(BaseAppProfile.getApplication(), CommonPreferenceConst.MAIN_PREFERENCE_FILE,
                    PREFERENCE_KEY.EXE, userInfo.execl_url);
  }

  public static UserInfoBean get() {
    if (sUserInfo == null) {
      sUserInfo = new UserInfoBean();
    }
    sUserInfo.name = PreferenceWrapper
        .getString(BaseAppProfile.getApplication(), CommonPreferenceConst.MAIN_PREFERENCE_FILE,
            PREFERENCE_KEY.NICK_NAME, null);
    sUserInfo.icon = PreferenceWrapper
        .getString(BaseAppProfile.getApplication(), CommonPreferenceConst.MAIN_PREFERENCE_FILE,
            PREFERENCE_KEY.AVATAR, null);
    sUserInfo.mac_key = getMacKey();
    return sUserInfo;
  }

  public static void delete() {
    sMacKey = null;
    PreferenceWrapper
        .remove(BaseAppProfile.getApplication(), CommonPreferenceConst.MAIN_PREFERENCE_FILE,
            PREFERENCE_KEY.NICK_NAME);
    PreferenceWrapper
        .remove(BaseAppProfile.getApplication(), CommonPreferenceConst.MAIN_PREFERENCE_FILE,
            PREFERENCE_KEY.AVATAR);
    PreferenceWrapper
        .remove(BaseAppProfile.getApplication(), CommonPreferenceConst.MAIN_PREFERENCE_FILE,
            PREFERENCE_KEY.MAC_KEY);
    PreferenceWrapper
            .remove(BaseAppProfile.getApplication(), CommonPreferenceConst.MAIN_PREFERENCE_FILE,
                    PREFERENCE_KEY.EXE);
  }

  public static boolean isLogin() {
    return isLogin(null);
  }

  public static boolean isLogin(Context context) {
    if (sMacKey == null) {
      sMacKey = PreferenceWrapper
          .getString(BaseAppProfile.getApplication(), CommonPreferenceConst.MAIN_PREFERENCE_FILE,
              PREFERENCE_KEY.MAC_KEY, null);
    }
    if (TextUtils.isEmpty(sMacKey)) {
      if (context != null) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
      }
      return false;
    }
    return true;
  }

  public static String getMacKey() {
    return getMacKey(null);
  }

  public static void setMacKey(String macKey) {
    sMacKey = macKey;
    PreferenceWrapper
        .setString(BaseAppProfile.getApplication(), CommonPreferenceConst.MAIN_PREFERENCE_FILE,
            PREFERENCE_KEY.MAC_KEY, sMacKey);
  }

  public static String getMacKeyInDebug() {
    String macKey = getMacKey();
    if (TextUtils.isEmpty(getMacKey())) {
      return "debug userid=1";
    } else {
      return macKey;
    }
  }

  public static String getMacKey(Context context) {
    if (sMacKey == null) {
      sMacKey = PreferenceWrapper
          .getString(BaseAppProfile.getApplication(), CommonPreferenceConst.MAIN_PREFERENCE_FILE,
              PREFERENCE_KEY.MAC_KEY, null);
    }
    if (TextUtils.isEmpty(sMacKey)) {
      if (context != null) {
        LoginActivity.clearLaunch(context);
      }
      return null;
    }
    return sMacKey;
  }

//  public interface CallBack {
//
//    void macKeyEmpty();
//  }
}
