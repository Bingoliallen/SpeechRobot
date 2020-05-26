package com.vma.speechrobot.adapter;

import android.content.Context;
import com.example.common.adapter.recycler.BaseRecyclerAdapter;
import com.example.common.adapter.recycler.ViewHolder;
import com.vma.speechrobot.R;
import com.vma.speechrobot.bean.MessageTemplate;

/**
 * Created by Administrator on 2019/1/1.
 */

public class TaskMsgModeEndAdapter extends BaseRecyclerAdapter<MessageTemplate> {

    public TaskMsgModeEndAdapter(Context context) {
        super(context);
        putItemLayoutId(R.layout.item_msg_mode_end);
    }

    @Override
    public void onBind(ViewHolder holder, MessageTemplate item, int position) {
        holder.setText(R.id.tvSwitch1, item.name);
        if(item.isSelect){
            holder.getView(R.id.tvSwitch1).setBackground(mContext.getResources().getDrawable(R.drawable.create_task_dialog_msg_mode_item_shape));
        }else{
            holder.getView(R.id.tvSwitch1).setBackground(mContext.getResources().getDrawable(R.drawable.create_task_dialog_msg_mode_item_shape1));

        }
    }
}

