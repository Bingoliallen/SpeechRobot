package com.example.common.utils.init;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatDrawableManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.example.common.R;
import com.example.common.utils.DrawableUtil;
import com.example.common.utils.ViewUtil;

/**
 * Toast统一管理类
 */
public class T {

  public static final int DEFAULT = 0;
  public static final int ERROR = 1;
  public static final int SUCCESS = 2;
  public static final int INFO = 3;
  public static final int WARNING = 4;

  private static Context sContext;
  private static Toast sToast;
  private static TextView sTvToast;
  private static ToastHandler sHandler;
  private static Drawable sErrorDrawable;
  private static Drawable sWarningDrawable;
  private static boolean sIsExit = false;

  /**
   * 必须先在UI线程中调用
   */
  @SuppressLint("RestrictedApi")
  public static void init(Application application) {
    sContext = application;
    sErrorDrawable = AppCompatDrawableManager.get()
        .getDrawable(sContext, R.drawable.common_svg_error);
    sWarningDrawable = AppCompatDrawableManager.get()
        .getDrawable(sContext, R.drawable.common_svg_warning);
    DrawableUtil
        .resize(sErrorDrawable, ViewUtil.dp2px(application, 16), ViewUtil.dp2px(application, 16));
    DrawableUtil
        .resize(sWarningDrawable, ViewUtil.dp2px(application, 18), ViewUtil.dp2px(application, 18));
    sToast = Toast.makeText(sContext, "", Toast.LENGTH_SHORT);
    View view = LayoutInflater.from(application).inflate(R.layout.common_toast_default, null);
    sTvToast = (TextView) view.findViewById(android.R.id.message);
    sToast.setView(view);
    sHandler = new ToastHandler();
  }

  public static void showShort(String message) {
    showShort(message, DEFAULT);
  }

  /**
   * 短时间显示Toast
   */
  public static void showShort(String message, int flag) {
    Message msg = Message.obtain();
    msg.what = Toast.LENGTH_SHORT;
    msg.arg1 = flag;
    msg.obj = message;
    sHandler.sendMessage(msg);
  }

  /**
   * 短时间显示Toast
   */
  public static void showShort(int messageRes) {
    Message msg = Message.obtain();
    msg.what = Toast.LENGTH_SHORT;
    msg.obj = sContext.getString(messageRes);
    sHandler.sendMessage(msg);
  }

  /**
   * 长时间显示Toast
   */
  public static void showLong(String message) {
    Message msg = Message.obtain();
    msg.what = Toast.LENGTH_LONG;
    msg.obj = message;
    sHandler.sendMessage(msg);
  }

  /**
   * hide the Toast, if any.
   */
  public static void hideToast() {
    if (sToast != null) {
      sToast.cancel();
    }
  }

  public static void showNetworkError() {
    showShort(sContext.getString(R.string.common_network_error), ERROR);
  }

  public static void exitApplication(Activity activity) {
    if (!sIsExit) {
      sIsExit = true;
      com.example.common.utils.init.T
          .showShort(sContext.getString(R.string.common_main_activity_exit_application));
      new Thread(new Runnable() {
        @Override
        public void run() {
          try {
            Thread.sleep(1500);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          sIsExit = false;
        }
      }).start();
    } else {
      activity.finish();
    }
  }

  private static class ToastHandler extends Handler {

    @Override
    public void handleMessage(Message msg) {
      int colorRes = R.color.common_toast_default;
      sTvToast.setCompoundDrawables(null, null, null, null);
      switch (msg.arg1) {
        case ERROR:
          colorRes = R.color.common_toast_error;
          sTvToast.setCompoundDrawables(sErrorDrawable, null, null, null);
          break;
        case SUCCESS:
          colorRes = R.color.common_toast_success;
          break;
        case INFO:
          colorRes = R.color.common_toast_info;
          break;
        case WARNING:
          colorRes = R.color.common_toast_warning;
          sTvToast.setCompoundDrawables(sWarningDrawable, null, null, null);
          break;
      }
      switch (msg.what) {
        case Toast.LENGTH_SHORT:
          sToast.setDuration(Toast.LENGTH_SHORT);
          break;
        case Toast.LENGTH_LONG:
          sToast.setDuration(Toast.LENGTH_LONG);
          break;
      }
      sTvToast.getBackground()
          .setColorFilter(ContextCompat.getColor(sContext, colorRes), PorterDuff.Mode.SRC_IN);
      sToast.setText((String) msg.obj);
      sToast.show();
    }
  }
}
