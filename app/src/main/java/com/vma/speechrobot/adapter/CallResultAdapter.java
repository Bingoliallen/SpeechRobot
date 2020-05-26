package com.vma.speechrobot.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.widget.TextView;
import com.example.common.adapter.recycler.BaseRecyclerAdapter;
import com.example.common.adapter.recycler.ViewHolder;
import com.example.common.utils.Spanny;
import com.vma.speechrobot.R;
import com.vma.speechrobot.bean.KeyNameBean;

public class CallResultAdapter extends BaseRecyclerAdapter<KeyNameBean> {

  public CallResultAdapter(Context context) {
    super(context);
    putItemLayoutId(R.layout.recycler_item_call_result);
  }

  @Override
  public void onBind(ViewHolder holder, KeyNameBean item, int position) {
    Spanny spanny = new Spanny();
    spanny.append(item.name, new RelativeSizeSpan(2f), new StyleSpan(Typeface.BOLD))
        .append("\n ", new RelativeSizeSpan(1.5f))
        .append(item.key);
    ((TextView) (holder.getView())).setText(spanny);
  }
}
