package com.example.common.utils;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.AppCompatDrawableManager;
import android.widget.TextView;

/**
 * Created by xxdr on 2017/11/14.
 */

public class DrawableUtil {

  private DrawableUtil() {
  }

  public static void setLeftDrawable(Context context, TextView textView, int drawableId, int width,
      int height) {
    Drawable drawable = AppCompatDrawableManager.get().getDrawable(context, drawableId);
    DrawableUtil.resize(drawable, width, height);
    textView.setCompoundDrawables(drawable, null, null, null);
  }

  public static void setTopDrawable(Context context, TextView textView, int drawableId, int width,
      int height) {
    Drawable drawable = AppCompatDrawableManager.get().getDrawable(context, drawableId);
    DrawableUtil.resize(drawable, width, height);
    textView.setCompoundDrawables(null, drawable, null, null);
  }

  public static void setBottomDrawable(Context context, TextView textView, int drawableId,
      int width, int height) {
    Drawable drawable = AppCompatDrawableManager.get().getDrawable(context, drawableId);
    DrawableUtil.resize(drawable, width, height);
    textView.setCompoundDrawables(null, null, null, drawable);
  }

  /**
   * 修改 drawable 的大小
   */
  public static void resizeLeftDrawable(TextView view, int size) {
    Drawable drawable = view.getCompoundDrawables()[0];
    resize(drawable, size, size);
    view.setCompoundDrawables(drawable, null, view.getCompoundDrawables()[2], null);
  }

  public static void resizeLeftDrawable(TextView view, int width, int height) {
    Drawable drawable = view.getCompoundDrawables()[0];
    resize(drawable, width, height);
    view.setCompoundDrawables(drawable, null, view.getCompoundDrawables()[2], null);
  }

  /**
   * 修改 drawable 的颜色
   */
  public static void tintBackground(TextView view, int color) {
    Drawable drawable = view.getBackground();
    view.setBackground(tintDrawable(drawable, color));
  }

  public static void tintLeftDrawable(TextView view, int color) {
    Drawable drawable = view.getCompoundDrawables()[0];
    view.setCompoundDrawables(tintDrawable(drawable, color), null, null, null);
  }

  public static void tintTopDrawable(TextView view, int color) {
    Drawable drawable = view.getCompoundDrawables()[1];
    view.setCompoundDrawables(null, tintDrawable(drawable, color), null, null);
  }

  public static void tintRightDrawable(TextView view, int color) {
    Drawable drawable = view.getCompoundDrawables()[2];
    view.setCompoundDrawables(null, null, tintDrawable(drawable, color), null);
  }

  public static void tintBottomDrawable(TextView view, int color) {
    Drawable drawable = view.getCompoundDrawables()[3];
    view.setCompoundDrawables(null, null, null, tintDrawable(drawable, color));
  }

  /**
   * 修改 Drawable 颜色
   */
  public static Drawable tintDrawable(Drawable drawable, @ColorInt int color) {
    if (drawable == null) {
      return null;
    }
    /**
     * android中从同一个资源文件中加载出来的drawable会共享状态，
     * 如果你加载出来多个drawable，
     * 当改变了其中一个的状态时，
     * 其他drawable的状态也会相应改变。
     * 如果把这个drawable变为mutate drawable后，
     * 这个drawable就不会与其他drawable共享状态。
     * 特别注意，这个mutate操作是不可逆转的。
     */
    Drawable result = DrawableCompat.wrap(drawable.mutate());
    // setTint 只能在 5.0 版本以上使用
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      DrawableCompat.setTint(result, color);
    } else {
      result.setColorFilter(color, PorterDuff.Mode.SRC_IN);
    }
    return result;
  }

  /**
   * 修改 Drawable 大小
   */
  public static void resize(Drawable drawable, int width, int height) {
    if (drawable == null) {
      return;
    }
    drawable.setBounds(0, 0, width, height);
  }
}
