package com.example.common.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.example.common.R;
import com.example.common.image.ImageWrapper;
import com.example.common.utils.SoftKeyboardUtil;
import com.example.common.utils.ViewUtil;


/**
 * Created by hang on 2014/11/24.
 * dialog base class
 */
public abstract class BaseDialog {

  protected Context mContext;
  protected PopupWindow mDialog;
  protected View mContentView;
  protected boolean mIsInitCompleted;

  public BaseDialog(Context context, int layoutId) {
    this(context, layoutId, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
  }

  public BaseDialog(Context context, int layoutId, int width, int height) {
    mContext = context;
    mContentView = LayoutInflater.from(mContext).inflate(layoutId, null);
    mDialog = new PopupWindow(mContentView, width, height, true);
    mDialog.setAnimationStyle(R.style.anim_base_dialog_fade);
    mDialog.setOnDismissListener(new PopupWindow.OnDismissListener() {
      @Override
      public void onDismiss() {
        ViewUtil.setBackgroundAlpha(mContext, 1);
      }
    });
    mIsInitCompleted = false;
  }

  public abstract void initContentView();

  /**
   * 设置显示动画
   */
  public void setWindowAnimation(int styleId) {
    mDialog.setAnimationStyle(styleId);
  }

  /**
   * 显示对话框
   */
  public void show() {
    show(Gravity.CENTER);
  }

  public void showAtBottom() {
    setWindowAnimation(R.style.anim_base_dialog_slide_at_bottom);
    show(Gravity.BOTTOM);
  }

  public void show(int gravity) {
    show(gravity, null);
  }

  public void show(int gravity, View parent) {
    if (mDialog != null) {
      if (!mIsInitCompleted) {
        initContentView();
        mIsInitCompleted = true;
      }
      if (parent == null) {
        parent = ((ViewGroup) ((Activity) mContext).findViewById(android.R.id.content))
            .getChildAt(0);
      }
      mDialog.showAtLocation(parent, gravity, 0, 0);
      ViewUtil.setBackgroundAlpha(mContext, 0.7f);
    }
    SoftKeyboardUtil.hideSoftKeyboard((Activity) mContext);
  }

  public void showAsDropDown(View anchor) {
    if (mDialog != null) {
      if (!mIsInitCompleted) {
        initContentView();
        mIsInitCompleted = true;
      }
      mDialog.showAsDropDown(anchor);
      ViewUtil.setBackgroundAlpha(mContext, 0.7f);
    }
    SoftKeyboardUtil.hideSoftKeyboard((Activity) mContext);
  }

  public boolean isShowing() {
    if (mDialog != null && mDialog.isShowing()) {
      return true;
    }
    return false;
  }

  public void dismiss() {
    if (mDialog != null && mDialog.isShowing()) {
      mDialog.dismiss();
    }
  }

  /**
   * 设置点击外部能否取消
   */
  public void setOutsideTouchable(boolean flag) {
    mDialog.setFocusable(flag);
    mDialog.setOutsideTouchable(flag);
  }

  protected void setText(int viewId, String text) {
    TextView tv = getView(viewId);
    tv.setText(text);
  }

  protected void setText(int viewId, @StringRes int resId) {
    TextView tv = getView(viewId);
    tv.setText(resId);
  }

  protected void setImage(int viewId, @DrawableRes int drawableId) {
    ImageView iv = getView(viewId);
    iv.setImageResource(drawableId);
  }

  protected void setImage(int viewId, Bitmap bm) {
    ImageView iv = getView(viewId);
    iv.setImageBitmap(bm);
  }

  protected void setImage(int viewId, String url) {
    ImageView iv = getView(viewId);
    ImageWrapper.setImage(iv, url);
  }

  protected <T extends View> T getView(int viewId) {
    View view = mContentView.findViewById(viewId);
    return (T) view;
  }

  /**
   * 设置对话框点击外部不能取消，需要在 activity 中添加，用以拦截事件。
   */
  //@Override
  //public boolean dispatchTouchEvent(MotionEvent event) {
  //  if (mPayDialog != null && mPayDialog.isShowing()) {
  //    return false;
  //  }
  //  return super.dispatchTouchEvent(event);
  //}
}
