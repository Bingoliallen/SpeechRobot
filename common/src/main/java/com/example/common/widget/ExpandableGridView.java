package com.example.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.GridView;

/**
 * 展开所有子项的 GridView
 */
public class ExpandableGridView extends GridView {

  boolean expanded = true;

  public ExpandableGridView(Context context) {
    super(context);
  }

  public ExpandableGridView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public ExpandableGridView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
  }

  public boolean isExpanded() {
    return expanded;
  }

  public void setExpanded(boolean expanded) {
    this.expanded = expanded;
  }

  @Override
  public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    if (expanded) {
      int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
      super.onMeasure(widthMeasureSpec, expandSpec);
      ViewGroup.LayoutParams params = getLayoutParams();
      params.height = getMeasuredHeight();
    } else {
      super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
  }
}
