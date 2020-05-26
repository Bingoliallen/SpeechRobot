package com.vma.speechrobot.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import com.example.common.adapter.recycler.BaseRecyclerAdapter;
import com.example.common.adapter.recycler.ViewHolder;
import com.vma.speechrobot.R;
import com.vma.speechrobot.bean.KeyNameBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Date: 2018/12/26
 * Author: libaibing
 * Email：
 * Des：
 */

public class CallNumberAdapter extends BaseRecyclerAdapter<KeyNameBean> {

    public CallNumberAdapter(Context context) {
        super(context);
        putItemLayoutId(R.layout.recycler_item_callnumber);

    }



    @Override
    public void onBind(ViewHolder holder,final KeyNameBean item,final int position) {
           holder.setText(R.id.tv_item, item.name);
        if(item.isSelect){
            ((ImageView)holder.getView(R.id.iv_item)).setImageResource(R.drawable.danxuan1);
        }else{
            ((ImageView)holder.getView(R.id.iv_item)).setImageResource(R.drawable.danxuan);
        }
        holder.getView(R.id.iv_item).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  if(onRecyclerItemClickListener!=null){
                      onRecyclerItemClickListener.onRecyclerItemClicked(CallNumberAdapter.this,view,item,position);
                  }
            }
        });
    }


}

