package com.example.common.widget.scroll2top;

import android.content.Context;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import com.example.common.widget.scroll2top.ObservableScrollView.ScrollViewListener;

/**
 * Created by xxdr on 2018/4/26.
 */

public class Scroll2TopButton extends Button {

  private ObservableScrollView mObservableSv;
  private RecyclerView mRecyclerView;

  public Scroll2TopButton(Context context) {
    this(context, null);
  }

  public Scroll2TopButton(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public Scroll2TopButton(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    setVisibility(GONE);
  }

  /**
   * 绑定 RecyclerView
   */
  public void bind(RecyclerView recyclerView) {
    if (recyclerView == null) {
      return;
    }
    mRecyclerView = recyclerView;
    if (VERSION.SDK_INT < VERSION_CODES.M) {
      mRecyclerView.setOnScrollListener(new OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
          setVisibilityByRecyclerView();
        }
      });
    } else {
      mRecyclerView.setOnScrollChangeListener(new OnScrollChangeListener() {
        @Override
        public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX,
            int oldScrollY) {
          setVisibilityByRecyclerView();
        }
      });
    }
  }

  /**
   * 绑定 ObservableScrollView
   */
  public void bind(ObservableScrollView scrollView) {
    if (scrollView == null) {
      return;
    }
    mObservableSv = scrollView;
    mObservableSv.setScrollViewListener(new ScrollViewListener() {

      @Override
      public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldX,
          int oldY) {
        int visibility = scrollView.getScrollY() > 0 ? View.VISIBLE : View.GONE;
        setVisibility(visibility);
      }
    });
  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {
    switch (event.getAction()) {
      case MotionEvent.ACTION_DOWN:
        scroll2Top();
        setVisibility(GONE);
        break;
    }
    return true;
  }

  /**
   * 回滚到头部
   */
  private void scroll2Top() {
    if (mObservableSv != null) {
      mObservableSv.fullScroll(ScrollView.FOCUS_UP);
    }
    if (mRecyclerView != null) {
      mRecyclerView.scrollToPosition(0);
    }
  }

  /**
   * 通过 RecyclerView 设置是否可见
   */
  private void setVisibilityByRecyclerView() {
    LinearLayoutManager layoutManager = (LinearLayoutManager) mRecyclerView
        .getLayoutManager();
    if (layoutManager != null) {
      int position = layoutManager.findFirstVisibleItemPosition();
      View firstVisibleChildView = layoutManager.findViewByPosition(position);
      int itemHeight = firstVisibleChildView.getHeight();
      int visibility =
          (position) * itemHeight - firstVisibleChildView.getTop() > 0
              ? VISIBLE
              : GONE;
      setVisibility(visibility);
    }
  }
}
