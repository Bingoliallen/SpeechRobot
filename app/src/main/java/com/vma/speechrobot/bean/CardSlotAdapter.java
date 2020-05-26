package com.vma.speechrobot.bean;

import android.content.Context;
import android.text.TextUtils;
import com.example.common.adapter.recycler.BaseRecyclerAdapter;
import com.example.common.adapter.recycler.ViewHolder;
import com.vma.speechrobot.R;

/**
 * Date: 2019/1/4
 * Author: libaibing
 * Email：
 * Des：
 */

public class CardSlotAdapter extends BaseRecyclerAdapter<CardSloBean> {

    public CardSlotAdapter(Context context) {
        super(context);
        putItemLayoutId(R.layout.item_dialog_cardslot);
    }



    @Override
    public void onBind(ViewHolder holder, CardSloBean item, int position) {
        holder.setText(R.id.tv_num, ""+item.getUser_name());
        holder.setText(R.id.tv_item, item.getMobile());
        if(!TextUtils.isEmpty(item.remark)){
            holder.setText(R.id.task_num, item.remark);//重复号码
        }else{
            holder.setText(R.id.task_num, "");
        }


    }
}
