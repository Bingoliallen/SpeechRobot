package com.vma.speechrobot.adapter;

import android.content.Context;
import com.example.common.adapter.recycler.BaseRecyclerAdapter;
import com.example.common.adapter.recycler.ViewHolder;
import com.vma.speechrobot.R;
import com.vma.speechrobot.bean.Employee;
import com.vma.speechrobot.bean.KeyNameBean;
import com.vma.speechrobot.bean.MessageTemplate;

/**
 * Date: 2018/12/25
 * Author: libaibing
 * Email：
 * Des：
 */

public class TaskWcPushAdapter extends BaseRecyclerAdapter<Employee> {

    public TaskWcPushAdapter(Context context) {
        super(context);
        putItemLayoutId(R.layout.recycler_item_task_wcpush_itme);
    }


    @Override
    public void onBind(ViewHolder holder, Employee item, int position) {
         holder.setText(R.id.tv_item, item.employee_name);
         if(item.isSelect){
             holder.getView(R.id.tv_item).setBackground(mContext.getResources().getDrawable(R.drawable.create_task_dialog_item_shape));
         }else{
             holder.getView(R.id.tv_item).setBackground(mContext.getResources().getDrawable(R.drawable.create_task_dialog_item_shape1));

         }
    }
}

