package com.vma.speechrobot.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;
import com.example.common.adapter.recycler.BaseRecyclerAdapter;
import com.example.common.adapter.recycler.ViewHolder;
import com.example.common.utils.ViewUtil;
import com.vma.speechrobot.R;
import com.vma.speechrobot.bean.CallDetailBean.KeyWordBean;

public class CallKeyAdapter extends BaseRecyclerAdapter<KeyWordBean> {

  public CallKeyAdapter(Context context) {
    super(context);
    putItemLayoutId(R.layout.recycler_item_call_key);
  }

  @Override
  public void onBind(ViewHolder holder, KeyWordBean item, int position) {
    if (item.status == null) {
      item.status = 0;
    }
    switch (item.status) {
      case KeyWordBean.AVAILABLE:
        break;
      case KeyWordBean.UNAVAILABLE:
        break;
    }
    //int color = getColor(item.status);
    TextView tv = (TextView) holder.getView();
    tv.setText(item.key_word);
   // tv.setTextColor(color);
   // tv.setBackground(getBgColor(color, tv.getWidth(), tv.getHeight()));
  }

  private int getColor(int i) {
    switch (i) {
      case 0:
        return ContextCompat.getColor(mContext, R.color.common_app_levelE);
      case 1:
        return ContextCompat.getColor(mContext, R.color.common_app_levelA);
    }
    return 0;
  }

  private Drawable getBgColor(int color, int width, int height) {
    int red = color >> 16 & 0xFF;
    int green = color >> 8 & 0xFF;
    int blue = color & 0xFF;
    color = Color.argb(44, red, green, blue);

    GradientDrawable drawable = new GradientDrawable();
    drawable.setSize(width, height);
    drawable.setColor(color);
    drawable.setCornerRadius(ViewUtil.dp2px(mContext, 5));
    return drawable;
  }
}
