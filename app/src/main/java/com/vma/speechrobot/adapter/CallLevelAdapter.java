package com.vma.speechrobot.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.widget.TextView;
import com.example.common.adapter.recycler.BaseRecyclerAdapter;
import com.example.common.adapter.recycler.ViewHolder;
import com.example.common.utils.Spanny;
import com.vma.speechrobot.R;
import com.vma.speechrobot.bean.KeyNameBean;

public class CallLevelAdapter extends BaseRecyclerAdapter<KeyNameBean> {

  public CallLevelAdapter(Context context) {
    super(context);
    putItemLayoutId(R.layout.recycler_item_call_level);
  }

  @Override
  public void onBind(ViewHolder holder, KeyNameBean item, int position) {
    Spanny spanny = new Spanny();
    spanny.append(item.name, new RelativeSizeSpan(1.5f), new StyleSpan(Typeface.BOLD))
        .append("\n ", new RelativeSizeSpan(1.5f))
        .append(item.key);
    TextView tv = (TextView) holder.getView();
    switch (item.key) {
      case "A级":
        tv.setTextColor(ContextCompat.getColor(mContext, R.color.common_app_levelA));
        break;
      case "B级":
        tv.setTextColor(ContextCompat.getColor(mContext, R.color.common_app_levelB));
        break;
      case "C级":
        tv.setTextColor(ContextCompat.getColor(mContext, R.color.common_app_levelC));
        break;
      case "D级":
        tv.setTextColor(ContextCompat.getColor(mContext, R.color.common_app_levelD));
        break;
      case "E级":
        tv.setTextColor(ContextCompat.getColor(mContext, R.color.common_app_levelE));
        break;
      case "F级":
        tv.setTextColor(ContextCompat.getColor(mContext, R.color.common_app_levelF));
        break;
    }
    tv.setText(spanny);
  }
}
