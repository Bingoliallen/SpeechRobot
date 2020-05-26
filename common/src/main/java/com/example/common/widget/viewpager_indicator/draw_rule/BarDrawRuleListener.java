package com.example.common.widget.viewpager_indicator.draw_rule;

import android.content.Context;
import android.graphics.Path;
import com.example.common.utils.ViewUtil;
import com.example.common.widget.viewpager_indicator.ViewPagerIndicator;

/**
 * Created by xxdr on 2018/3/27.
 */

public class BarDrawRuleListener implements ViewPagerIndicator.OnDrawRuleListener {

  @Override
  public void onDrawRule(Context context, Path path, int tabWidth) {
    int width = ViewUtil.dp2px(context, 36);
    int height = ViewUtil.dp2px(context, 2);
    path.moveTo(-width / 2, 0);
    path.lineTo(width / 2, 0);
    path.lineTo(width / 2, -height);
    path.lineTo(-width / 2, -height);
    path.close();
  }
}
