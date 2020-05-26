package com.example.common.widget;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import com.example.common.R;
import com.example.common.consts.CommonTimeConst;
import com.example.common.utils.TimerTask;

/**
 * Created by xxdr on 2017/11/3.
 */

public class CountDownButton extends Button implements View.OnClickListener, TimerTask.CallBack {

  /**
   * 倒计时时长，默认 60 秒
   */
  private long mDuration = 60;

  private long mRemainingTime = mDuration;

  private TimerTask mTimerTask;

  private Activity mActivity;
  private OnClickListener mListener;

  /**
   * 倒计时之前显示的内容
   */
  private int mClickText = R.string.common_count_down_btn_normal;
  /**
   * 倒计时之后显示的内容
   */
  private int mWaitText = R.string.common_count_down_btn_pressed;

  private int mCurrentSecond = 0;

  public CountDownButton(Context context) {
    this(context, null);
  }

  public CountDownButton(Context context, AttributeSet attrs) {
    super(context, attrs);
    mActivity = (Activity) context;
    initView();
  }

  /**
   * 初始化 view
   */
  private void initView() {
    setText(mClickText);
    setOnClickListener(this);
  }

  /**
   * 初始化 timer
   */
  private void initTimer(long duration) {
    mRemainingTime = duration;
    if (mTimerTask == null) {
      mTimerTask = new TimerTask<>();
    }
  }

  @Override
  public void call() {
    if (mCurrentSecond == mDuration) {
      setEnabled(true);
      setText(mClickText);
      mTimerTask.stopTimerTask();
    } else {
      if (!mActivity.isDestroyed() && !mActivity.isFinishing()) {
        setText(getContext().getString(mWaitText, mDuration - mCurrentSecond));
        mCurrentSecond++;
      } else {
        stop();
      }
    }
  }

  @Override
  public void onClick(View v) {
    if (mListener != null) {
      if (mListener.beforeOnClick()) {
        start(mDuration);
        mListener.onClick(v);
      }
    }
  }

  public void setDuration(long duration) {
    mDuration = duration;
  }

  public void setClickTextId(int clickTextId) {
    mClickText = clickTextId;
  }

  public void setWaitTextId(int waitTextId) {
    mWaitText = waitTextId;
  }

  public void setListener(OnClickListener listener) {
    mListener = listener;
  }


  /**
   * 获取剩余时间
   */
  public long getRemainingTime() {
    return mRemainingTime;
  }

  /**
   * 开始倒计时
   */
  public void start(long duration) {
    initTimer(duration);
    mCurrentSecond = 0;
    mTimerTask.startTimerTask(this, 0, CommonTimeConst.S);
    setEnabled(false);
  }

  /**
   * 结束倒计时
   */
  public void stop() {
    mTimerTask.stopTimerTask();
  }

  public interface OnClickListener {

    void onClick(View v);

    /**
     * 做一些验证，返回 true 才调用 onClick
     */
    boolean beforeOnClick();
  }
}