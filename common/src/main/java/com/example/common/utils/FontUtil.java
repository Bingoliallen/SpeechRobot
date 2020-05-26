package com.example.common.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.lang.reflect.Field;

/**
 * Created by xxdr on 2018/4/19.
 */

public class FontUtil {

  public static final String FONTS_DIR = "fonts/";

  public static String getFontPath(String fontName) {
    return FONTS_DIR + fontName;
  }

  public static void injectViewFont(View rootView, String fontName) {
    injectViewFont(rootView, createTypeface(rootView.getContext(), getFontPath(fontName)));
  }

  public static void injectSystemDefaultFont(Context context, String fontName) {
    replaceTypefaceField("DEFAULT", createTypeface(context, getFontPath(fontName)));
    replaceTypefaceField("MONOSPACE", createTypeface(context, getFontPath(fontName)));
    replaceTypefaceField("SERIF", createTypeface(context, getFontPath(fontName)));
    replaceTypefaceField("SANS_SERIF", createTypeface(context, getFontPath(fontName)));
  }

  public static void injectViewFont(View rootView, Typeface tf) {
    if (rootView instanceof ViewGroup) {
      ViewGroup group = (ViewGroup) rootView;
      int childCount = group.getChildCount();
      for (int i = 0; i < childCount; i++) {
        injectViewFont(group.getChildAt(i), tf);
      }
    } else if (rootView instanceof TextView) {
      ((TextView) rootView).setTypeface(tf);
    }
  }

  private static void replaceTypefaceField(String fieldName, Object value) {
    try {
      Field defaultField = Typeface.class.getDeclaredField(fieldName);
      defaultField.setAccessible(true);
      defaultField.set(null, value);
    } catch (NoSuchFieldException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }
  }

  private static Typeface createTypeface(Context context, String fontPath) {
    return Typeface.createFromAsset(context.getAssets(), fontPath);
  }

  //在 style.xml 的 AppTheme 中加入
  //<item name="android:typeface">monospace</item>
}
