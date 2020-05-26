package com.example.common.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatDrawableManager;
import android.text.Editable;
import android.text.Html;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

/**
 * Created by xxdr on 2018/1/1.
 */

public class ViewUtil {

  private ViewUtil() {
  }

  /**
   * dp to px
   */
  public static int dp2px(Context context, float dpValue) {
    return (int) (dpValue * context.getResources().getDisplayMetrics().density + 0.5f);
  }

  /**
   * sp to px
   */
  public static int sp2px(Context context, float spValue) {
    return (int) (spValue * context.getResources().getDisplayMetrics().scaledDensity + 0.5f);
  }

  /**
   * px to dp
   */
  public static int px2dp(Context context, float pxValue) {
    return (int) (pxValue / context.getResources().getDisplayMetrics().density + 0.5f);
  }

  /**
   * px to sp
   */
  public static int px2sp(Context context, float pxValue) {
    return (int) (pxValue / context.getResources().getDisplayMetrics().scaledDensity + 0.5f);
  }

  /**
   * 展示EditText的错误信息
   */
  public static void showEditError(EditText v, String msg) {
    v.requestFocus();
    v.setError(Html.fromHtml("<font color=#B2001F>" + msg + "</font>"));
  }

  /**
   * 判断EditText内容是否非空，为空显示提醒
   * false 非空     true 空
   */
  public static boolean checkEditEmpty(EditText v, String msg) {
    if (TextUtils.isEmpty(v.getText())) {
      showEditError(v, msg);
      return true;
    } else {
      return false;
    }
  }

  public static void setBackgroundAlpha(Context context, float alpha) {
    WindowManager.LayoutParams lp = ((Activity) context).getWindow().getAttributes();
    lp.alpha = alpha;
    ((Activity) context).getWindow().setAttributes(lp);
  }

  /**
   * 展示EditText自带的清空按钮
   */
  public static void setEditWithClearButton(final Context context, final EditText edt,
      final int imgRes) {
    edt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
      @SuppressLint("RestrictedApi")
      @Override
      public void onFocusChange(View v, boolean hasFocus) {
        Drawable[] drawables = edt.getCompoundDrawables();
        if (hasFocus && edt.getText().toString().length() > 0) {
          edt.setTag(true);
          edt.setCompoundDrawablesWithIntrinsicBounds(
              drawables[0],
              drawables[1],
              AppCompatDrawableManager.get().getDrawable(context, imgRes),
              drawables[3]);
        } else {
          edt.setTag(false);
          edt.setCompoundDrawablesWithIntrinsicBounds(
              drawables[0],
              drawables[1],
              null,
              drawables[3]);
        }
      }
    });
    edt.setOnTouchListener(new View.OnTouchListener() {
      @Override
      public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
          case MotionEvent.ACTION_UP:
            int curX = (int) event.getX();
            // 满足清空条件
            if (curX > v.getWidth() - dp2px(context, 50) && !TextUtils.isEmpty(edt.getText())) {
              if (edt.getTag() != null && (Boolean) edt.getTag()) {
                edt.setText("");
                // http://blog.csdn.net/eclipsexys/article/details/8785149
                // 需要传递 UP 事件，不然 onTouchEvent 没有接收到 UP 事件会变成长按。
                // 传递事件之前要设置 InputType 为 TYPE_NULL，否则会弹出键盘。
                int cacheInputType = edt.getInputType();
                edt.setInputType(InputType.TYPE_NULL);
                edt.onTouchEvent(event);
                edt.setInputType(cacheInputType);
                return true;
              } else {
                return false;
              }
            }
            break;
        }
        return false;
      }
    });
    edt.addTextChangedListener(new TextWatcher() {
      @Override
      public void afterTextChanged(Editable s) {
      }

      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {
      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {
        Drawable[] drawables = edt.getCompoundDrawables();
        if (edt.getText().toString().length() == 0) {
          edt.setTag(false);
          edt.setCompoundDrawablesWithIntrinsicBounds(
              drawables[0],
              drawables[1],
              null,
              drawables[3]);
        } else {
          edt.setTag(true);
          edt.setCompoundDrawablesWithIntrinsicBounds(
              drawables[0],
              drawables[1],
              ContextCompat.getDrawable(context, imgRes),
              drawables[3]);
        }
      }
    });
  }

  /**
   * 获取视图的宽度
   */
  public static int getViewWidth(View view) {
    int measure = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
    view.measure(measure, measure);
    return view.getMeasuredWidth();
  }

  /**
   * 获取视图的高度
   */
  public static int getViewHeight(View view) {
    int measure = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
    view.measure(measure, measure);
    return view.getMeasuredHeight();
  }
}
