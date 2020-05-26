package com.example.common.widget.viewpager_indicator.draw_rule;

import android.content.Context;
import android.graphics.Path;
import com.example.common.utils.ViewUtil;
import com.example.common.widget.viewpager_indicator.ViewPagerIndicator;

/**
 * Created by xxdr on 2018/3/27.
 */

public class TriangleDrawRuleListener implements ViewPagerIndicator.OnDrawRuleListener {

  @Override
  public void onDrawRule(Context context, Path path, int tabWidth) {
    int triangleWidth = ViewUtil.dp2px(context, 10);
    int triangleHeight = ViewUtil.dp2px(context, 5);
    path.moveTo(-triangleWidth / 2, 0);
    path.lineTo(triangleWidth / 2, 0);
    path.lineTo(0, -triangleHeight);
    path.close();
  }
}
