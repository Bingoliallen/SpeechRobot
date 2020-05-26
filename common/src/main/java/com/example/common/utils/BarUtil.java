package com.example.common.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by xxdr on 2018/4/11.
 */

public class BarUtil {

  /**
   * 隐藏 ActionBar
   */
  public static void hideActionBar(Activity activity) {
    if (activity instanceof AppCompatActivity) {
      ((AppCompatActivity) activity).getSupportActionBar().hide();
    }
  }

  /**
   * 透明状态栏
   */
  public static void translucentStatusBar(Activity activity, boolean flag) {
    if (flag) {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        Window window = activity.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.getDecorView().setSystemUiVisibility(
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        window.setStatusBarColor(Color.TRANSPARENT);
      } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
      }
    } else {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        Window window = activity.getWindow();
        window.setFlags(1, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
      } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
      }
    }
  }

  /**
   * 修改状态栏颜色，支持4.4以上版本
   */
  public static void setStatusBarColor(Activity activity, int colorId) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      Window window = activity.getWindow();
      //window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
      window.setStatusBarColor(ContextCompat.getColor(activity, colorId));
    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
      //使用SystemBarTint库使4.4版本状态栏变色，需要先将状态栏设置为透明
      translucentStatusBar(activity, true);
      SystemBarTintManager tintManager = new SystemBarTintManager(activity);
      tintManager.setStatusBarTintEnabled(true);
      tintManager.setStatusBarTintResource(colorId);
    }
  }

  /**
   * 透明导航栏
   */
  public static void translucentNavigation(Activity activity) {
    activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
  }

  /**
   * 全屏
   */
  public static void fullScreen(Activity activity) {
    activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
  }

  /**
   * 判断状态栏是否存在
   */
  public static boolean isStatusBarExist(Activity activity) {
    WindowManager.LayoutParams params = activity.getWindow().getAttributes();
    return (params.flags & WindowManager.LayoutParams.FLAG_FULLSCREEN)
        != WindowManager.LayoutParams.FLAG_FULLSCREEN;
  }

  /**
   * 获取状态栏高度
   */
  public static int getStatusBarHeight(Context context) {
    int result = 0;
    int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
    if (resourceId > 0) {
      result = context.getResources().getDimensionPixelSize(resourceId);
    }
    return result;
  }

  /**
   * 获取ActionBar高度
   */
  public static int getActionBarHeight(Activity activity) {
    TypedValue tv = new TypedValue();
    if (activity.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
      return TypedValue
          .complexToDimensionPixelSize(tv.data, activity.getResources().getDisplayMetrics());
    }
    return 0;
  }

  /**
   * 设置状态栏黑色字体图标，
   * 适配4.4以上版本MIUIV、Flyme和6.0以上版本其他Android
   *
   * @return 1:MIUI 2:Flyme 3:android6.0
   */
  public static int statusBarDarkMode(Activity activity) {
    int result = 0;
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
      if (statusBarDarkModeMIUI(activity.getWindow(), true)) {
        result = 1;
      } else if (statusBarDarkModeFlyme(activity.getWindow(), true)) {
        result = 2;
      } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        activity.getWindow().getDecorView()
            .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        result = 3;
      }
    }
    return result;
  }


  /**
   * 设置状态栏图标为深色和魅族特定的文字风格
   * 可以用来判断是否为Flyme用户
   *
   * @param window 需要设置的窗口
   * @param dark 是否把状态栏字体及图标颜色设置为深色
   * @return boolean 成功执行返回true
   */
  public static boolean statusBarDarkModeFlyme(Window window, boolean dark) {
    boolean result = false;
    if (window != null) {
      try {
        WindowManager.LayoutParams lp = window.getAttributes();
        Field darkFlag = WindowManager.LayoutParams.class
            .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
        Field meizuFlags = WindowManager.LayoutParams.class.getDeclaredField("meizuFlags");
        darkFlag.setAccessible(true);
        meizuFlags.setAccessible(true);
        int bit = darkFlag.getInt(null);
        int value = meizuFlags.getInt(lp);
        if (dark) {
          value |= bit;
        } else {
          value &= ~bit;
        }
        meizuFlags.setInt(lp, value);
        window.setAttributes(lp);
        result = true;
      } catch (Exception e) {
      }
    }
    return result;
  }

  /**
   * 设置状态栏字体图标为深色，需要MIUIV6以上
   *
   * @param window 需要设置的窗口
   * @param dark 是否把状态栏字体及图标颜色设置为深色
   * @return boolean 成功执行返回true
   */
  public static boolean statusBarDarkModeMIUI(Window window, boolean dark) {
    boolean result = false;
    if (window != null) {
      /**
       * 新
       */
      window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
      window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
      window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
      /**
       * 旧
       */
      Class clazz = window.getClass();
      try {
        int darkModeFlag = 0;
        Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
        Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
        darkModeFlag = field.getInt(layoutParams);
        Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
        if (dark) {
          extraFlagField.invoke(window, darkModeFlag, darkModeFlag);//状态栏透明且黑色字体
        } else {
          extraFlagField.invoke(window, 0, darkModeFlag);//清除黑色字体
        }
        result = true;
      } catch (Exception e) {
      }
    }
    return result;
  }
}
