package com.vma.speechrobot.adapter;

import android.content.Context;
import android.widget.TextView;
import com.example.common.adapter.recycler.BaseRecyclerAdapter;
import com.example.common.adapter.recycler.ViewHolder;
import com.vma.speechrobot.R;
import com.vma.speechrobot.bean.KeyValueBean;

public class SpinerAdapter<K> extends BaseRecyclerAdapter<KeyValueBean<K, String>> {

  public SpinerAdapter(Context context) {
    super(context);
    putItemLayoutId(R.layout.recycler_item_spiner);
  }

  @Override
  public void onBind(ViewHolder holder, KeyValueBean<K, String> item, int position) {
    ((TextView) holder.getView()).setText(item.value);
  }
}
