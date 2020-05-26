package com.example.common.utils;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.text.TextUtils;

public class AppUtil {


  private AppUtil() {
  }

  /**
   * 获取渠道名
   */
  // public static String getChannel(Context context) {
  //     ApplicationInfo appinfo = context.getApplicationInfo();
  //     String sourceDir = appinfo.sourceDir;
  //     String ret = "";
  //     ZipFile zipfile = null;
  //     try {
  //         zipfile = new ZipFile(sourceDir);
  //         Enumeration<?> entries = zipfile.entries();
  //         while (entries.hasMoreElements()) {
  //             ZipEntry entry = ((ZipEntry) entries.nextElement());
  //             String entryName = entry.getName();
  //             if (entryName.startsWith("META-INF/channel")) {
  //                 ret = entryName;
  //                 break;
  //             }
  //         }
  //     } catch (IOException e) {
  //         e.printStackTrace();
  //     } finally {
  //         if (zipfile != null) {
  //             try {
  //                 zipfile.close();
  //             } catch (IOException e) {
  //                 e.printStackTrace();
  //             }
  //         }
  //     }
  //     String[] split = ret.split("_");
  //     if (split != null && split.length >= 2) {
  //         return ret.substring(split[0].length() + 1);
  //
  //     } else {
  //         return "";
  //     }
  // }

  /**
   * 检测是否有这个包
   */
  public static boolean checkPackage(Context context, String packageName) {
    PackageInfo packageInfo = null;
    try {
      packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
    } catch (PackageManager.NameNotFoundException e) {
      packageInfo = null;
      e.printStackTrace();
    } finally {
      if (packageInfo == null) {
        return false;
      } else {
        return true;
      }
    }
  }

  /**
   * 检测权限
   */
  public static boolean checkPermission(Context context, String permission) {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
      return true;
    }
    return context.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
  }

  /**
   * 检测当的网络（WLAN、3G/2G）状态
   */
  public static boolean isNetworkAvailable(Context context) {
    ConnectivityManager connectivity = (ConnectivityManager) context
        .getSystemService(Context.CONNECTIVITY_SERVICE);
    if (connectivity != null) {
      NetworkInfo info = connectivity.getActiveNetworkInfo();
      if (info != null && info.isConnected()) {
        // 当前网络是连接的
        if (info.getState() == NetworkInfo.State.CONNECTED) {
          // 当前所连接的网络可用
          return true;
        }
      }
    }
    return false;
  }

  /**
   * 复制
   */
  public static void copy(Context context, String str) {
    ClipboardManager board = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
    board.setText(str);
  }

  /**
   * 粘贴
   */
  public static String paste(Context context) {
    ClipboardManager board = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
    return board.getText().toString();
  }
}
