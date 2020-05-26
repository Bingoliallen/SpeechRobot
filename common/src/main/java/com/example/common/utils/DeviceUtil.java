package com.example.common.utils;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.regex.Pattern;

/**
 * Created by Asha on 15-7-28.
 * Asha hzqiujiadi@corp.netease.com
 * <p>
 * 获取设备相关内容
 */
public class DeviceUtil {

  private static final Pattern SPLIT_PATTERN = Pattern.compile("\\s*:\\s*");

  private DeviceUtil() {
    /* cannot be instantiated */
    throw new UnsupportedOperationException("cannot be instantiated");
  }

  public static String getSystemVersion() {
    return Build.VERSION.RELEASE;
  }

  public static String getDeviceName() {
    return Build.BRAND;
  }

  public static int getDeviceWidth() {
    return DensityUtil.appDisplayMetrics.widthPixels;
  }

  public static int getDeviceHeight() {
    return DensityUtil.appDisplayMetrics.heightPixels;
  }

  public static float getDensity() {
    return DensityUtil.appDisplayMetrics.density;
  }

  /**
   * 获取电量百分比
   */
  public static int getBatteryPercent(Context context) {
    IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
    Intent intent = context.registerReceiver(null, filter);
    if (intent != null) {
      int level = intent.getIntExtra("level", -1);
      int scale = intent.getIntExtra("scale", -1);
      if (scale != -1) {
        return (level * 100) / scale;
      }
    }
    return -1;
  }

  /**
   * 是否 Root
   */
  public static boolean isRooted() {
    boolean isEmulator = isEmulator();
    String tags = Build.TAGS;
    if (!isEmulator && tags != null && tags.contains("test-keys")) {
      return true;
    }
    if (new File("system/app/Superuser.apk").exists()) {
      return true;
    }
    if (!isEmulator && new File("/system/xbin/su").exists()) {
      return true;
    }
    return false;
  }

  /**
   * 是否是模拟器
   */
  public static boolean isEmulator() {
    if (!TextUtils.isEmpty(Build.MODEL) && Build.MODEL.toLowerCase().contains("sdk")) {
      return true;
    }
    if (!TextUtils.isEmpty(Build.MANUFACTURER) && Build.MANUFACTURER.toLowerCase()
        .contains("unknown")) {
      return true;
    }
    if (!TextUtils.isEmpty(Build.DEVICE) && Build.DEVICE.toLowerCase().contains("genetic")) {
      return true;
    }
    return false;
  }

  /**
   * 获取运存
   */
  public static float getRamPercent(Context context) {
    long total = getTotalRAM();
    long avail = getAvailRAM(context);
    if (total < 0) {
      return -1;
    } else {
      return (float) (avail * 100) / total;
    }
  }

  /**
   * 获取总运存大小
   */
  public static long getTotalRAM() {
    long total = 0L;
    String str;
    if (!TextUtils.isEmpty(str = parseFile(new File("/proc/meminfo"), "MemTotal"))) {
      str = str.toUpperCase();
      if (str.endsWith("KB")) {
        total = DiskUtil.parseFormatSize(str, "KB", 1024);
      } else if (str.endsWith("MB")) {
        total = DiskUtil.parseFormatSize(str, "MB", 1048576);
      } else if (str.endsWith("GB")) {
        total = DiskUtil.parseFormatSize(str, "GB", 1073741824);
      } else {
        total = -1;
      }
    }
    return total;
  }

  /**
   * 获取可用运存大小
   */
  private static long getAvailRAM(Context context) {
    ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
    ActivityManager.MemoryInfo info = new ActivityManager.MemoryInfo();
    am.getMemoryInfo(info);
    return info.availMem;
  }

  private static String parseFile(File file, String filter) {
    String str = null;
    if (file.exists()) {
      BufferedReader reader = null;
      try {
        reader = new BufferedReader(new FileReader(file), 1024);
        String line;
        while ((line = reader.readLine()) != null) {
          String[] ret = SPLIT_PATTERN.split(line, 2);
          if (ret != null && ret.length > 1 && ret[0].equals(filter)) {
            str = ret[1];
            break;
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        IOUtil.close(reader);
      }
    }
    return str;
  }

  /**
   * 判断SDCard是否挂载
   */
  public static boolean isSDCardMounted() {
    if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
      return true;
    }
    return false;
  }

  /**
   * 获取 SD 百分比
   */
  public static float getSDPercent() {
    long total = getSDTotalSize();
    long avail = getSDAvailSize();
    if (total <= 0) {
      return -1;
    } else {
      return (float) (avail * 100) / total;
    }
  }

  /**
   * 获取设备SD卡总的大小
   */
  public static long getSDTotalSize() {
    if (!isSDCardMounted()) {
      return -1;
    }
    File path = Environment.getExternalStorageDirectory();
    StatFs stat = new StatFs(path.getPath());
    long blockSize = stat.getBlockSizeLong();
    long blocks = stat.getBlockCountLong();
    return blockSize * blocks;
  }

  /**
   * 获取设备SD卡可被应用程序使用的空间大小
   */
  public static long getSDAvailSize() {
    if (!isSDCardMounted()) {
      return -1;
    }
    File path = Environment.getExternalStorageDirectory();
    StatFs stat = new StatFs(path.getPath());
    long blockSize = stat.getBlockSizeLong();
    long blocks = stat.getAvailableBlocksLong();
    return blockSize * blocks;
  }

  /**
   * 获取网络信息
   */
  public static String getNetworkInfo(Context context) {
    String info = "unknown";
    ConnectivityManager connectivityManager = (ConnectivityManager) context
        .getSystemService(Context.CONNECTIVITY_SERVICE);
    if (connectivityManager != null) {
      NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
      if (networkInfo != null) {
        if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
          info = networkInfo.getTypeName();
        } else {
          StringBuilder sb = new StringBuilder();
          sb.append(networkInfo.getTypeName());
          sb.append(" [");
          String networkOperatorName = getNetworkOperatorName(context);
          if (!TextUtils.isEmpty(networkOperatorName)) {
            sb.append(networkOperatorName);
            sb.append("#");
          }
          sb.append(networkInfo.getSubtypeName());
          sb.append("]");
          info = sb.toString();
        }
      }
    }
    return info;
  }


  /**
   * 获取运营商名称
   */
  public static String getNetworkOperatorName(Context context) {
    if (AppUtil.checkPermission(context, Manifest.permission.READ_PHONE_STATE)) {
      TelephonyManager telephonyManager = (TelephonyManager) context
          .getSystemService(Context.TELEPHONY_SERVICE);
      return telephonyManager.getNetworkOperatorName();
    } else {
      return "unknown";
    }
  }
}
