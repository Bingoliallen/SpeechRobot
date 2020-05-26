package com.vma.speechrobot.adapter;

import android.content.Context;
import android.view.View;

import com.example.common.adapter.recycler.BaseRecyclerAdapter;
import com.example.common.adapter.recycler.ViewHolder;
import com.vma.speechrobot.R;
import com.vma.speechrobot.bean.HumanCardRelate;

/**
 * Date: 2019/1/3
 * Author: libaibing
 * Email：
 * Des：
 */

public class HumanCardRelateAdapter extends BaseRecyclerAdapter<HumanCardRelate> {

    public interface ItemHumanCardRelateListener {
        void onClick(HumanCardRelate item, int position);
    }

    private ItemHumanCardRelateListener mItemHumanCardRelateListener;

    public void setmItemHumanCardRelateListener(ItemHumanCardRelateListener mItemHumanCardRelateListener) {
        this.mItemHumanCardRelateListener = mItemHumanCardRelateListener;
    }

    public HumanCardRelateAdapter(Context context) {
        super(context);
        putItemLayoutId(R.layout.item_dialog_humancardrelate);
    }


    @Override
    public void onBind(ViewHolder holder, final HumanCardRelate item, final int position) {
        holder.setText(R.id.tv_num, "" + item.user_id);
        holder.setText(R.id.tv_item, item.employee_name);
        holder.setText(R.id.task_num, "" + item.machine_name + "(" + item.mobile + ")");

        holder.getView(R.id.tv_type).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemHumanCardRelateListener != null) {
                    mItemHumanCardRelateListener.onClick(item, position);
                }
            }
        });


    }
}

