package com.vma.speechrobot.widget;


import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import com.example.common.adapter.recycler.BaseRecyclerAdapter.OnRecyclerItemClickListener;
import com.vma.speechrobot.R;
import com.vma.speechrobot.adapter.SpinerAdapter;
import com.vma.speechrobot.bean.KeyValueBean;
import java.util.List;

public class SpinerPopWindow<K> extends PopupWindow {

  private LayoutInflater inflater;
  private RecyclerView mRecyclerView;
  private Context mContext;
  private List<KeyValueBean<K, String>> mList;
  private SpinerAdapter<K> mSpinnerAdapter;

  public SpinerPopWindow(Context context, List<KeyValueBean<K, String>> list,
      OnRecyclerItemClickListener listener) {
    super(context);
    mContext = context;
    inflater = LayoutInflater.from(context);
    mList = list;
    init(listener);
  }

  private void init(OnRecyclerItemClickListener listener) {
    View view = inflater.inflate(R.layout.spiner_window_layout, null);
    setContentView(view);
    setWidth(LayoutParams.WRAP_CONTENT);
    setHeight(LayoutParams.WRAP_CONTENT);
    setFocusable(true);
    ColorDrawable dw = new ColorDrawable(Color.TRANSPARENT);
    setBackgroundDrawable(dw);
    mRecyclerView = (RecyclerView) view;
    mRecyclerView
        .setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
    mRecyclerView.setAdapter(mSpinnerAdapter = new SpinerAdapter(mContext));
    mSpinnerAdapter.setOnRecyclerItemClickListener(listener);
    mSpinnerAdapter.set(mList);
  }
}
