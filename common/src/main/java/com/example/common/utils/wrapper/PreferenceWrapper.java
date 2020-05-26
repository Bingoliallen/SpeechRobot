package com.example.common.utils.wrapper;

import android.content.Context;
import android.content.SharedPreferences;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by xxdr on 2017/7/27.
 */

public class PreferenceWrapper {

  private PreferenceWrapper() {
  }

  public static void clear(Context context, String spName) {
    SharedPreferences.Editor editor = context.getSharedPreferences(spName, Context.MODE_PRIVATE)
        .edit();
    editor.clear();
    SharedPreferencesCompat.apply(editor);
  }

  public static void remove(Context context, String spName, String key) {
    SharedPreferences.Editor editor = context.getSharedPreferences(spName, Context.MODE_PRIVATE)
        .edit();
    editor.remove(key);
    SharedPreferencesCompat.apply(editor);
  }

  public static boolean hasKey(Context context, String spName, String paramString) {
    return context.getSharedPreferences(spName, Context.MODE_PRIVATE).contains(paramString);
  }


  /**
   * get and set
   */
  public static boolean getBoolean(Context context, String spName, String key,
      boolean defaultValue) {
    return context.getSharedPreferences(spName, Context.MODE_PRIVATE).getBoolean(key, defaultValue);
  }

  public static float getloat(Context context, String spName, String paramString,
      float defaultValue) {
    return context.getSharedPreferences(spName, Context.MODE_PRIVATE)
        .getFloat(paramString, defaultValue);
  }

  public static int getInt(Context context, String spName, String paramString, int defaultValue) {
    return context.getSharedPreferences(spName, Context.MODE_PRIVATE)
        .getInt(paramString, defaultValue);
  }

  public static long getLong(Context context, String spName, String paramString,
      long defaultValue) {
    return context.getSharedPreferences(spName, Context.MODE_PRIVATE)
        .getLong(paramString, defaultValue);
  }

  public static String getString(Context context, String spName, String paramString1,
      String defaultValue) {
    return context.getSharedPreferences(spName, Context.MODE_PRIVATE)
        .getString(paramString1, defaultValue);
  }

  public static void setBoolean(Context context, String spName, String key, boolean value) {
    SharedPreferences.Editor editor = context.getSharedPreferences(spName, Context.MODE_PRIVATE)
        .edit();
    editor.putBoolean(key, value);
    SharedPreferencesCompat.apply(editor);
  }

  public static void setFloat(Context context, String spName, String key, float value) {
    SharedPreferences.Editor editor = context.getSharedPreferences(spName, Context.MODE_PRIVATE)
        .edit();
    editor.putFloat(key, value);
    SharedPreferencesCompat.apply(editor);
  }

  public static void setInt(Context context, String spName, String key, int value) {
    SharedPreferences.Editor editor = context.getSharedPreferences(spName, Context.MODE_PRIVATE)
        .edit();
    editor.putInt(key, value);
    SharedPreferencesCompat.apply(editor);
  }

  public static void setString(Context context, String spName, String key, String value) {
    SharedPreferences.Editor editor = context.getSharedPreferences(spName, Context.MODE_PRIVATE)
        .edit();
    editor.putString(key, value);
    SharedPreferencesCompat.apply(editor);
  }

  public static void setLong(Context context, String spName, String key, long value) {
    SharedPreferences.Editor editor = context.getSharedPreferences(spName, Context.MODE_PRIVATE)
        .edit();
    editor.putLong(key, value).apply();
    SharedPreferencesCompat.apply(editor);
  }

  /**
   * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
   */
  private static class SharedPreferencesCompat {

    private static final Method sApplyMethod = findApplyMethod();

    /**
     * 反射查找apply的方法
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    private static Method findApplyMethod() {
      try {
        Class clz = SharedPreferences.Editor.class;
        return clz.getMethod("apply");
      } catch (NoSuchMethodException e) {
      }
      return null;
    }

    /**
     * 如果找到则使用apply执行，否则使用commit
     */
    public static void apply(SharedPreferences.Editor editor) {
      try {
        if (sApplyMethod != null) {
          sApplyMethod.invoke(editor);
          return;
        }
      } catch (IllegalArgumentException e) {
      } catch (IllegalAccessException e) {
      } catch (InvocationTargetException e) {
      }
      editor.commit();
    }
  }
}
