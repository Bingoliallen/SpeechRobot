package com.example.common.adapter.recycler.divider;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by xxdr on 2018/5/4.
 */

public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

  private int mSpanCount;
  private int mSpacingH;
  private int mSpacingV;
  private boolean mIncludeEdge;

  public GridSpacingItemDecoration(int spanCount, int spacingH, int spacingV, boolean includeEdge) {
    mSpanCount = spanCount;
    mSpacingH = spacingH;
    mSpacingV = spacingV;
    mIncludeEdge = includeEdge;
  }

  @Override
  public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
      RecyclerView.State state) {
    int position = parent.getChildAdapterPosition(view); // item position
    int column = position % mSpanCount; // item column

    if (mIncludeEdge) {
      outRect.left = mSpacingH
          - column * mSpacingH / mSpanCount; // mSpacingH - column * ((1f / mSpanCount) * mSpacingH)
      outRect.right =
          (column + 1) * mSpacingH / mSpanCount; // (column + 1) * ((1f / mSpanCount) * mSpacingH)
      if (position < mSpanCount) { // top edge
        outRect.top = mSpacingV;
      }
      outRect.bottom = mSpacingV; // item bottom
    } else {
      outRect.left = column * mSpacingH / mSpanCount; // column * ((1f / mSpanCount) * mSpacingH)
      outRect.right = mSpacingH - (column + 1) * mSpacingH
          / mSpanCount; // mSpacingH - (column + 1) * ((1f /    mSpanCount) * mSpacingH)
      if (position >= mSpanCount) {
        outRect.top = mSpacingV; // item top
      }
    }
  }
}
