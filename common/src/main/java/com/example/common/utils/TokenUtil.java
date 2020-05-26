//package com.example.common.utils;
//
//
//import android.content.Context;
//import android.text.TextUtils;
//import com.example.common.consts.CommonPreferenceConst;
//import com.example.common.utils.wrapper.PreferenceWrapper;
//
///**
// * Created by xxdr on 2017/11/18.
// */
//
//public class TokenUtil {
//
//  private static String sToken;
//  private static CallBack sCallBack;
//
//  private TokenUtil() {
//  }
//
//  public static void init(Context context, CallBack callBack) {
//    sToken = PreferenceWrapper.getString(context, CommonPreferenceConst.MAIN_PREFERENCE_FILE,
//        CommonPreferenceConst.PREFERENCE_KEY.TOKEN, "");
//    sCallBack = callBack;
//  }
//
//  public static String getToken() {
//    return getToken(true);
//  }
//
//  public static String getToken(boolean isCallBack) {
//    if (TextUtils.isEmpty(sToken)) {
//      if (sCallBack != null && isCallBack) {
//        sCallBack.tokenEmpty();
//        return null;
//      }
//    }
//    return sToken;
//  }
//
//  public static void setToken(Context context, String token) {
//    sToken = token;
//    PreferenceWrapper.setString(context, CommonPreferenceConst.MAIN_PREFERENCE_FILE,
//        CommonPreferenceConst.PREFERENCE_KEY.TOKEN, sToken);
//  }
//
//  interface CallBack {
//
//    void tokenEmpty();
//  }
//}
