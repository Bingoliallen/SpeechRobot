package com.vma.speechrobot.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.widget.TextView;
import com.example.common.adapter.recycler.BaseRecyclerAdapter;
import com.example.common.adapter.recycler.ViewHolder;
import com.example.common.utils.Spanny;
import com.vma.speechrobot.R;
import com.vma.speechrobot.bean.KeyNameBean;

public class CallTimeAdapter extends BaseRecyclerAdapter<KeyNameBean> {

  public CallTimeAdapter(Context context) {
    super(context);
    putItemLayoutId(R.layout.recycler_item_call_time);
  }

  @Override
  public void onBind(ViewHolder holder, KeyNameBean item, int position) {
    Spanny spanny = new Spanny();
    switch (position) {
      case 0:
        spanny = spanny.append(item.name, new RelativeSizeSpan(2f),
            new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.common_app_time1)),
            new StyleSpan(Typeface.BOLD));
        break;
      case 1:
        spanny = spanny.append(item.name, new RelativeSizeSpan(2f),
            new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.common_app_time2)),
            new StyleSpan(Typeface.BOLD));
        break;
      case 2:
        spanny = spanny.append(item.name, new RelativeSizeSpan(2f),
            new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.common_app_time3)),
            new StyleSpan(Typeface.BOLD));
        break;
      case 3:
        spanny = spanny.append(item.name, new RelativeSizeSpan(2f),
            new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.common_app_time4)),
            new StyleSpan(Typeface.BOLD));
        break;
    }
    spanny = spanny.append("\n ", new RelativeSizeSpan(1.5f))
        .append(item.key);
    ((TextView) (holder.getView())).setText(spanny);
  }
}
