package com.vma.speechrobot.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.example.common.adapter.recycler.BaseRecyclerAdapter;
import com.example.common.adapter.recycler.ViewHolder;
import com.vma.speechrobot.R;
import com.vma.speechrobot.bean.Customer;
import com.vma.speechrobot.utils.TimeUtils;

/**
 * Created by Administrator on 2018/12/25.
 */

public class YinRuAdapter  extends BaseRecyclerAdapter<Customer> {
    private int page=0;
    public YinRuAdapter(Context context) {
        super(context);
        putItemLayoutId(R.layout.recycler_item_yinru);
    }

    public void setPage(int page) {
        this.page = page;
    }

    @Override
    public void onBind(ViewHolder holder, Customer item, int position) {
        holder.setText(R.id.tv_num, ""+item.id);
        holder.setText(R.id.tv_item, item.list_name);
        holder.setText(R.id.task_num, ""+item.effective_num);
        holder.setText(R.id.tv_time, ""+ TimeUtils.millis2String(Long.valueOf(item.create_time)));
        if(item.is_use==1){
            holder.setText(R.id.tv_type, "已拨打");
        }else{
            holder.setText(R.id.tv_type, "未拨打");
        }

        if(item.isSelect){
            ((ImageView)holder.getView(R.id.iv_item)).setImageResource(R.drawable.danxuan1);
        }else{
            ((ImageView)holder.getView(R.id.iv_item)).setImageResource(R.drawable.danxuan);
        }
    }
}

