package com.vma.speechrobot.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.common.adapter.recycler.BaseRecyclerAdapter;
import com.example.common.adapter.recycler.ViewHolder;
import com.example.common.consts.CommonTimeConst;
import com.example.common.utils.TimeUtil;
import com.vma.speechrobot.R;
import com.vma.speechrobot.bean.TaskBean;
import com.vma.speechrobot.enums.TaskStatus;
import com.vma.speechrobot.widget.MorePopWindow;

/**
 * Date: 2018/12/27
 * Author: libaibing
 * Email：
 * Des：
 */

public class TaskManagerAdapter extends BaseRecyclerAdapter<TaskBean> {
    private MorePopWindow.MorePopWindowListener mMorePopWindowListener;

    public void setmMorePopWindowListener(MorePopWindow.MorePopWindowListener mMorePopWindowListener) {
        this.mMorePopWindowListener = mMorePopWindowListener;
    }

    public  interface onRecyclerItemClickedListener{
        void onRecyclerItemClicked(TaskBean data,int position);
    }
    private onRecyclerItemClickedListener monRecyclerItemClickedListener;

    public void setMonRecyclerItemClickedListener(onRecyclerItemClickedListener monRecyclerItemClickedListener) {
        this.monRecyclerItemClickedListener = monRecyclerItemClickedListener;
    }

    public TaskManagerAdapter(Context context) {
        super(context);
        putItemLayoutId(R.layout.recycler_item_task_manager);
        setOnRecyclerItemClickListener(new OnRecyclerItemClickListener<TaskBean>() {
            @Override
            public void onRecyclerItemClicked(BaseRecyclerAdapter adapter, View view, TaskBean data,
                                              int position) {
                if(monRecyclerItemClickedListener!=null){
                    monRecyclerItemClickedListener.onRecyclerItemClicked(data ,position);
                }
               // TaskDetailActivity.launch(mContext, data.id);
            }
        });
    }

    @Override
    public void onBind(ViewHolder holder, final TaskBean item,final int position) {
        LinearLayout iv_changed= holder.getView(R.id.iv_changed);
        iv_changed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MorePopWindow morePopWindow = new MorePopWindow((Activity) mContext ,item.id,item.status);
                morePopWindow.setmMorePopWindowListener(mMorePopWindowListener);
                morePopWindow.showPopupWindow(iv_changed);
            }
        });

        LinearLayout mLinerItem = holder.getView(R.id.mLinerItem);
        if(item.isSelect){
            mLinerItem.setBackground(
                    ContextCompat.getDrawable(mContext, R.drawable.task_manager_item_tv_shape));
        }else{
            mLinerItem.setBackground(
                    ContextCompat.getDrawable(mContext, R.drawable.task_manager_item_tv_shape1));
        }
        TextView tvStatus = holder.getView(R.id.tv_status);
        ProgressBar progressBar = holder.getView(R.id.progress_bar_task);
        progressBar.setMax(item.user_count);
        progressBar.setProgress(item.complete_user_count);
        holder.setText(R.id.tv_name, item.task_name);
        holder.setText(R.id.tv_progress,
                String.format("进度   %d/%d", item.complete_user_count, item.user_count));
        holder.setText(R.id.tv_robot, String.format("机器坐席号码:%s", item.mobile));
        holder.setText(R.id.tv_create, String
                .format("%s创建于%s", item.admin_name,
                        TimeUtil
                                .long2Str(Long.parseLong(item.create_time), String.format("%s-%s-%s %s:%s",
                                        CommonTimeConst.YEAR, CommonTimeConst.MONTH, CommonTimeConst.DAY,
                                        CommonTimeConst.HOUR, CommonTimeConst.MINUTE))));
        switch (item.status) {
            case TaskStatus.NO_START:
                tvStatus.setTextColor(ContextCompat.getColor(mContext, R.color.common_app_text_theme1));
                tvStatus.setText("未开始");
                progressBar.setProgressDrawable(
                        ContextCompat.getDrawable(mContext, R.drawable.task_manager_progress_shape));
                break;
            case TaskStatus.PROCESS:
                tvStatus.setTextColor(ContextCompat.getColor(mContext, R.color.common_app_text_theme1));
                tvStatus.setText("进行中");
                progressBar.setProgressDrawable(
                        ContextCompat.getDrawable(mContext, R.drawable.progress_bar_task_theme1));
                break;
            case TaskStatus.PAUSE:
                tvStatus.setTextColor(ContextCompat.getColor(mContext, R.color.common_app_text_theme10));
                tvStatus.setText("已暂停");
                progressBar.setProgressDrawable(
                        ContextCompat.getDrawable(mContext, R.drawable.progress_bar_task_theme8));
                break;
            case TaskStatus.STOP:
                tvStatus.setTextColor(ContextCompat.getColor(mContext, R.color.common_app_text_theme10));
                tvStatus.setText("已结束");
                progressBar.setProgressDrawable(
                        ContextCompat.getDrawable(mContext, R.drawable.progress_bar_task_theme8));
                break;
            case TaskStatus.COMPLETE:
                tvStatus.setTextColor(ContextCompat.getColor(mContext, R.color.common_app_text_theme1));
                tvStatus.setText("已完成");
                progressBar.setProgressDrawable(
                        ContextCompat.getDrawable(mContext, R.drawable.progress_bar_task_theme5));
                break;
        }
    }
}
