package com.example.common.widget.scroll2top;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by xxdr on 2018/4/26.
 */

public class ObservableScrollView extends ScrollView {

  private ScrollViewListener mScrollViewListener = null;

  public ObservableScrollView(Context context) {
    this(context, null);
  }

  public ObservableScrollView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public ObservableScrollView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);

  }

  public void setScrollViewListener(ScrollViewListener scrollViewListener) {
    mScrollViewListener = scrollViewListener;
  }

  @Override
  protected void onScrollChanged(int x, int y, int oldX, int oldY) {
    super.onScrollChanged(x, y, oldX, oldY);
    if (mScrollViewListener != null) {
      mScrollViewListener.onScrollChanged(this, x, y, oldX, oldY);
    }
  }

  public interface ScrollViewListener {

    void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldX, int oldY);

  }
}
