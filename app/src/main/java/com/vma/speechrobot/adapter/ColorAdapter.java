package com.vma.speechrobot.adapter;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.widget.TextView;
import com.example.common.adapter.recycler.BaseRecyclerAdapter;
import com.example.common.adapter.recycler.ViewHolder;
import com.example.common.utils.BitmapUtil;
import com.example.common.utils.ViewUtil;
import com.vma.speechrobot.R;
import com.vma.speechrobot.bean.ColorBean;

public class ColorAdapter extends BaseRecyclerAdapter<ColorBean> {

  public ColorAdapter(Context context) {
    super(context);
    putItemLayoutId(R.layout.recycler_item_color);
  }

  @Override
  public void onBind(ViewHolder holder, ColorBean item, int position) {
    GradientDrawable drawable = new GradientDrawable();
    drawable.setSize(ViewUtil.dp2px(mContext, 12), ViewUtil.dp2px(mContext, 12));
    drawable.setColor(item.color);
    drawable.setCornerRadius(ViewUtil.dp2px(mContext, 3));
    holder.setImage(R.id.iv_color, BitmapUtil.drawable2Bitmap(drawable));
    TextView tvColor = holder.getView(R.id.tv_color);
    tvColor.setTextColor(item.color);
    tvColor.setText(item.name);
  }
}
