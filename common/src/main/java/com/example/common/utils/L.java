package com.example.common.utils;

import android.util.Log;

/**
 * Created by xxdr on 2018/1/12.
 */

public class L {

  private L() {
  }

  public static void d(String tag, String msg) {
    if (msg != null) {
      Log.d(tag, msg);
    } else {
      Log.d(tag, "message is null !");
    }
  }

  public static void d(String tag, int i) {
    Log.d(tag, String.valueOf(i));
  }

  public static void d(String tag, double d) {
    Log.d(tag, String.valueOf(d));
  }

  public static void d(String tag, boolean b) {
    Log.d(tag, String.valueOf(b));
  }

  public static void d(String tag, Object obj) {
    if (obj == null) {
      Log.d(tag, "object is null !");
    } else {
      Log.d(tag, obj.toString());
    }
  }
}
