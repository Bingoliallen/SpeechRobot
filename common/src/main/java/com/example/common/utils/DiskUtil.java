package com.example.common.utils;

import android.content.Context;
import android.os.storage.StorageManager;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;

/**
 * Created by zyp on 2017/2/2.
 */

public class DiskUtil {

  private DiskUtil() {
  }

  //---------------------------------------------缓存---------------------------------------------

  /**
   * 获取缓存的大小
   */
  public static String getTotalCacheSize(Context context) throws Exception {
    //Context.getCacheDir() --> data/data/你的应用包名/cache，一般存放临时缓存数据
    long cacheSize = FileUtil.getFolderSize(context.getCacheDir());
    //Context.getExternalFilesDir() --> sdcard/Android/data/你的应用的包名/cache，一般放一些长时间保存的数据
    if (DeviceUtil.isSDCardMounted()) {
      cacheSize += FileUtil.getFolderSize(context.getExternalCacheDir());
    }
    return getFormatSize(cacheSize);
  }

  /**
   * 清理缓存
   */
  public static void clearAllCache(Context context) {
    FileUtil.deleteFile(context.getCacheDir());
    if (DeviceUtil.isSDCardMounted()) {
      FileUtil.deleteFile(context.getExternalCacheDir());
      //下面两句清理webview网页缓存.但是每次执行都报false,我用的是魅蓝5.1的系统，后来发现且/data/data/应用package目录下找不到database文///件夹 不知道是不是个别手机的问题，
      //mContext.deleteDatabase("webview.db");
      //mContext.deleteDatabase("webviewCache.db");
    }
  }
  //---------------------------------------------缓存---------------------------------------------


  /**
   * 格式化单位
   */
  public static String getFormatSize(double size) {
    double kiloByte = size / 1024;
    if (kiloByte < 1) {
//            return size + "Byte";
      return size + "B";
    }
    double megaByte = kiloByte / 1024;
    if (megaByte < 1) {
      BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
      return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
    }
    double gigaByte = megaByte / 1024;
    if (gigaByte < 1) {
      BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
      return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
    }
    double teraBytes = gigaByte / 1024;
    if (teraBytes < 1) {
      BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
      return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
    }
    BigDecimal result4 = new BigDecimal(teraBytes);
    return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
  }

  /**
   * 格式化单位转 long
   */
  public static long parseFormatSize(String size, String uint, int factor) {
    return Long.parseLong(size.split(uint)[0].trim()) * factor;
  }

  /**
   * 获取外置SD卡的路径
   */
  public static String getExternalSDCardPath(Context context) {
    StorageManager mStorageManager = (StorageManager) context
        .getSystemService(Context.STORAGE_SERVICE);
    Class<?> storageVolumeClazz;
    try {
      storageVolumeClazz = Class.forName("android.os.storage.StorageVolume");
      Method getVolumeList = mStorageManager.getClass().getMethod("getVolumeList");
      Method getPath = storageVolumeClazz.getMethod("getPath");
      Method isRemovable = storageVolumeClazz.getMethod("isRemovable");
      Object result = getVolumeList.invoke(mStorageManager);
      final int length = Array.getLength(result);
      for (int i = 0; i < length; i++) {
        Object storageVolumeElement = Array.get(result, i);
        String path = (String) getPath.invoke(storageVolumeElement);
        boolean removable = (Boolean) isRemovable.invoke(storageVolumeElement);
        if (removable) {
          return path;
        }
      }
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }
    return null;
  }
}