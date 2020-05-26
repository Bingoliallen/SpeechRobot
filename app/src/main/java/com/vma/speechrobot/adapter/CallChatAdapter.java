package com.vma.speechrobot.adapter;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.common.adapter.recycler.BaseRecyclerAdapter;
import com.example.common.adapter.recycler.ViewHolder;
import com.vma.speechrobot.R;
import com.vma.speechrobot.bean.CallDetailBean;
import com.vma.speechrobot.bean.CallDetailBean.RecordDetailsListBean;
import com.vma.speechrobot.event.AudioEvent;
import org.greenrobot.eventbus.EventBus;

public class CallChatAdapter extends BaseRecyclerAdapter<CallDetailBean.RecordDetailsListBean> {

  public CallChatAdapter(Context context) {
    super(context);
    putItemLayoutId(RecordDetailsListBean.SYSTEM, R.layout.recycler_item_call_chat_system);
    putItemLayoutId(RecordDetailsListBean.CUSTOMER, R.layout.recycler_item_call_chat_customer);
  }

  @Override
  public void onBind(ViewHolder holder, final RecordDetailsListBean item, int position) {
    holder.setText(R.id.tv_content, item.call_content);
    RelativeLayout iv = holder.getView(R.id.iv_voice);
    iv.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        EventBus.getDefault().post(new AudioEvent(item.call_audio));
      }
    });
  }

  @Override
  public int getItemViewType(int position, RecordDetailsListBean item) {
    return item.call_type;
  }
}
