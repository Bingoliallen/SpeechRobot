package com.example.common.widget;

import static com.example.common.widget.ObservableNestedScrollView.OnScrollListener.SCROLL_STATE_FLING;
import static com.example.common.widget.ObservableNestedScrollView.OnScrollListener.SCROLL_STATE_IDLE;
import static com.example.common.widget.ObservableNestedScrollView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class ObservableNestedScrollView extends NestedScrollView {

  private static final boolean DEBUG = false;
  private static final int CHECK_SCROLL_STOP_DELAY_MILLIS = 80;
  private static final int MSG_SCROLL = 1;
  private boolean mIsTouched = false;
  private int mScrollState = SCROLL_STATE_IDLE;
  private OnScrollListener mOnScrollListener;
  private final Handler mHandler = new Handler(Looper.getMainLooper(), new Handler.Callback() {

    private int mLastY = Integer.MIN_VALUE;

    @Override
    public boolean handleMessage(Message msg) {
      if (msg.what == MSG_SCROLL) {
        final int scrollY = getScrollY();
        if (!mIsTouched && mLastY == scrollY) {
          mLastY = Integer.MIN_VALUE;
          setScrollState(SCROLL_STATE_IDLE);
        } else {
          mLastY = scrollY;
          restartCheckStopTiming();
        }
        return true;
      }
      return false;
    }
  });

  public ObservableNestedScrollView(Context context) {
    super(context);
  }

  public ObservableNestedScrollView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public ObservableNestedScrollView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
  }

  private void restartCheckStopTiming() {
    mHandler.removeMessages(MSG_SCROLL);
    mHandler.sendEmptyMessageDelayed(MSG_SCROLL, CHECK_SCROLL_STOP_DELAY_MILLIS);
  }

  public void setOnScrollListener(OnScrollListener onScrollListener) {
    mOnScrollListener = onScrollListener;
  }

  @Override
  public boolean onInterceptTouchEvent(MotionEvent ev) {
    handleDownEvent(ev);
    return super.onInterceptTouchEvent(ev);
  }

  @Override
  public boolean onTouchEvent(MotionEvent ev) {
    handleUpEvent(ev);
    return super.onTouchEvent(ev);
  }

  @Override
  protected void onScrollChanged(int l, int t, int oldl, int oldt) {
    super.onScrollChanged(l, t, oldl, oldt);
    if (mIsTouched) {
      setScrollState(SCROLL_STATE_TOUCH_SCROLL);
    } else {
      setScrollState(SCROLL_STATE_FLING);
      restartCheckStopTiming();
    }
    if (mOnScrollListener != null) {
      mOnScrollListener.onScroll(this, mIsTouched, l, t, oldl, oldt);
    }
  }

  private void handleDownEvent(MotionEvent ev) {
    switch (ev.getAction()) {
      case MotionEvent.ACTION_DOWN:
        mIsTouched = true;
        break;
    }
  }

  private void handleUpEvent(MotionEvent ev) {
    switch (ev.getAction()) {
      case MotionEvent.ACTION_UP:
      case MotionEvent.ACTION_CANCEL:
        mIsTouched = false;
        restartCheckStopTiming();
        break;
    }
  }

  private void setScrollState(int state) {
    if (mScrollState != state) {
      mScrollState = state;
      if (mOnScrollListener != null) {
        mOnScrollListener.onScrollStateChanged(this, state);
      }
    }
  }

  public interface OnScrollListener {

    /**
     * The view is not scrolling. Note navigating the list using the trackball counts as
     * being in the idle state since these transitions are not animated.
     */
    int SCROLL_STATE_IDLE = 0;

    /**
     * The user is scrolling using touch, and their finger is still on the screen
     */
    int SCROLL_STATE_TOUCH_SCROLL = 1;

    /**
     * The user had previously been scrolling using touch and had performed a fling. The
     * animation is now coasting to a stop
     */
    int SCROLL_STATE_FLING = 2;

    void onScrollStateChanged(ObservableNestedScrollView view, int scrollState);

    void onScroll(ObservableNestedScrollView view, boolean isTouchScroll, int l, int t, int oldl,
        int oldt);
  }
}
